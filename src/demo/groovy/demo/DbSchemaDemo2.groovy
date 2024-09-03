package demo


import groovy.sql.Sql
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage

class DbSchemaDemo2 extends Application {
    @Override
    void start(Stage stage) {


        @GrabConfig(systemClassLoader = true)
        @Grab(group='mysql', module='mysql-connector-java', version='8.0.26')

        // Establishing the connection
        def sql = Sql.newInstance('jdbc:mysql://localhost:3306/dbschema', 'dbschema', 'dbschema12', 'com.mysql.cj.jdbc.Driver')

        // Executing a query
        sql.eachRow('SELECT VERSION()') { row ->
            println "Database version: ${row[0]}"
        }


        stage.title = 'Persons Age Chart'

        // Define the axes
        final CategoryAxis xAxis = new CategoryAxis()
        final NumberAxis yAxis = new NumberAxis()
        xAxis.label = 'Name'
        yAxis.label = 'Age'

        // Create the chart
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis)
        barChart.title = 'Age of Persons'

        // Fetch data from the database
        def data = sql.rows('SELECT name, age FROM persons')

        // Populate the chart with data
        XYChart.Series<String, Number> series = new XYChart.Series<>()
        series.name = 'Persons'
        data.each { row ->
            series.data.add(new XYChart.Data<>(row.name, row.age))
        }

        barChart.data.add(series)

        // Set up the scene and stage
        Scene scene = new Scene(barChart, 800, 600)
        stage.scene = scene
        stage.show()
    }

    static void main(String[] args) {
        launch(args)
    }
}