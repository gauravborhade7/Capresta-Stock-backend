package com.capresta.stock_portal.StockPortal.model;

import org.springframework.stereotype.Component;

@Component
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private String brandName;

    private String location;
    private String lotNo;
    private double stockQuantity;

    private double ratePerKgExCold;

    private String viewProduct; // Base64 string or URL
    private String labReportBase64; // base64 encoded image or PDF

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getRatePerKgExCold() {
        return ratePerKgExCold;
    }

    public void setRatePerKgExCold(double ratePerKgExCold) {
        this.ratePerKgExCold = ratePerKgExCold;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public double getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getLabReportBase64() {
        return labReportBase64;
    }

    public void setLabReportBase64(String labReportBase64) {
        this.labReportBase64 = labReportBase64;
    }

    public String getViewProduct() {
        return viewProduct;
    }

    public void setViewProduct(String viewProduct) {
        this.viewProduct = viewProduct;
    }
}
