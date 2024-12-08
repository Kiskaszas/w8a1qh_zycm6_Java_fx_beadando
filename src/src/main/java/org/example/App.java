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
        this.primaryStage = primaryStage; // Inicializálás
        try {
            // Menü és fő ablak inicializálása
            MenuBar menuBar = createMenuBar();
            //BorderPane root = new BorderPane(); // Alapértelmezett nézet

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
        Menu menu = new Menu("Navigáció");

        MenuItem databaseMenu = new MenuItem("olvas");//read
        databaseMenu.setOnAction(e -> navigateTo(DatabaseUI.createReadView()));

        MenuItem databaseFilteredMenu = new MenuItem("olvas2");//szűrővel
        databaseFilteredMenu.setOnAction(e -> navigateTo(DatabaseUI.createFilteredReadView()));

        MenuItem databaseInsertMenu = new MenuItem("Írás");//add
        databaseInsertMenu.setOnAction(e -> navigateTo(DatabaseUI.createInsertView()));

        MenuItem databaseUpdateMenu = new MenuItem("Módosít");//módosít
        databaseUpdateMenu.setOnAction(e -> navigateTo(DatabaseUI.createUpdateView()));

        MenuItem databaseDeleteMenu = new MenuItem("Töröl");//töröl
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

        menu.getItems().addAll(databaseMenu, databaseFilteredMenu, databaseInsertMenu, databaseUpdateMenu, databaseDeleteMenu);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu, soapMenu, feladat_3);
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

    private void navigateTo(BorderPane view) {
        // A menü hozzáadása minden nézethez
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
