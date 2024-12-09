package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.AccountInfo;
import org.example.services.ForexService;

public class ForexAccountInfoController {

    @FXML
    private TableView<AccountInfo> accountTable;
    @FXML
    private TableColumn<AccountInfo, String> accountIdColumn;
    @FXML
    private TableColumn<AccountInfo, Double> balanceColumn;
    @FXML
    private TableColumn<AccountInfo, String> typeColumn;

    private final ForexService forexService = new ForexService();

    @FXML
    public void initialize() {
        accountIdColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        accountTable.getItems().addAll(forexService.getAccountInfo());
    }
}