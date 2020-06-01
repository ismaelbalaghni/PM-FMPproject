package com.ismaelbalaghni.fmpproject.presentation.model;

public class StockCompany {
    private String symbol;
    private String name;
    private float price;
    private String exchange;

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getExchange() {
        return exchange;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
