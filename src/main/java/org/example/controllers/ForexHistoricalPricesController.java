package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.models.HistoricalPrice;
import org.example.services.ForexService;

import java.time.LocalDate;
import java.util.List;

public class ForexHistoricalPricesController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<HistoricalPrice> historicalDataTable;
    @FXML
    private TableColumn<HistoricalPrice, String> dateColumn;
    @FXML
    private TableColumn<HistoricalPrice, Double> priceColumn;
    @FXML
    private LineChart<String, Number> priceChart;

    private final ForexService forexService = new ForexService();

    @FXML
    public void initialize() {
        currencyPairComboBox.getItems().addAll("EUR/USD", "GBP/USD", "USD/JPY");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void onGetHistoricalDataClicked() {
        String currencyPair = currencyPairComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (currencyPair == null || startDate == null || endDate == null) {
            App.showPopup("Hiba", "Kérlek, töltsd ki az összes mezőt!");
            return;
        }

        List<HistoricalPrice> historicalPrices = forexService.getHistoricalPrices(currencyPair, startDate, endDate);

        historicalDataTable.getItems().setAll(historicalPrices);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(currencyPair);
        for (HistoricalPrice price : historicalPrices) {
            series.getData().add(new XYChart.Data<>(price.getDate(), price.getPrice()));
        }

        priceChart.getData().clear();
        priceChart.getData().add(series);
    }
}