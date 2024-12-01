package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.services.SoapClientService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoapController {

    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextField currenciesField;
    @FXML
    private TextArea resultArea;

    private final SoapClientService soapClientService = new SoapClientService();

    @FXML
    public void onDownloadAllClicked() {
        String data = soapClientService.getAllExchangeRates();
        saveToFile("MNB.txt", data);
        resultArea.setText("Adatok sikeresen letöltve az MNB.txt fájlba!");
    }

    @FXML
    public void onFilteredDownloadClicked() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String currencies = currenciesField.getText();
        String data = soapClientService.getFilteredExchangeRates(startDate, endDate, currencies);
        saveToFile("MNB_filtered.txt", data);
        resultArea.setText("Szűrt adatok letöltve az MNB_filtered.txt fájlba!");
    }

    @FXML
    public void onShowGraphClicked() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String currencies = currenciesField.getText();
        String data = soapClientService.getFilteredExchangeRates(startDate, endDate, currencies);

        List<XYChart.Data<String, Number>> chartData = processGraphData(data);

        Stage stage = new Stage();
        stage.setTitle("Árfolyam grafikon");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Árfolyamok");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Árfolyamok");
        series.getData().addAll(chartData);

        lineChart.getData().add(series);

        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private List<XYChart.Data<String, Number>> processGraphData(String data) {
        List<XYChart.Data<String, Number>> chartData = new ArrayList<>();
        String[] rows = data.split("\n");

        for (String row : rows) {
            String[] parts = row.split(",");
            if (parts.length == 2) {
                chartData.add(new XYChart.Data<>(parts[0], Double.parseDouble(parts[1])));
            }
        }
        return chartData;
    }

    private void saveToFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        } catch (IOException e) {
            resultArea.setText("Hiba történt az adatok mentésekor: " + e.getMessage());
        }
    }
}