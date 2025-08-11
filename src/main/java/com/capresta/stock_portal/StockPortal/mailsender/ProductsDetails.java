package com.capresta.stock_portal.StockPortal.mailsender;


public class ProductsDetails {
    private String productName;
    private String tradeMark;
    private int quantityAvailableBags;
    private double netWeightPerBag;
    private String location;
    private String LotNo;
    private double RatePerKgExCold;

    public String getLotNo() {
        return LotNo;
    }

    public void setLotNo(String lotNo) {
        LotNo = lotNo;
    }

    public double getRatePerKgExCold() {
        return RatePerKgExCold;
    }

    public void setRatePerKgExCold(double ratePerKgExCold) {
        RatePerKgExCold = ratePerKgExCold;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public int getQuantityAvailableBags() {
        return quantityAvailableBags;
    }

    public void setQuantityAvailableBags(int quantityAvailableBags) {
        this.quantityAvailableBags = quantityAvailableBags;
    }

    public double getNetWeightPerBag() {
        return netWeightPerBag;
    }

    public void setNetWeightPerBag(double netWeightPerBag) {
        this.netWeightPerBag = netWeightPerBag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
