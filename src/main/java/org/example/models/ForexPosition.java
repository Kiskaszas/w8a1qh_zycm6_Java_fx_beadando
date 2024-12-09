package org.example.models;

public class ForexPosition {

    private int id;

    private String currencyPair;

    private double quantity;

    private String direction;

    private String status;

    public ForexPosition() {
    }

    public ForexPosition(String currencyPair, double quantity, String direction, String status) {
        this.currencyPair = currencyPair;
        this.quantity = quantity;
        this.direction = direction;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ForexPosition{" +
                "id=" + id +
                ", currencyPair='" + currencyPair + '\'' +
                ", quantity=" + quantity +
                ", direction='" + direction + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}