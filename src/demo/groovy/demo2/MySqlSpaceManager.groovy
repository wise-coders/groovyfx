import com.sun.javafx.stage.StageHelper
import groovy.sql.Sql
import groovy.transform.Canonical
import groovyx.javafx.beans.FXBindable
import javafx.collections.FXCollections
import javafx.scene.chart.PieChart
import javafx.scene.control.Hyperlink
import javafx.scene.control.TableCell

import static groovyx.javafx.GroovyFX.start

@Canonical
class DbItem {
    @FXBindable String name
    @FXBindable long dataSize
    @FXBindable long indexSize
}

@GrabConfig(systemClassLoader = true)
@Grab(group = 'mysql', module = 'mysql-connector-java', version = '8.0.26')

schemes = FXCollections.observableList([])
tables = FXCollections.observableList([])
chartData = FXCollections.observableArrayList()
chartData2 = FXCollections.observableArrayList()
chartData2.add(new PieChart.Data("No Data", 1))
def schemaTableView

Sql getSql(){
    Sql.newInstance('jdbc:mysql://localhost:3306/sakila', 'dbschema', 'dbschema12', 'com.mysql.cj.jdbc.Driver')
}

sql = getSql()
sql.eachRow("""
    SELECT
       schemaName,
       SUM(dataSize) dataSize,
       SUM(indexSize) indexSize 
    FROM (
       SELECT 
         table_schema schemaName,
         ifNull(data_length,0) dataSize,
         ifNull(index_length,0) indexSize 
       FROM information_schema.tables 
       WHERE table_schema NOT IN ('mysql','information_schema')
       ORDER BY data_length+index_length
    ) a 
    GROUP BY schemaName
    """) { row ->
    schemes.add( new DbItem(name: row.schemaName, dataSize: row.dataSize, indexSize : row.indexSize ) )
    chartData.add(new PieChart.Data(row.schemaName, row.dataSize + row.indexSize ))
}
sql.close()




void refreshDetails( DbItem schemaItem ){
    tables.clear()
    chartData2.clear()
    if ( schemaItem != null ) {
        Sql sql = getSql()
        sql.eachRow(
                """
                   SELECT 
                     table_name tableName,
                     ifNull(data_length,0) dataSize,
                     ifNull(index_length,0) indexSize 
                   FROM information_schema.tables 
                   WHERE table_schema=? ORDER By dataSize + indexSize
                """, [schemaItem.name]) { row ->
            tables.add(new DbItem(name: row.tableName, dataSize: row.dataSize, indexSize: row.indexSize))
            chartData2.add(new PieChart.Data(row.tableName, row.dataSize + row.indexSize))
        }
    }
    sql.close()
}



start {

    stage(title: 'MySQL Space Management', width: 1024, height: 960, visible: true, owner: StageHelper.getStages().get(0)) {
        scene {
            splitPane(orientation: VERTICAL, prefHeight: 200) {
                dividerPosition(index: 0, position: 0.5)
                splitPane(orientation: HORIZONTAL, prefHeight: 200) {
                    dividerPosition(index: 0, position: 0.5)
                    pieChart(data: chartData)
                    schemaTableView = tableView(items: schemes) {
                        tableColumn(property: "name", text: "Schema Name", prefWidth: 250, cellFactory: {
                            new TableCell<String, String>() {
                                Hyperlink hyperlink = new Hyperlink();

                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(null);
                                    if (item != null && !empty) {
                                        hyperlink.setText(item)
                                        setGraphic(hyperlink)
                                    }
                                }
                            }
                        })
                        tableColumn(property: "dataSize", text: "Data Size", prefWidth: 80)
                        tableColumn(property: "indexSize", text: "Index Size", prefWidth: 80)
                    }
                    schemaTableView.getSelectionModel().selectedItemProperty().addListener((o,p,c)-> {
                        refreshDetails( c )
                    })
                }
                splitPane(orientation: HORIZONTAL, prefHeight: 200) {
                    dividerPosition(index: 0, position: 0.5)
                    pieChart(data: chartData2)
                    tableView(items: tables) {
                        tableColumn(property: "name", text: "Tables in Selected Schema", prefWidth: 200)
                        tableColumn(property: "dataSize", text: "Data Size", prefWidth: 80)
                        tableColumn(property: "indexSize", text: "Index Size", prefWidth: 80)
                    }
                }
            }
        }
    }
}
