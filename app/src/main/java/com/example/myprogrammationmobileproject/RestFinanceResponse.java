package com.example.myprogrammationmobileproject;

import java.util.ArrayList;

public class RestFinanceResponse {
    private ArrayList<StockCompany> symbolsList;

    public ArrayList<StockCompany> getSymbolsList() {
        return symbolsList;
    }

    public void setSymbolsList(ArrayList<StockCompany> symbolsList) {
        this.symbolsList = symbolsList;
    }
}
