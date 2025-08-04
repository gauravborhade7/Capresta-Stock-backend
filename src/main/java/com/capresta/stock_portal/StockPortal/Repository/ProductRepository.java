package com.capresta.stock_portal.StockPortal.Repository;
import com.capresta.stock_portal.StockPortal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom query methods here if needed
//    List<Product> findByProductName(String productName);
    List<Product> findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(String ProductName,String location);

}