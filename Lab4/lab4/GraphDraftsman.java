package lab4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lab4.data.ApproximatedFunction;
import lab4.data.DiscreteFunction;

public class GraphDraftsman extends Application {
    private static DiscreteFunction discreteFunction;
    private static String functionName;
    private static ApproximatedFunction modifiedApproximatedFunction;
    private static String modifiedApproximatedFunctionName;
    private LineChart<Number, Number> lineChart;
    private static ApproximatedFunction function;
    public static void setDiscreteFunction(DiscreteFunction discreteFunction) {
        GraphDraftsman.discreteFunction = discreteFunction;
    }

    public static void setFunction(ApproximatedFunction function, String functionName) {
        GraphDraftsman.function = function;
        GraphDraftsman.functionName = functionName;
    }
    public static void setModifiedApproximatedFunction(ApproximatedFunction modifiedApproximatedFunction, String modifiedName) {
        GraphDraftsman.modifiedApproximatedFunction = modifiedApproximatedFunction;
        GraphDraftsman.modifiedApproximatedFunctionName = modifiedName;
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
        double xMin = findMin(discreteFunction.getXArray());
        double xMax = findMax(discreteFunction.getXArray());
        drawDiscreteFunction();
        drawFunction(modifiedApproximatedFunction, xMin, xMax, modifiedApproximatedFunctionName, false);
        drawFunction(function, xMin, xMax, functionName, true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double findMin(double[] arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private double findMax(double[] arr) {
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public void drawDiscreteFunction() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Сгенерированные точки");
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        for (int i = 0; i < discreteFunction.getXArray().length; i++) {
            data.add(new XYChart.Data(discreteFunction.getXArray()[i], discreteFunction.getYArray()[i]));
        }
        series.setData(data);
        lineChart.getData().add(series);
        Node line = series.getNode().lookup(".chart-series-line");
        line.setStyle("-fx-stroke: transparent;");
    }

    public void drawFunction(ApproximatedFunction function, double xMin, double xMax, String name, boolean effect) {
        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        double step = (xMax - xMin) / 200;
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
