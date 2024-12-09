package org.example.services;

import org.example.models.AccountInfo;
import org.example.models.HistoricalPrice;
import org.example.models.OpenPosition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForexService {

    // Mock data generator
    private final Random random = new Random();

    // Számlainformációk lekérése
    public List<AccountInfo> getAccountInfo() {
        List<AccountInfo> accounts = new ArrayList<>();
        accounts.add(new AccountInfo("ACC12345", 10000.0, "Standard"));
        accounts.add(new AccountInfo("ACC67890", 15000.0, "Premium"));
        return accounts;
    }

    // Aktuális árak lekérése
    public String getCurrentPrice(String currencyPair) {
        return String.format("%.4f", 1 + random.nextDouble());
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public List<HistoricalPrice> getHistoricalPrices(String currencyPair, LocalDate startDate, LocalDate endDate) {
        List<HistoricalPrice> historicalPrices = new ArrayList<>();
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            String formattedDate = date.format(formatter);
            historicalPrices.add(new HistoricalPrice(formattedDate, random.nextDouble() * 100));
            date = date.plusDays(1);
        }

        return historicalPrices;
    }

    // Pozíció nyitása
    public void openPosition(String currencyPair, double amount, String direction) {
        // Mock server response for opening a position
        System.out.println("Position opened: " + direction + " " + amount + " " + currencyPair);
    }

    // Pozíció zárása
    public void closePosition(String positionId) {
        // Mock server response for closing a position
        System.out.println("Position closed: ID " + positionId);
    }

    // Nyitott pozíciók lekérése
    public List<OpenPosition> getOpenPositions() {
        List<OpenPosition> openPositions = new ArrayList<>();
        openPositions.add(new OpenPosition("POS123", "EUR/USD", 1000, "Buy", 1.2345));
        openPositions.add(new OpenPosition("POS456", "GBP/USD", 500, "Sell", 1.3456));
        return openPositions;
    }
}