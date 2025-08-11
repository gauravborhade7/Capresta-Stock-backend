package com.capresta.stock_portal.StockPortal.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ Security configuration (your original)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // ✅ Static resource handler for uploaded files
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fullPath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/assets/**")
                .addResourceLocations(fullPath);
    }

    // ✅ Add this method for global CORS config
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // allow all paths
//                .allowedOrigins("http://localhost:5173") // allow frontend origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allow common methods
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://stock.caprestaindia.com", "http://stock.caprestaindia.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
