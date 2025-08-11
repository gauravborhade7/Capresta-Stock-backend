package com.capresta.stock_portal.StockPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StockPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPortalApplication.class, args);
	}

}
