package com.example.myprogrammationmobileproject;

import java.util.ArrayList;
import java.util.List;

public class RestFinanceResponse {
    private List<StockCompany> symbolsList;

    public List<StockCompany> getSymbolsList() {
        return symbolsList;
    }

    public void setSymbolsList(List<StockCompany> symbolsList) {
        this.symbolsList = symbolsList;
    }
}
