package com.capresta.stock_portal.StockPortal.Controller;

import com.capresta.stock_portal.StockPortal.Repository.ProductRepository;
import com.capresta.stock_portal.StockPortal.mailsender.MailProductDto;
import com.capresta.stock_portal.StockPortal.mailsender.MailService;
import com.capresta.stock_portal.StockPortal.mailsender.PreorderRequest;
import com.capresta.stock_portal.StockPortal.mailsender.ProductsDetails;
import com.capresta.stock_portal.StockPortal.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class MailController {

    @Autowired
    private MailService emailService;


@PostMapping("/send")
public ResponseEntity<String> sendPreorderEmail(@RequestBody PreorderRequest preorderRequest) {
    try {
        String mobile = preorderRequest.getMobile();
        List<ProductsDetails> products = preorderRequest.getProducts();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("📱 Mobile Number: ").append(mobile).append("\n\n");
        messageBuilder.append("🛒 Ordered Products:\n");

        for (ProductsDetails product : products) {
            messageBuilder.append("- Product Name : ").append(product.getProductName()).append("\n");
            messageBuilder.append("  Trade Mark : ").append(product.getTradeMark()).append("\n");
            messageBuilder.append("  Quantity : ").append(product.getQuantityAvailableBags()).append(" bags\n");
            messageBuilder.append("  Weight/Bag : ").append(product.getNetWeightPerBag()).append(" kg\n");
            messageBuilder.append("  Location : ").append(product.getLocation()).append("\n");
            messageBuilder.append("  Lot no : ").append(product.getLotNo()).append("\n");
            messageBuilder.append("  Rate Per Kg Ex Cold : ").append(product.getRatePerKgExCold()).append("\n\n");


        }

        // Subject will now include the mobile number
        String subject = "Pre-Order from Mobile: " + mobile;

        emailService.sendEmail(
                "caprestain@gmail.com",
                subject,
                messageBuilder.toString()
        );

        return ResponseEntity.ok("✅ Preorder email sent successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("❌ Failed to send preorder email: " + e.getMessage());
    }
}


}
