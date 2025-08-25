package com.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Configuration class
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for JPA configuration
 * 
 * Follows Open/Closed Principle:
 * - Can be extended without modifying existing code
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.ecommerce.repository")
@EnableTransactionManagement
public class JpaConfig {
    // Configuration is handled by annotations
}
