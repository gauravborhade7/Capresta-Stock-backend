package com.capresta.stock_portal.StockPortal.Controller;
import com.capresta.stock_portal.StockPortal.Repository.ProductRepository;
import com.capresta.stock_portal.StockPortal.model.Product;
import com.capresta.stock_portal.StockPortal.Service.ProductService;
import com.capresta.stock_portal.StockPortal.model.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Allow frontend calls from anywhere
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Product product;

    @Autowired
    private ProductResponseDTO dto;
    @Autowired
    private ProductRepository productRepository;

//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }

//@GetMapping("/name/{name}/location/{location}")
//public ResponseEntity<ProductResponseDTO> getProductWithImage(@PathVariable String name,@PathVariable String location) {
//    Optional<Product> optionalProduct = productService.findByProductNameIgnoreCaseAndLocationIgnoreCase(name, location);
//
//    if (optionalProduct.isPresent()) {
//        Product product = optionalProduct.get();
//
//        ProductResponseDTO dto = new ProductResponseDTO();
//        dto.setId(product.getId());
//        dto.setProductName(product.getProductName());
//        dto.setBrandName(product.getTradeMark());
//        dto.setLotNo(product.getLotNo());
//        dto.setStockQuantity(product.getNetWeightPerBag());
//
//        if (product.getLabReport() != null) {
//            String base64Image = Base64.getEncoder().encodeToString(product.getLabReport());
//            dto.setLabReportBase64(base64Image);
//        }
//
//        return ResponseEntity.ok(dto);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}

//    @GetMapping("/name/{name}/location/{location}")
//    public ResponseEntity<ProductResponseDTO> getProductWithImage(@PathVariable String name, @PathVariable String location) {
//        Optional<Product> optionalProduct = productService.findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);
//
//        if (optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//
//            ProductResponseDTO dto = new ProductResponseDTO();
//            dto.setId(product.getId());
//            dto.setProductName(product.getProductName());
//            dto.setBrandName(product.getTradeMark());
//            dto.setLotNo(product.getLotNo());
//            dto.setLocation(product.getLocation());
//
//            dto.setStockQuantity(product.getQuantityAvailableBags()); // Or getNetWeightPerBag()
//
//            // ✅ Add missing fields
//            dto.setRatePerKgExCold(product.getRatePerKgExCold());
//
//            // ✅ Convert product image to base64 if not null
//            if (product.getViewProduct() != null) {
//                String base64ViewImage = Base64.getEncoder().encodeToString(product.getViewProduct());
//                dto.setViewProduct(base64ViewImage);
//            }
//
//            // ✅ Convert lab report to base64
////            if (product.getLabReport() != null) {
////                String base64LabReport = Base64.getEncoder().encodeToString(product.getLabReport());
////                dto.setLabReportBase64(base64LabReport);
////            }
//
//
//            return ResponseEntity.ok(dto);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

@GetMapping("/name/{name}/location/{location}")
public ResponseEntity<List<ProductResponseDTO>> getMatchingProducts(
        @PathVariable String name,
        @PathVariable String location) {

    List<Product> matchingProducts = productService.findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);

    if (matchingProducts.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    List<ProductResponseDTO> dtoList = matchingProducts.stream().map(product -> {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setBrandName(product.getTradeMark());
        dto.setLotNo(product.getLotNo());
        dto.setLocation(product.getLocation());
        dto.setStockQuantity(product.getQuantityAvailableBags());
        dto.setRatePerKgExCold(product.getRatePerKgExCold());

        // View Product (Image)
//        if (product.getViewProduct() != null) {
//            String base64ViewImage = Base64.getEncoder().encodeToString(product.getViewProduct());
//            dto.setViewProduct(base64ViewImage);
//        }

        // Lab Report (PDF or similar)
//        if (product.getLabReport() != null) {
//            String base64LabReport = Base64.getEncoder().encodeToString(product.getLabReport());
//            dto.setLabReportBase64(base64LabReport);
//        }

        return dto;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(dtoList);
}


//    @GetMapping("/{name}/{location}/image")
//    public ResponseEntity<byte[]> getProductImage(@PathVariable String name, @PathVariable String location) {
//        List<Product> products = productRepository.findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found with name: " + name + " and location: " + location);
//        }
//        byte[] image = product.getViewProduct(); // Make sure this returns byte[]
//        if (product.getViewProduct() != null) {
//            String base64ViewImage = Base64.getEncoder().encodeToString(product.getViewProduct());
//            dto.setViewProduct(base64ViewImage);
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<>(image, headers, HttpStatus.OK);
//    }

//    @GetMapping("/{name}/{location}/labreport")
//    public ResponseEntity<byte[]> getLabReport(@PathVariable String name, @PathVariable String location) {
//        List<Product> products = productRepository
//                .findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found with name: " + name + " and location: " + location);
//        }
//
//        byte[] report = product.getLabReport(); // Ensure this returns byte[]
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        return new ResponseEntity<>(report, headers, HttpStatus.OK);
//    }

//    @GetMapping("/images/{name}/{location}")
//    public ResponseEntity<List<String>> getAllImagesByNameAndLocation(
//            @PathVariable String name,
//            @PathVariable String location) {
//
//        List<Product> products = productRepository.findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);
//
//        if (products.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        List<String> base64Images = products.stream()
//                .filter(p -> p.getViewProduct() != null)
//                .map(p -> Base64.getEncoder().encodeToString(p.getViewProduct()))
//                .toList();
//
//        return ResponseEntity.ok(base64Images);
//    }

    @GetMapping("/details/{name}/{location}")
    public ResponseEntity<List<Map<String, Object>>> getProductDetailsWithImages(
            @PathVariable String name,
            @PathVariable String location) {

        List<Product> products = productRepository
                .findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Product product : products) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("productName", product.getProductName());
            productMap.put("brandName", product.getTradeMark());
            productMap.put("stockQuantity", product.getQuantityAvailableBags());
            productMap.put("netWeightPerBag", product.getNetWeightPerBag());
            productMap.put("location", product.getLocation());
            productMap.put("lotNo", product.getLotNo());
            productMap.put("ratePerKgExCold", product.getRatePerKgExCold());

            if (product.getViewProduct() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getViewProduct());
                productMap.put("image", base64Image);
            }

            if (product.getLabReport() != null) {
                String base64Pdf = Base64.getEncoder().encodeToString(product.getLabReport());
                productMap.put("labReport", base64Pdf);
            }

            responseList.add(productMap);
        }

        return ResponseEntity.ok(responseList);
    }


    @GetMapping("/{name}/{location}/labreports")
    public ResponseEntity<List<String>> getAllLabReports(@PathVariable String name, @PathVariable String location) {
        List<Product> products = productRepository
                .findByProductNameContainingIgnoreCaseAndLocationIgnoreCase(name, location);

        if (products.isEmpty()) {
            throw new RuntimeException("No products found with name: " + name + " and location: " + location);
        }

        List<String> reportsBase64 = products.stream()
                .map(Product::getLabReport)
                .filter(Objects::nonNull)
                .map(reportBytes -> Base64.getEncoder().encodeToString(reportBytes))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reportsBase64);
    }






//@GetMapping("/name/{name}/location/{location}")
//public ResponseEntity<ProductResponseDTO> getProductWithImage(@PathVariable String name,@PathVariable String location) {
//    Optional<Product> optionalProduct = productService.findByProductNameIgnoreCaseAndLocationIgnoreCase(name,location);
//
//    if (optionalProduct.isPresent()) {
//        Product product = optionalProduct.get();
//
//        ProductResponseDTO dto = new ProductResponseDTO();
//        dto.setId(product.getId());
//        dto.setProductName(product.getProductName());
//        dto.setBrandName(product.getTradeMark());
//        dto.setLotNo(product.getLotNo());
//        dto.setStockQuantity(product.getNetWeightPerBag());
//        dto.setStockQuantity(product.getViewProduct());
//
//        if (product.getLabReport() != null) {
//            String base64Image = Base64.getEncoder().encodeToString(product.getLabReport());
//            dto.setLabReportBase64(base64Image);
//        }
//
//        return ResponseEntity.ok(dto);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}
//       @GetMapping("/name/{name}/location/{location}")
//    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name,@PathVariable String location) {
//        List<Product> products = productService.findByProductNameIgnoreCaseAndLocationIgnoreCase(name,location);
//        if (products.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(products);
//    }


//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        return ResponseEntity.ok(updateProduct(product));

@PostMapping("/add")
public ResponseEntity<Product> createProduct(
        @RequestParam("productName") String productName,
        @RequestParam("tradeMark") String tradeMark,
        @RequestParam("quantityAvailableBags") int quantityAvailableBags,
        @RequestParam("netWeightPerBag") double netWeightPerBag,
        @RequestParam("location") String location,
        @RequestParam("lotNo") String lotNo,
        @RequestParam("purchaseRate") double purchaseRate,
        @RequestParam("ratePerKgExCold") double ratePerKgExCold,
    @RequestParam(value = "viewProduct", required = false) MultipartFile viewProduct,
    @RequestParam(value = "labReport", required = false) MultipartFile labReport
) throws IOException {
    Product product = new Product();
    product.setProductName(productName);
    product.setTradeMark(tradeMark);
    product.setQuantityAvailableBags(quantityAvailableBags);
    product.setNetWeightPerBag(netWeightPerBag);
    product.setLocation(location);
    product.setLotNo(lotNo);
    product.setPurchaseRate(purchaseRate);
    product.setRatePerKgExCold(ratePerKgExCold);
    if (viewProduct != null && !viewProduct.isEmpty()) {
        product.setViewProduct(viewProduct.getBytes());
    }
    if (labReport != null && !labReport.isEmpty()) {
        product.setLabReport(labReport.getBytes());
    }


    return ResponseEntity.ok(productService.addProduct(product, viewProduct,labReport));
}

//    @PostMapping("/add")
//    public ResponseEntity<Product> createProduct(
//            @RequestParam("productName") String productName,
//            @RequestParam("tradeMark") String tradeMark,
//            @RequestParam("quantityAvailableBags") int quantityAvailableBags,
//            @RequestParam("netWeightPerBag") double netWeightPerBag,
//            @RequestParam("location") String location,
//            @RequestParam("lotNo") String lotNo,
//            @RequestParam("purchaseRate") double purchaseRate,
//            @RequestParam("ratePerKgExCold") double ratePerKgExCold,
//            @RequestParam(value = "viewProduct", required = false) MultipartFile viewProduct,
//            @RequestParam(value = "labReport", required = false) MultipartFile labReport
//    ) {
//        Product product = new Product();
//        product.setProductName(productName);
//        product.setTradeMark(tradeMark);
//        product.setQuantityAvailableBags(quantityAvailableBags);
//        product.setNetWeightPerBag(netWeightPerBag); // ✅ this now accepts decimals
//        product.setLocation(location);
//        product.setLotNo(lotNo);
//        product.setPurchaseRate(purchaseRate);
//        product.setRatePerKgExCold(ratePerKgExCold);
//
//        return ResponseEntity.ok(productService.addProduct(product, viewProduct, labReport));
//    }


//@PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        Product updated = productService.updateProduct(id, product);
//        if (updated != null)
//            return ResponseEntity.ok(updated);
//        else
//            return ResponseEntity.notFound().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("productName") String productName,
            @RequestParam("tradeMark") String tradeMark,
            @RequestParam("quantityAvailableBags") int quantityAvailableBags,
            @RequestParam("netWeightPerBag") double netWeightPerBag,
            @RequestParam("location") String location,
            @RequestParam("lotNo") String lotNo,
            @RequestParam("purchaseRate") double purchaseRate,
            @RequestParam("ratePerKgExCold") double ratePerKgExCold,
            @RequestParam(value = "viewProduct", required = false) MultipartFile viewProduct,
            @RequestParam(value = "labReport", required = false) MultipartFile labReport
    ) throws IOException {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product productToUpdate = productOptional.get();
        // Update fields
        productToUpdate.setProductName(productName);
        productToUpdate.setTradeMark(tradeMark);
        productToUpdate.setQuantityAvailableBags(quantityAvailableBags);
        productToUpdate.setNetWeightPerBag(netWeightPerBag);
        productToUpdate.setLocation(location);
        productToUpdate.setLotNo(lotNo);
        productToUpdate.setPurchaseRate(purchaseRate);
        productToUpdate.setRatePerKgExCold(ratePerKgExCold);

        // Update files only if provided
        if (viewProduct != null && !viewProduct.isEmpty()) {
            productToUpdate.setViewProduct(viewProduct.getBytes());
        }
        if (labReport != null && !labReport.isEmpty()) {
            productToUpdate.setLabReport(labReport.getBytes());
        }

        Product updated = productService.updateProduct(id, productToUpdate);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
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
}
