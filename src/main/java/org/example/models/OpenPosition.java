package org.example.models;

public class OpenPosition {
    private String positionId;
    private String currencyPair;
    private double amount;
    private String direction;
    private double price;

    public OpenPosition(String positionId, String currencyPair, double amount, String direction, double price) {
        this.positionId = positionId;
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.direction = direction;
        this.price = price;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}