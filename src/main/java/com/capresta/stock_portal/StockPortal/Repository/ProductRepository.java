package com.capresta.stock_portal.StockPortal.Repository;
import com.capresta.stock_portal.StockPortal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String productName, String location);

}