package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.services.ForexService;

public class ForexClosePositionController {

    @FXML
    private TextField positionIdField;

    private final ForexService forexService = new ForexService();

    @FXML
    public void onClosePositionClicked() {
        String positionId = positionIdField.getText();

        if (positionId != null && !positionId.isEmpty()) {
            forexService.closePosition(positionId);
            App.showPopup("Success", "Position closed successfully.");
        } else {
            App.showPopup("Error", "Please enter a position ID.");
        }
    }
}