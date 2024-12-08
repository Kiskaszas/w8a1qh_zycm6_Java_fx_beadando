package org.example.utils;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class GraphUtil {

    public static LineChart<Number, Number> createLineChart(String title, String xAxisLabel, String yAxisLabel, List<Double> xValues, List<Double> yValues) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        for (int i = 0; i < xValues.size(); i++) {
            series.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
        }

        lineChart.getData().add(series);

        return lineChart;
    }
}
