package org.example.models;

public class AccountInfo {
    private String accountId;
    private double balance;
    private String type;

    public AccountInfo(String accountId, double balance, String type) {
        this.accountId = accountId;
        this.balance = balance;
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}