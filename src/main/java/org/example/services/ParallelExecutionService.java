package org.example.services;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelExecutionService {

    private final ExecutorService executorService;

    public ParallelExecutionService() {
        this.executorService = Executors.newFixedThreadPool(2); // Két szál a példában
    }

    public void startParallelExecution(Label label1, Label label2) {
        executorService.execute(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                int finalCount = count++;
                Platform.runLater(() -> label1.setText("Label 1: " + finalCount));
                try {
                    Thread.sleep(1000); // 1 másodpercenként frissítés
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        executorService.execute(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                int finalCount = count++;
                Platform.runLater(() -> label2.setText("Label 2: " + finalCount));
                try {
                    Thread.sleep(2000); // 2 másodpercenként frissítés
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void stopParallelExecution() {
        executorService.shutdownNow();
    }
}
