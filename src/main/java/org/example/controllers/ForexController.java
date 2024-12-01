package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.services.ForexService;

public class ForexController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private TextArea resultArea;

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
            resultArea.setText("Current price for " + selectedPair + ": " + price);
        }
    }
}
