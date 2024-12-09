package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.example.services.ForexService;

public class ForexCurrentPricesController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private TextArea priceDisplay;

    private final ForexService forexService = new ForexService();

    @FXML
    public void initialize() {
        currencyPairComboBox.getItems().addAll("EUR/USD", "GBP/USD", "USD/JPY");
    }

    @FXML
    public void onGetPriceClicked() {
        String selectedPair = currencyPairComboBox.getValue();
        if (selectedPair != null) {
            String price = forexService.getCurrentPrice(selectedPair);
            priceDisplay.setText("Current price for " + selectedPair + ": " + price);
        } else {
            priceDisplay.setText("Please select a currency pair.");
        }
    }
}