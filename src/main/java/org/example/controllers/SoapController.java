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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
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
    @FXML
    private Button downloadAllButton;
    @FXML
    private Button filteredDownloadButton;
    @FXML
    private Button showGraphButton;

    private final SoapClientService soapClientService = new SoapClientService();

    @FXML
    public void onDownloadAllClicked() {
        try {
            String data = soapClientService.getAllExchangeRates();
            saveToFile("MNB.txt", data);
            resultArea.setText("Az összes adat sikeresen letöltve az MNB.txt fájlba!");
        } catch (Exception e) {
            resultArea.setText("Hiba történt az adatok letöltésekor: " + e.getMessage());
        }
    }

    @FXML
    public void onFilteredDownloadClicked() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String currencies = currenciesField.getText();

        try {
            String data = soapClientService.getFilteredExchangeRates(startDate, endDate, currencies);
            saveToFile("MNB_filtered.txt", data);
            resultArea.setText("A szűrt adatok sikeresen letöltve az MNB_filtered.txt fájlba!");
        } catch (Exception e) {
            resultArea.setText("Hiba történt a szűrt adatok letöltésekor: " + e.getMessage());
        }
    }

    @FXML
    public void onShowGraphClicked() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        String currencies = currenciesField.getText();
        String data = soapClientService.getFilteredExchangeRates(startDate, endDate, currencies);

        List<XYChart.Data<String, Number>> chartData = processGraphData(data);

        if (chartData.isEmpty()) {
            resultArea.setText("Nincs megjeleníthető adat!");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Árfolyam grafikon");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dátum");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Árfolyam");

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
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(data));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            if (!"MNBExchangeRates".equals(root.getNodeName())) {
                System.err.println("Nem megfelelő XML formátum: Gyökérelem nem MNBExchangeRates.");
                return chartData;
            }

            NodeList days = root.getElementsByTagName("Day");

            for (int i = 0; i < days.getLength(); i++) {
                Node dayNode = days.item(i);
                if (dayNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element dayElement = (Element) dayNode;

                    String date = dayElement.getAttribute("date");

                    NodeList rates = dayElement.getElementsByTagName("Rate");
                    if (rates.getLength() > 0) {
                        Element rateElement = (Element) rates.item(0);

                        String rateStr = rateElement.getTextContent().replace(",", ".");
                        double rate = Double.parseDouble(rateStr);

                        chartData.add(new XYChart.Data<>(date, rate));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Hiba az XML feldolgozása közben: " + e.getMessage());
            e.printStackTrace();
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

/*import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
}*/