package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private MenuItem databaseMenuItem, soapMenuItem, forexMenuItem, parallelMenuItem;

    @FXML
    public void initialize() {
        databaseMenuItem.setOnAction(event -> openWindow("fxml/readFromDatabase.fxml", "Database Operations"));
        soapMenuItem.setOnAction(event -> openWindow("fxml/soap.fxml", "SOAP Client"));
        forexMenuItem.setOnAction(event -> openWindow("fxml/forex.fxml", "Forex API"));
        parallelMenuItem.setOnAction(event -> openWindow("fxml/parallel.fxml", "Parallel Execution"));
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
