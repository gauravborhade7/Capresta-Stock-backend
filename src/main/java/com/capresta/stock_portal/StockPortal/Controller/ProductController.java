package com.capresta.stock_portal.StockPortal.Controller;
import com.capresta.stock_portal.StockPortal.Repository.ProductRepository;
import com.capresta.stock_portal.StockPortal.model.Product;
import com.capresta.stock_portal.StockPortal.Service.ProductService;
import com.capresta.stock_portal.StockPortal.model.ProductResponseDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductResponseDTO dto;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " not found.");
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
    }

// ✅ Set your upload folder here
private final Path uploadDir = Paths.get("E:/StockPortal/assets").toAbsolutePath().normalize();

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
            System.out.println("📂 Created upload directory at: " + uploadDir.toAbsolutePath());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(
            @RequestParam String productName,
            @RequestParam String tradeMark,
            @RequestParam int quantityAvailableBags,
            @RequestParam double netWeightPerBag,
            @RequestParam String location,
            @RequestParam double ratePerKgExCold,
            @RequestParam String lotNo,
            @RequestParam double purchaseRate,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "labReport", required = false) MultipartFile labReport
    ) throws IOException {

        Product product = new Product();
        product.setProductName(productName);
        product.setTradeMark(tradeMark);
        product.setQuantityAvailableBags(quantityAvailableBags);
        product.setNetWeightPerBag(netWeightPerBag);
        product.setLocation(location);
        product.setRatePerKgExCold(ratePerKgExCold);
        product.setLotNo(lotNo);
        product.setPurchaseRate(purchaseRate);

        if (image != null && !image.isEmpty()) {
            product.setImagePath(saveFile(image));
        }
        if (labReport != null && !labReport.isEmpty()) {
            product.setLabReportPath(saveFile(labReport));
        }

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetPath = uploadDir.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName; // only store filename, not full path
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String productName,
            @RequestParam(required = false, defaultValue = "") String location) {

        List<Product> products = productRepository
                .findByProductNameContainingIgnoreCaseAndLocationContainingIgnoreCase(productName, location);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(products); // 200 OK with list of products
    }


    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = uploadDir.resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            UrlResource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType;
            if (fileName.toLowerCase().endsWith(".pdf")) {
                contentType = "application/pdf";
            } else {
                contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }


}
    @PatchMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String tradeMark,
            @RequestParam(required = false) Integer quantityAvailableBags,
            @RequestParam(required = false) Double netWeightPerBag,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double ratePerKgExCold,
            @RequestParam(required = false) String lotNo,
            @RequestParam(required = false) Double purchaseRate,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "labReport", required = false) MultipartFile labReport
    ) throws IOException {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();

        if (productName != null) product.setProductName(productName);
        if (tradeMark != null) product.setTradeMark(tradeMark);
        if (quantityAvailableBags != null) product.setQuantityAvailableBags(quantityAvailableBags);
        if (netWeightPerBag != null) product.setNetWeightPerBag(netWeightPerBag);
        if (location != null) product.setLocation(location);
        if (ratePerKgExCold != null) product.setRatePerKgExCold(ratePerKgExCold);
        if (lotNo != null) product.setLotNo(lotNo);
        if (purchaseRate != null) product.setPurchaseRate(purchaseRate);

        if (image != null && !image.isEmpty()) {
            product.setImagePath(saveFile(image));
        }
        if (labReport != null && !labReport.isEmpty()) {
            product.setLabReportPath(saveFile(labReport));
        }

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

}


