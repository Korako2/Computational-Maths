package lab5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lab5.data.ApproximatedFunction;
import lab5.data.DifferentialEquation;

public class GraphDraftsman extends Application {

    private LineChart<Number, Number> lineChart;
    private static DifferentialEquation function;
    private static String functionName;
    private static ApproximatedFunction approximatedFunction;
    private static String approximatedFunctionName;
    private static double xMin;
    private static double xMax;

    public static void setFunction(DifferentialEquation function, String functionName) {
        GraphDraftsman.function = function;
        GraphDraftsman.functionName = functionName;
    }
    public static void setApproximatedFunction(ApproximatedFunction approximatedFunction, String approximatedFunctionName) {
        GraphDraftsman.approximatedFunction = approximatedFunction;
        GraphDraftsman.approximatedFunctionName = approximatedFunctionName;
    }
    public static void setBounds(double min, double max) {
        GraphDraftsman.xMin = min;
        GraphDraftsman.xMax = max;
    }

    public void build() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Графики");
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("");
        Scene scene = new Scene(lineChart, 650, 650);
        drawAnalyticalFunction(function, xMin, xMax, functionName, true);
        drawFunction(approximatedFunction, xMin, xMax, approximatedFunctionName, false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawAnalyticalFunction(DifferentialEquation function, double xMin, double xMax, String name, boolean effect) {
        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        double step = (xMax - xMin) / 2000;
        for (double i = xMin - 1; i < xMax + 1; i += step) {
            XYChart.Data item = new XYChart.Data(i, function.calculateSolutionFunction(i));
            data.add(item);
        }
        series.setData(data);
        lineChart.getData().add(series);
        for (XYChart.Data item : data) {
            item.getNode().setVisible(false);
        }
        if (effect) series.getNode().setStyle("-fx-stroke-dash-array: 1 20 20 1; ");
    }

    public void drawFunction(ApproximatedFunction function, double xMin, double xMax, String name, boolean effect) {
        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        double step = (xMax - xMin) / 2000;
        for (double i = xMin - 1; i < xMax + 1; i += step) {
            XYChart.Data item = new XYChart.Data(i, function.calculate(i));
            data.add(item);
        }
        series.setData(data);
        lineChart.getData().add(series);
        for (XYChart.Data item : data) {
            item.getNode().setVisible(false);
        }
        if (effect) series.getNode().setStyle("-fx-stroke-dash-array: 1 10 10 1; ");
    }
}
