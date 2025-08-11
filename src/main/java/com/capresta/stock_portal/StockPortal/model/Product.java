package com.capresta.stock_portal.StockPortal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String tradeMark;
    private int quantityAvailableBags;
    private double netWeightPerBag;
    private String location;
    private double RatePerKgExCold;
    private String LotNo;
    private double PurchaseRate;


    private String imagePath;   // relative path to image
    private String labReportPath; // relative path to PDF

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getTradeMark() { return tradeMark; }
    public void setTradeMark(String tradeMark) { this.tradeMark = tradeMark; }

    public int getQuantityAvailableBags() { return quantityAvailableBags; }
    public void setQuantityAvailableBags(int quantityAvailableBags) { this.quantityAvailableBags = quantityAvailableBags; }

    public double getNetWeightPerBag() { return netWeightPerBag; }
    public void setNetWeightPerBag(double netWeightPerBag) { this.netWeightPerBag = netWeightPerBag; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getLabReportPath() { return labReportPath; }
    public void setLabReportPath(String labReportPath) { this.labReportPath = labReportPath; }

    public double getRatePerKgExCold() {
        return RatePerKgExCold;
    }

    public void setRatePerKgExCold(double ratePerKgExCold) {
        RatePerKgExCold = ratePerKgExCold;
    }

    public String getLotNo() {
        return LotNo;
    }

    public void setLotNo(String lotNo) {
        LotNo = lotNo;
    }

    public double getPurchaseRate() {
        return PurchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        PurchaseRate = purchaseRate;
    }
}
