package com.capresta.stock_portal.StockPortal.Service;
import com.capresta.stock_portal.StockPortal.Repository.ProductRepository;
import com.capresta.stock_portal.StockPortal.model.Product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        return productRepository.findByProductNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name,location);
    }




    private static final String IMAGE_DIR = "src/main/resources/static/assets/images/";
    private static final String LABREPORT_DIR = "src/main/resources/static/assets/labreports/";



    public List<Product> searchByNameAndLocation(String productName, String location) {
        return productRepository.findByProductNameContainingIgnoreCaseAndLocationContainingIgnoreCase(productName, location);
    }

    public Product addProduct(Product product, MultipartFile image, MultipartFile labReport) throws IOException {
        // Ensure directories exist
        Files.createDirectories(Paths.get(IMAGE_DIR));
        Files.createDirectories(Paths.get(LABREPORT_DIR));

        if (image != null && !image.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_DIR + imageFileName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImagePath("/assets/images/" + imageFileName);
        }

        if (labReport != null && !labReport.isEmpty()) {
            String labReportFileName = System.currentTimeMillis() + "_" + labReport.getOriginalFilename();
            Path labReportPath = Paths.get(LABREPORT_DIR + labReportFileName);
            Files.copy(labReport.getInputStream(), labReportPath, StandardCopyOption.REPLACE_EXISTING);
            product.setLabReportPath("/assets/labreports/" + labReportFileName);
        }

        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
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
