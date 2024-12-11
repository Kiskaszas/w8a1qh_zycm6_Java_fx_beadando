package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.App;
import org.example.models.HistoricalPrice;
import org.example.services.SoapClientService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.App.showPopup;

public class SoapController {

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        startDatePicker.setOnAction(event -> {
            if (startDatePicker.getValue() != null && endDatePicker.getValue() != null &&
                    startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                startDatePicker.setValue(endDatePicker.getValue().minusDays(1));
            }
        });
        endDatePicker.setOnAction(event -> {
            if (startDatePicker.getValue() != null && endDatePicker.getValue() != null &&
                    startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                endDatePicker.setValue(startDatePicker.getValue().plusDays(1));
            }
        });
    }

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
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            endDatePicker.setValue(startDatePicker.getValue().plusDays(1));
        }

        String startDate = startDatePicker.getValue().format(formatter);
        String endDate = endDatePicker.getValue().format(formatter);
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
        String startDate = startDatePicker.getValue().format(formatter);
        String endDate = endDatePicker.getValue().format(formatter);
        String currencies = currenciesField.getText();

        try {
            String data = soapClientService.getFilteredExchangeRates(startDate, endDate, currencies);
            List<XYChart.Data<String, Number>> chartData = processGraphData(data);

            Stage stage = new Stage();
            stage.setTitle("Árfolyam grafikon");

            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Dátum");
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Árfolyam");

            LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Árfolyamok");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(currencies);
            series.getData().addAll(chartData);

            lineChart.getData().add(series);

            Scene scene = new Scene(lineChart, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            resultArea.setText("Hiba történt a grafikon megjelenítésekor: " + e.getMessage());
        }
    }

    private List<XYChart.Data<String, Number>> processGraphData(String data) {
        List<XYChart.Data<String, Number>> chartData = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new java.io.ByteArrayInputStream(data.getBytes()));

            Element root = doc.getDocumentElement();

            NodeList days = root.getElementsByTagName("Day");

            for (int i = 0; i < days.getLength(); i++) {
                Element dayElement = (Element) days.item(i);

                String date = dayElement.getAttribute("date");

                Element rateElement = (Element) dayElement.getElementsByTagName("Rate").item(0);
                if(rateElement == null) {
                showPopup("Pénznem", "A pénznem nincs meghatározva");
                }
                String value = rateElement.getTextContent().replace(",", "."); // Tizedesvessző cseréje ponttal

                try {
                    chartData.add(new XYChart.Data<>(date, Double.parseDouble(value)));
                } catch (NumberFormatException e) {
                    System.err.println("Hibás adat a grafikon feldolgozásakor: " + value);
                }
            }

            Collections.reverse(chartData);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Hiba történt az XML adat feldolgozásakor: " + e.getMessage());
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