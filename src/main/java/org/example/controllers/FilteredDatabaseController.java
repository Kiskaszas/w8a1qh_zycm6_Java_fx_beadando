package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.DataRow;
import org.example.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class FilteredDatabaseController {

    @FXML
    public RadioButton ferfiRadio;
    @FXML
    public RadioButton noRadio;
    @FXML
    public TableColumn columnNezoFerfi;


    @FXML
    private TextField dateField;

    @FXML
    private TextField idopontField;

    @FXML
    private TextField dateTimeField;

    @FXML
    private ComboBox<String> dateComboBox;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private TextField nezoNevField;

    @FXML
    private CheckBox berletesField;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField belepo;

    @FXML
    private ToggleGroup entryGroup;

    @FXML
    private RadioButton paidEntry;

    @FXML
    private RadioButton unpaidEntry;

    @FXML
    private CheckBox showAll;

    @FXML
    private TableView<DataRow> filteredTableView;

    @FXML
    private TableColumn<DataRow, Long> columnId;

    @FXML
    private TableColumn<DataRow, String> columnIdopont;
    @FXML
    private TableColumn<DataRow, String> columnNezoNeve;
    @FXML
    private TableColumn<DataRow, String> columnNezoBerletes;

    @FXML
    private TableColumn<DataRow, String> columnDatum;

    @FXML
    private TableColumn<DataRow, String> columnKezdes;

    @FXML
    private TableColumn<DataRow, String> columnTipus;

    @FXML
    private TableColumn<DataRow, Integer> columnBelepo;

    @FXML
    private TextField idField;

    @FXML
    private TextField belepoField;

    @FXML
    private TextField datumField;
    @FXML
    private TextField kezdesField;

    @FXML
    public ComboBox<String> nezoComboBox;

    ToggleGroup toggleGroup = new ToggleGroup();

    private final DatabaseService databaseService = new DatabaseService();

    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("belepesId"));
        columnIdopont.setCellValueFactory(new PropertyValueFactory<>("idopont"));
        columnNezoNeve.setCellValueFactory(new PropertyValueFactory<>("nev"));
        columnNezoFerfi.setCellValueFactory(new PropertyValueFactory<>("ferfi"));
        columnNezoBerletes.setCellValueFactory(new PropertyValueFactory<>("berletes"));
        columnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        columnKezdes.setCellValueFactory(new PropertyValueFactory<>("kezdes"));
        columnTipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        columnBelepo.setCellValueFactory(new PropertyValueFactory<>("belepo"));

        loadData();
        ferfiRadio.setToggleGroup(toggleGroup);
        noRadio.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            System.out.println(newVal + " was selected");
        });


        if (dateComboBox != null) {
            dateComboBox.setOnAction(event -> {
                String selectedMeccs = dateComboBox.getSelectionModel().getSelectedItem();
                if (selectedMeccs != null) {
                    loadTimesForDate(selectedMeccs);
                }
            });
        }

        if(nezoComboBox != null) {
            nezoComboBox.setOnAction(event -> {
                String selectedNezo =  nezoComboBox.getSelectionModel().getSelectedItem();
                if (selectedNezo != null) {
                    loadNezoIsFerfiCheckbox();
                    loadNezoIsBerletesCheckbox();
                }
            });
        }

        filteredTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //idField.setText(String.valueOf(newSelection.getBelepesId()));
                columnIdopont.setText(String.valueOf(newSelection.getIdopont()));
                columnNezoNeve.setText(String.valueOf(newSelection.getNev()));
                columnNezoFerfi.setText(String.valueOf(newSelection.isFerfi()));
                columnNezoBerletes.setText(String.valueOf(newSelection.isBerletes()));
                if (datumField!=null) {
                    datumField.setText(newSelection.getDatum());
                }
                kezdesField.setText(newSelection.getKezdes());
                typeCombo.setValue(newSelection.getTipus());
                belepoField.setText(String.valueOf(newSelection.getBelepo()));
            } else {
                clearForm();
            }
        });
    }

    private void loadNezoIsBerletesCheckbox() {
        berletesField.setSelected(databaseService.fechUnikNezoIsBerletes(nezoComboBox.getValue()));
    }

    private void loadDatesIntoComboBox() {
        if (dateComboBox != null) {
            ObservableList<String> datumok = FXCollections.observableArrayList(databaseService.fetchUniqueDates());
            dateComboBox.setItems(datumok);
        }
    }

    private void loadNezoIntoComboBox() {
        if (nezoComboBox != null) {
            ObservableList<String> nezok = FXCollections.observableArrayList(databaseService.fechUnikNezok());
            nezoComboBox.setItems(nezok);
        }
    }

    private void loadNezoIsFerfiCheckbox(){
        ferfiRadio.setSelected(databaseService.fechUnikNezoIsFerfi(nezoComboBox.getValue()));
    }

    private void loadTimesForDate(String date) {
        if (timeComboBox != null) {
            ObservableList<String> times = FXCollections.observableArrayList(databaseService.fetchTimesForDate(date));
            timeComboBox.setItems(times);
        }
    }

    private void loadData() {
        List<DataRow> data = databaseService.get100DataRow();
        ObservableList<DataRow> observableMatches = FXCollections.observableArrayList(data);
        filteredTableView.setItems(observableMatches);
        loadDatesIntoComboBox();
        loadNezoIntoComboBox();
    }

    @FXML
    private void showAll(){
        List<DataRow> filteredMatches = new ArrayList<>();
        filteredMatches = databaseService.get100DataRow();
        ObservableList<DataRow> observableMatches = FXCollections.observableArrayList(filteredMatches);
        filteredTableView.setItems(observableMatches);
    }

    @FXML
    private void onFilterClicked() {
        // Szűrési paraméterek összegyűjtése
        String idopont;
        if (idopontField != null) idopont = idopontField.getText();
        else idopont = null;
        String date = dateComboBox.getSelectionModel().getSelectedItem();
        String dateTime = timeComboBox.getSelectionModel().getSelectedItem();
        String nezoNev = nezoComboBox.getSelectionModel().getSelectedItem();

        boolean isFerfi = ferfiRadio.isSelected();
        boolean isNo = noRadio.isSelected();
        if (isNo){
            isFerfi = false;
        } else {
            isFerfi = true;
        }
        boolean berletes = berletesField.isSelected();
        String type = typeCombo.getValue();
        int belopoSzam = 0;
        if (belepo != null && !belepo.getText().isEmpty() && belepo.getText().matches("\\d+")) {
            belopoSzam = Integer.parseInt(belepo.getText());
        }

        // Szűrési logika
        if (idopont.isEmpty() && date!=null && date.isEmpty() && dateTime!=null &&dateTime.isEmpty() && nezoNev.isEmpty() && !isFerfi && !berletes && type!=null && type.isEmpty() && belopoSzam==0){
            showAll();
        }
        else{
            List<DataRow> filteredMatches = new ArrayList<>();
            filteredMatches = databaseService.getFilteredMatches(idopont, date, dateTime, nezoNev, isFerfi, berletes, type, belopoSzam);
            ObservableList<DataRow> observableMatches = FXCollections.observableArrayList(filteredMatches);
            filteredTableView.setItems(observableMatches);
            clearForm();
        }
    }

    private void clearFormx() {
        idopontField.clear();
        dateComboBox.getSelectionModel().clearSelection();
        timeComboBox.getSelectionModel().clearSelection();
        typeCombo.getSelectionModel().clearSelection();
        noRadio.setSelected(false);
        ferfiRadio.setSelected(false);
        berletesField.setSelected(false);
        //belepoField.clear();
        //idField.clear();
    }

    private void clearForm() {
        if (idopontField != null) {
            idopontField.clear();
        }
        if (dateComboBox != null) {
            dateComboBox.getSelectionModel().clearSelection();
        }
        if (timeComboBox != null) {
            timeComboBox.getSelectionModel().clearSelection();
        }
        if (nezoComboBox != null) {
            nezoComboBox.getSelectionModel().clearSelection();
        }

        ferfiRadio.setSelected(false);
        //belepesIdField.clear();
    }

}
