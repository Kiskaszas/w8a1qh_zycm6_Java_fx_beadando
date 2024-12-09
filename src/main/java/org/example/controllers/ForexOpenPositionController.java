package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.services.ForexService;

public class ForexOpenPositionController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<String> directionComboBox;

    private final ForexService forexService = new ForexService();

    @FXML
    public void initialize() {
        currencyPairComboBox.getItems().addAll("EUR/USD", "GBP/USD", "USD/JPY");
        directionComboBox.getItems().addAll("Buy", "Sell");
    }

    @FXML
    public void onOpenPositionClicked() {
        String selectedPair = currencyPairComboBox.getValue();
        String direction = directionComboBox.getValue();
        String amountText = amountField.getText();

        if (selectedPair != null && direction != null && amountText != null) {
            try {
                double amount = Double.parseDouble(amountText);
                forexService.openPosition(selectedPair, amount, direction);
                App.showPopup("Success", "Position opened successfully.");
            } catch (NumberFormatException e) {
                App.showPopup("Error", "Invalid amount.");
            }
        } else {
            App.showPopup("Error", "Please fill all fields.");
        }
    }
}