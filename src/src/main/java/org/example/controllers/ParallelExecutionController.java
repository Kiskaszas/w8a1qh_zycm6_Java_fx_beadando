package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ParallelExecutionController {

    @FXML
    private Label label1, label2;

    @FXML
    public void onStartClicked() {
        new Thread(() -> updateLabel(label1, 1000, "Updating every second")).start();
        new Thread(() -> updateLabel(label2, 2000, "Updating every two seconds")).start();
    }

    private void updateLabel(Label label, int delay, String text) {
        try {
            while (true) {
                Thread.sleep(delay);
                javafx.application.Platform.runLater(() -> label.setText(text));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
