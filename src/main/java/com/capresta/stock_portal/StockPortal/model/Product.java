package com.capresta.stock_portal.StockPortal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "trade_mark")
    private String tradeMark;

    @Column(name = "quantity_available_bags")
    private Integer quantityAvailableBags;

    public double getNetWeightPerBag() {
        return netWeightPerBag;
    }

    public void setNetWeightPerBag(double netWeightPerBag) {
        this.netWeightPerBag = netWeightPerBag;
    }

    @Column(name = "net_weight_per_bag")
    private double netWeightPerBag;

    @Column(name = "location")
    private String location;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "purchase_rate")
    private Double purchaseRate;

    @Column(name = "rate_per_kg_ex_cold")
    private Double ratePerKgExCold;

    @Column(name = "view_product", columnDefinition = "TEXT")
    private byte[] viewProduct;

//    @Column(name = "view_product", columnDefinition = "TEXT")
//    private String viewProduct;

//    @Column(name = "Add_product", columnDefinition = "TEXT")
//    private byte[] AddProduct;
//
@Lob
@Column(name = "addlab_report", columnDefinition = "LONGBLOB")
private byte[] labReport;


//    @Column(name = "lab_report", columnDefinition = "TEXT")
//    private byte[] AddlabReport;

//    @Column(name = "lab_report", columnDefinition = "TEXT")
//    private String labReport;


    }

