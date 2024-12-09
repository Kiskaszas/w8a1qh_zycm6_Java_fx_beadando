package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.OpenPosition;
import org.example.services.ForexService;

public class ForexOpenPositionsController {

    @FXML
    private TableView<OpenPosition> openPositionsTable;
    @FXML
    private TableColumn<OpenPosition, String> positionIdColumn;
    @FXML
    private TableColumn<OpenPosition, String> currencyPairColumn;
    @FXML
    private TableColumn<OpenPosition, Double> amountColumn;
    @FXML
    private TableColumn<OpenPosition, String> directionColumn;
    @FXML
    private TableColumn<OpenPosition, Double> priceColumn;

    private final ForexService forexService = new ForexService();

    @FXML
    public void initialize() {
        positionIdColumn.setCellValueFactory(new PropertyValueFactory<>("positionId"));
        currencyPairColumn.setCellValueFactory(new PropertyValueFactory<>("currencyPair"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        openPositionsTable.getItems().addAll(forexService.getOpenPositions());
    }
}