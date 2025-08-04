package com.capresta.stock_portal.StockPortal.Service;
import com.capresta.stock_portal.StockPortal.Repository.ProductRepository;
import com.capresta.stock_portal.StockPortal.model.Product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(String name,String location) {
        return productRepository.findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name,location);
    }


    public Product addProduct(Product product, MultipartFile viewProduct, MultipartFile labReport) {
        try {
            if (viewProduct != null && !viewProduct.isEmpty()) {
                product.setViewProduct(viewProduct.getBytes());
            }
            if (labReport != null && !labReport.isEmpty()) {
                product.setLabReport(labReport.getBytes());
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read files", e);
        }

        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


//    public Product addProduct(Product product, MultipartFile labReport) {
//        try {
//            if (labReport != null && !labReport.isEmpty()) {
//                product.setLabReport(labReport.getBytes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error reading lab report file", e);
//        }
//
//        return productRepository.save(product);
//    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setProductName(updatedProduct.getProductName());
            product.setTradeMark(updatedProduct.getTradeMark());
            product.setQuantityAvailableBags(updatedProduct.getQuantityAvailableBags());
            product.setNetWeightPerBag(updatedProduct.getNetWeightPerBag());
            product.setLocation(updatedProduct.getLocation());
            product.setLotNo(updatedProduct.getLotNo());
            product.setPurchaseRate(updatedProduct.getPurchaseRate());
            product.setRatePerKgExCold(updatedProduct.getRatePerKgExCold());
            product.setViewProduct(updatedProduct.getViewProduct());
            product.setLabReport(updatedProduct.getLabReport());
            return productRepository.save(product);
        }).orElse(null); // Alternatively, throw an exception if not found
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
