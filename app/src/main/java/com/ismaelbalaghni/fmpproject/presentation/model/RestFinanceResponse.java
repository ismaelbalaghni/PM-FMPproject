package com.ismaelbalaghni.fmpproject.presentation.model;

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
