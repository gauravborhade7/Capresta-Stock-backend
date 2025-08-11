package com.capresta.stock_portal.StockPortal.mailsender;

import java.util.List;

public class PreorderRequest {
    private String mobile;
    private List<ProductsDetails> products;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<ProductsDetails> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsDetails> products) {
        this.products = products;
    }
}
