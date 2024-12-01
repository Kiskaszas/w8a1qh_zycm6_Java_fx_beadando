    package org.example.controllers;

    import javafx.application.Platform;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import org.example.models.DataRow;
    import org.example.models.Meccs;
    import org.example.models.Nezo;
    import org.example.services.DatabaseService;

    import java.util.regex.Pattern;

    public class DatabaseController {

        @FXML
        public DatePicker idopontDate;
        @FXML
        private TableView<DataRow> tableView;
        @FXML
        private TableColumn<DataRow, Long> belepesColumnId;
        @FXML
        private TableColumn<DataRow, String> belepesColumnIdopont;
        @FXML
        private TableColumn<DataRow, String> columnNezoNeve;

        @FXML
        private TableColumn<DataRow, String> columnNezoIsFerfi;

        @FXML
        RadioButton ferfiRadio = new RadioButton("Férfi");

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
////////////////////////////
        /*@FXML
        private TextField datumField;
        @FXML
        private TextField kezdesField;*/

        @FXML
        public TextField idopontField;

        @FXML
        private ComboBox<String> dateComboBox;

        @FXML
        private ComboBox<String> timeComboBox;

        @FXML
        private TextField tipusField;

        @FXML
        private TextField belepoField;
        @FXML
        private TextField belepesIdField;

        @FXML
        public ComboBox<String> nezoComboBox;

        private long nezoID;
        private long meccsID;
        private boolean isFerfi;

        private final DatabaseService databaseService = new DatabaseService();

        @FXML
        private ComboBox<Meccs> meccsComboBox;

        @FXML
        private TextField datumField;

        @FXML
        private TextField kezdesField;

        @FXML
        public void initialize() {
            belepesColumnId.setCellValueFactory(new PropertyValueFactory<>("belepesId"));
            belepesColumnIdopont.setCellValueFactory(new PropertyValueFactory<>("idopont"));
            columnNezoNeve.setCellValueFactory(new PropertyValueFactory<>("nev"));
            columnNezoIsFerfi.setCellValueFactory(new PropertyValueFactory<>("ferfi"));
            columnNezoBerletes.setCellValueFactory(new PropertyValueFactory<>("berletes"));
            columnDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
            columnKezdes.setCellValueFactory(new PropertyValueFactory<>("kezdes"));
            columnTipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
            columnBelepo.setCellValueFactory(new PropertyValueFactory<>("belepo"));

            loadDatesIntoComboBox();
            loadNezoIntoComboBox();

            loadTableData();

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
                    }
                });
            }

            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    belepesIdField.setText(String.valueOf(newSelection.getBelepesId()));
                    belepesColumnIdopont.setText(String.valueOf(newSelection.getIdopont()));
                    columnNezoNeve.setText(String.valueOf(newSelection.getNev()));
                    if (newSelection.isFerfi()){
                        ferfiRadio.setSelected(true);
                    }
                    columnNezoIsFerfi.setText(String.valueOf(newSelection.isFerfi()));
                    columnNezoBerletes.setText(String.valueOf(newSelection.getNev()));
                    if (belepoField!=null) {
                        belepoField.setText(String.valueOf(newSelection.getBelepo()));
                    }
                    nezoID = newSelection.getNezoId();
                    meccsID = newSelection.getMeccsId();
                    isFerfi = newSelection.isFerfi();
                } else {
                    clearForm();
                }
            });
        }

        private void loadDatesIntoComboBox() {
            if (dateComboBox != null) {
                ObservableList<String> datumok = FXCollections.observableArrayList(databaseService.fetchUniqueDates());
                dateComboBox.setItems(datumok);
            }
        }

        private void loadTimesForDate(String date) {
            if (timeComboBox != null) {
                ObservableList<String> times = FXCollections.observableArrayList(databaseService.fetchTimesForDate(date));
                timeComboBox.setItems(times);
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

        private void loadTableData() {
            ObservableList<DataRow> dataRows = FXCollections.observableArrayList(databaseService.get100DataRow());
            System.out.println("Retrieved matches: " + dataRows.size());
            Platform.runLater(() -> tableView.setItems(dataRows));
        }

        @FXML
        private void addRecord(ActionEvent event) {
            //meccs adatok
            Pattern hourAndMinutePattern = Pattern.compile("^\\d{2}:\\d{2}$");
            String idopont = idopontField.getText();

            if (!idopont.isEmpty() && !hourAndMinutePattern.matcher(idopont).matches()) {
                idopontField.clear();
                showAlert("Hiba", "Az dőpontnak a következő formátumnak kell lennie hh:mm!", Alert.AlertType.ERROR);
                return;
            }else{
                idopont = idopontField.getText();
            }

            String datum = dateComboBox.getSelectionModel().getSelectedItem();
            String kezdes = timeComboBox.getSelectionModel().getSelectedItem();
            //nezo adat
            long nezoId = nezoID;
            String nev = nezoComboBox.getValue();
            boolean ferfi = Boolean.parseBoolean(String.valueOf(ferfiRadio.isSelected()));
            boolean berletes = Boolean.parseBoolean(columnNezoBerletes.getText());

            DataRow dataRow = new DataRow(
                    0,
                    nezoId,
                    0,
                    idopont,
                    nev,
                    ferfi,
                    berletes,
                    ferfi,
                    berletes,
                    datum,
                    kezdes,
                    0,
                    null
            );

            if (databaseService.addRecord(dataRow)) {
                showAlert("Siker", "Rekord hozzáadva!", Alert.AlertType.INFORMATION);
                clearForm();
                loadTableData();
            } else {
                showAlert("Hiba", "Nem sikerült hozzáadni a rekordot.", Alert.AlertType.ERROR);
            }
        }

        @FXML
        private void updateRecord(ActionEvent event) {
            String idText = belepesIdField.getText();
            String datum = dateComboBox.getSelectionModel().getSelectedItem();
            String kezdes = timeComboBox.getSelectionModel().getSelectedItem();
            int belepo = Integer.parseInt(belepoField.getText());

            if (idText.isEmpty() || datum.isEmpty() || kezdes.isEmpty()) {
                showAlert("Hiba", "Minden mezőt ki kell tölteni a frissítéshez!", Alert.AlertType.ERROR);
                return;
            }

            try {
                belepo = Integer.parseInt(belepoField.getText());
                Long id = Long.parseLong(idText);

                if (databaseService.updateRecord(id, datum.toLowerCase(), kezdes.toLowerCase(), belepo)) {
                    showAlert("Siker", "Rekord frissítve!", Alert.AlertType.INFORMATION);
                    clearForm();
                    loadTableData();
                } else {
                    showAlert("Hiba", "Nem sikerült frissíteni a rekordot.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Hiba", "Az ID és a belépő érvényes számnak kell lennie!", Alert.AlertType.ERROR);
            }
        }

        @FXML
        private void deleteRecord(ActionEvent event) {
            String idText = belepesIdField.getText();

            if (idText.isEmpty()) {
                showAlert("Hiba", "Adja meg a törölni kívánt rekord azonosító alapján!", Alert.AlertType.ERROR);
                return;
            }

            try {
                Long id = Long.parseLong(idText);

                if (databaseService.deleteRecord(id)) {
                    showAlert("Siker", "Rekord törölve!", Alert.AlertType.INFORMATION);
                    belepesIdField.clear();
                    loadTableData();
                } else {
                    showAlert("Hiba", "Nem sikerült törölni a rekordot.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Hiba", "Az ID érvényes számnak kell lennie!", Alert.AlertType.ERROR);
            }
        }

        private void clearForm() {
            if (belepoField != null) {
                belepesIdField.clear();
            }
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

        private void showAlert(String title, String message, Alert.AlertType type) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }