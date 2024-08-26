import javafx.scene.Scene
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage

import static groovyx.javafx.GroovyFX.start
import static groovyx.javafx.GroovyFX.startStage
import groovy.sql.Sql

@GrabConfig(systemClassLoader = true)
@Grab(group='mysql', module='mysql-connector-java', version='8.0.26')

// Establishing the connection
def sql = Sql.newInstance('jdbc:mysql://localhost:3306/dbschema', 'dbschema', 'dbschema12', 'com.mysql.cj.jdbc.Driver')

// Executing a query
sql.eachRow('SELECT VERSION()') { row ->
    println "Database version: ${row[0]}"
}

// Closing the connection


start {

    final CategoryAxis xAxis = new CategoryAxis()
    final NumberAxis yAxis = new NumberAxis()
    xAxis.label = 'Title'
    yAxis.label = 'Rental Duration'

    // Create the chart
    final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis)
    barChart.title = 'Rental Duration'

    // Fetch data from the database
    def data = sql.rows('SELECT title, rental_duration FROM sakila.film')

    // Populate the chart with data
    XYChart.Series<String, Number> series = new XYChart.Series<>()
    series.name = 'Persons'
    data.each { row ->
        series.data.add(new XYChart.Data<>(row.title, row.rental_duration))
    }

    barChart.data.add(series)

    // Set up the scene and stage
    Scene scene = new Scene(barChart, 800, 600)
    Stage stage = new Stage()
    stage.scene = scene
    stage.show()


    startStage {
        stage(title: "GroovyFX Accordion Demo", x: 100, y: 100, visible: true, style: "decorated", primary: true) {
            scene(fill: ALICEBLUE, width: 400, height: 400) {
                accordion {
                    titledPane(id: "t1", text: "Label 1") {
                        content {
                            label(text: "This is Label 1\n\nAnd there were a few empty lines just there!")
                        }
                    }
                    titledPane(id: "t2", text: "Label 2") {
                        content {
                            label(text: "This is Label 2\n\nAnd there were a few empty lines just there!")
                        }
                    }
                    titledPane(id: "t3", text: "Label 3") {
                        // this is content
                        label(text: "This is Label 3\n\nAnd there were a few empty lines just there!")
                    }
                }
            }
        }
    }
}


