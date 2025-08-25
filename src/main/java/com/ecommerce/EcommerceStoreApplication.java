package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Ecommerce Store
 * 
 * This application follows SOLID principles and provides:
 * - RESTful API endpoints
 * - JPA-based data persistence
 * - Spring Security for authentication
 * - ELK stack integration for logging
 * - Docker containerization support
 */
@SpringBootApplication
public class EcommerceStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceStoreApplication.class, args);
    }
}
