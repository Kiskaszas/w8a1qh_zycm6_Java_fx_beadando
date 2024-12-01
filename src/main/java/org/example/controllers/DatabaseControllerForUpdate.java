package org.example.controllers;

import javafx.scene.control.TextField;
import org.example.models.Meccs;
import org.example.services.DatabaseSericeForUpdate;
import org.example.services.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
public class DatabaseControllerForUpdate {

    @FXML
    private ComboBox<Meccs> meccsComboBox;

    @FXML
    private TextField datumField;

    @FXML
    private TextField kezdesField;

    @FXML
    private TextField belepoField;

    @FXML
    private TextField tipusField;

    private DatabaseSericeForUpdate databaseSericeForUpdate;

    @FXML
    public void initialize() {
        populateMeccsComboBox();
        meccsComboBox.setOnAction(event -> loadSelectedMeccsDetails());
        databaseSericeForUpdate = new DatabaseSericeForUpdate();
    }

    private void populateMeccsComboBox() {
        ObservableList<Meccs> meccsList = FXCollections.observableArrayList(databaseSericeForUpdate.getAllMeccs());
        meccsComboBox.setItems(meccsList);
    }

    private void loadSelectedMeccsDetails() {
        Meccs selectedMeccs = meccsComboBox.getValue();
        if (selectedMeccs != null) {
            datumField.setText(selectedMeccs.getDatum());
            kezdesField.setText(selectedMeccs.getKezdes());
            belepoField.setText(String.valueOf(selectedMeccs.getBelepo()));
            tipusField.setText(selectedMeccs.getTipus());
        }
    }

    @FXML
    private void updateMeccsRecord(ActionEvent event) {
        Meccs selectedMeccs = meccsComboBox.getValue();
        if (selectedMeccs != null) {
            selectedMeccs.setDatum(datumField.getText());
            selectedMeccs.setKezdes(kezdesField.getText());
            selectedMeccs.setBelepo(Integer.parseInt(belepoField.getText()));
            selectedMeccs.setTipus(tipusField.getText());

            if (databaseSericeForUpdate.updateMeccs(selectedMeccs)) {
                showAlert("Sikeres frissítés", "A rekord sikeresen frissült!");
            } else {
                showAlert("Frissítési hiba", "A rekord frissítése nem sikerült.");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
