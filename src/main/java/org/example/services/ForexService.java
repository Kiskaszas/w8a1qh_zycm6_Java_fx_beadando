package org.example.services;

import java.util.Random;

public class ForexService {

    public String getCurrentPrice(String currencyPair) {
        Random random = new Random();
        return String.format("%.4f", 1 + random.nextDouble());
    }
}
