package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.controllers.DatabaseController;
import org.example.ui.DatabaseUI;
import org.example.utils.DatabaseUtil;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage;
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            MenuBar menuBar = createMenuBar();

            root.setTop(menuBar);

            Scene scene = new Scene(root, 1200, 900);
            primaryStage.setTitle("JavaFX Alkalmazás");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public MenuBar createMenuBar() {
        Menu menu = new Menu("Adatbázis");

        MenuItem databaseMenu = new MenuItem("olvas");
        databaseMenu.setOnAction(e -> navigateTo(DatabaseUI.createReadView()));

        MenuItem databaseFilteredMenu = new MenuItem("olvas2");
        databaseFilteredMenu.setOnAction(e -> navigateTo(DatabaseUI.createFilteredReadView()));

        MenuItem databaseInsertMenu = new MenuItem("Írás");
        databaseInsertMenu.setOnAction(e -> navigateTo(DatabaseUI.createInsertView()));

        MenuItem databaseUpdateMenu = new MenuItem("Módosít");
        databaseUpdateMenu.setOnAction(e -> navigateTo(DatabaseUI.createUpdateView()));

        MenuItem databaseDeleteMenu = new MenuItem("Töröl");
        databaseDeleteMenu.setOnAction(e -> navigateTo(DatabaseUI.createDeleteView()));

        Menu soapMenu = new Menu("SOAP");

        MenuItem downloadAllItem = new MenuItem("Download All");
        downloadAllItem.setOnAction(event -> openSoapClientUI());

        MenuItem filteredDownloadItem = new MenuItem("Filtered Download");
        filteredDownloadItem.setOnAction(event -> openSoapClientUI());

        MenuItem showGraphItem = new MenuItem("Show Graph");
        showGraphItem.setOnAction(event -> openSoapClientUI());

        soapMenu.getItems().addAll(downloadAllItem, filteredDownloadItem, showGraphItem);

        Menu feladat_3 = new Menu("Páthuzamos almenü");
        MenuItem gomb = new MenuItem("Gomb");
        gomb.setOnAction(event -> showGomb());

        feladat_3.getItems().addAll(gomb);

        Menu forexMenu = new Menu("FOREX");

        MenuItem accountInfoItem = new MenuItem("Számlainformációk");
        accountInfoItem.setOnAction(event -> openForexAccountInfoUI());

        MenuItem currentPricesItem = new MenuItem("Aktuális árak");
        currentPricesItem.setOnAction(event -> openForexCurrentPricesUI());

        MenuItem historicalPricesItem = new MenuItem("Historikus árak");
        historicalPricesItem.setOnAction(event -> openForexHistoricalPricesUI());

        MenuItem openPositionItem = new MenuItem("Pozíció nyitás");
        openPositionItem.setOnAction(event -> openForexOpenPositionUI());

        MenuItem closePositionItem = new MenuItem("Pozíció zárás");
        closePositionItem.setOnAction(event -> openForexClosePositionUI());

        MenuItem openPositionsItem = new MenuItem("Nyitott pozíciók");
        openPositionsItem.setOnAction(event -> openForexOpenPositionsUI());

        forexMenu.getItems().addAll(
                accountInfoItem,
                currentPricesItem,
                historicalPricesItem,
                openPositionItem,
                closePositionItem,
                openPositionsItem
        );

        menu.getItems().addAll(databaseMenu, databaseFilteredMenu, databaseInsertMenu, databaseUpdateMenu, databaseDeleteMenu);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu, soapMenu, feladat_3, forexMenu);
        return menuBar;
    }

    private void openSoapClientUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/soap.fxml"));
            BorderPane soapLayout = loader.load();
            root.setCenter(soapLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openForexAccountInfoUI() {
        navigateTo("/fxml/forex_account_info.fxml");
    }

    private void openForexCurrentPricesUI() {
        navigateTo("/fxml/forex_current_prices.fxml");
    }

    private void openForexHistoricalPricesUI() {
        navigateTo("/fxml/forex_historical_prices.fxml");
    }

    private void openForexOpenPositionUI() {
        navigateTo("/fxml/forex_open_position.fxml");
    }

    private void openForexClosePositionUI() {
        navigateTo("/fxml/forex_close_position.fxml");
    }

    private void openForexOpenPositionsUI() {
        navigateTo("/fxml/forex_open_positions.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            BorderPane layout = loader.load();
            root.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void navigateTo(BorderPane view) {
        view.setTop(createMenuBar());
        primaryStage.getScene().setRoot(view);
    }

    @FXML
    public void showGomb() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gomb.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Gombok");
            stage.setScene(scene);
            stage.show();
        } catch    (IOException e)  {
            e.printStackTrace();    }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
