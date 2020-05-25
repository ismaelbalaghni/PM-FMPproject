package com.example.myprogrammationmobileproject.presentation.model;

public class FullStockCompany  {

    private float price, beta, lastDiv, changes, dcfDiff, dcf;
    private int volAvg;
    private long mktCap;
    private String symbol, exchange, range, companyName, industry, website, description, ceo, sector, image;

    public FullStockCompany() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public float getLastDiv() {
        return lastDiv;
    }

    public void setLastDiv(float lastDiv) {
        this.lastDiv = lastDiv;
    }

    public float getChanges() {
        return changes;
    }

    public void setChanges(float changes) {
        this.changes = changes;
    }

    public float getDcfDiff() {
        return dcfDiff;
    }

    public void setDcfDiff(float dcfDiff) {
        this.dcfDiff = dcfDiff;
    }

    public float getDcf() {
        return dcf;
    }

    public void setDcf(float dcf) {
        this.dcf = dcf;
    }

    public int getVolAvg() {
        return volAvg;
    }

    public void setVolAvg(int volAvg) {
        this.volAvg = volAvg;
    }

    public long getMktCap() {
        return mktCap;
    }

    public void setMktCap(long mktCap) {
        this.mktCap = mktCap;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
