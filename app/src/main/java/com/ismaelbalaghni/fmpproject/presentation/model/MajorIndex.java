package com.ismaelbalaghni.fmpproject.presentation.model;

public class MajorIndex {
    private String ticker;
    private float changes;
    private float price;
    private String indexName;

    public String getTicker() {
        return ticker;
    }

    public float getChanges() {
        return changes;
    }

    public float getPrice() {
        return price;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setChanges(float changes) {
        this.changes = changes;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
