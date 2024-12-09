package org.example.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.example.App;
import org.example.controllers.DatabaseController;

import java.io.IOException;

public class DatabaseUI {

    private static final DatabaseController controller = new DatabaseController();

    public static BorderPane createReadView() {
        return loadFXML("/fxml/readFromDatabase.fxml");
    }

    public static BorderPane createFilteredReadView() {
        return loadFXML("/fxml/filteredDatabase.fxml");
    }

    public static BorderPane createInsertView() {
        return loadFXML("/fxml/createDatabaseRow.fxml");
    }

    public static BorderPane createUpdateView() {
        return loadFXML("/fxml/updateDatabaseRecord.fxml");
    }

    public static BorderPane createDeleteView() {
        return loadFXML("/fxml/deleteDatabaseRecord.fxml");
    }

    private static BorderPane loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            ClassLoader loaderClassLoader = new App().getClass().getClassLoader();
            System.out.println(loaderClassLoader.getResource(fxmlPath)+"-----------------------------");
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Nem sikerült betölteni az FXML fájlt: " + fxmlPath);
        }
    }
}