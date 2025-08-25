package com.ecommerce.service;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Product Service interface
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for product business logic operations
 * 
 * Follows Interface Segregation Principle:
 * - Clients depend only on methods they use
 * 
 * Follows Dependency Inversion Principle:
 * - High-level modules depend on this abstraction
 */
public interface ProductService {

    /**
     * Create a new product
     * 
     * @param product the product to create
     * @return the created product
     */
    Product createProduct(Product product);

    /**
     * Find product by ID
     * 
     * @param id the product ID
     * @return Optional containing the product if found
     */
    Optional<Product> findById(Long id);

    /**
     * Find product by SKU
     * 
     * @param sku the SKU
     * @return Optional containing the product if found
     */
    Optional<Product> findBySku(String sku);

    /**
     * Find product by barcode
     * 
     * @param barcode the barcode
     * @return Optional containing the product if found
     */
    Optional<Product> findByBarcode(String barcode);

    /**
     * Update product information
     * 
     * @param id the product ID
     * @param product the updated product information
     * @return the updated product
     */
    Product updateProduct(Long id, Product product);

    /**
     * Delete product by ID
     * 
     * @param id the product ID
     */
    void deleteProduct(Long id);

    /**
     * Get all products with pagination
     * 
     * @param pageable pagination information
     * @return Page of products
     */
    Page<Product> getAllProducts(Pageable pageable);

    /**
     * Get active products with pagination
     * 
     * @param pageable pagination information
     * @return Page of active products
     */
    Page<Product> getActiveProducts(Pageable pageable);

    /**
     * Get products by category
     * 
     * @param category the category to search for
     * @return List of products in the specified category
     */
    List<Product> getProductsByCategory(Product.ProductCategory category);

    /**
     * Get products by status
     * 
     * @param status the status to search for
     * @return List of products with the specified status
     */
    List<Product> getProductsByStatus(Product.ProductStatus status);

    /**
     * Get active products
     * 
     * @return List of active products
     */
    List<Product> getActiveProducts();

    /**
     * Get featured products
     * 
     * @return List of featured products
     */
    List<Product> getFeaturedProducts();

    /**
     * Get bestseller products
     * 
     * @return List of bestseller products
     */
    List<Product> getBestsellerProducts();

    /**
     * Get products in stock
     * 
     * @return List of products that are in stock
     */
    List<Product> getInStockProducts();

    /**
     * Get products by price range
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return List of products within the specified price range
     */
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Get products by brand
     * 
     * @param brand the brand to search for
     * @return List of products from the specified brand
     */
    List<Product> getProductsByBrand(String brand);

    /**
     * Get products by manufacturer
     * 
     * @param manufacturer the manufacturer to search for
     * @return List of products from the specified manufacturer
     */
    List<Product> getProductsByManufacturer(String manufacturer);

    /**
     * Search products by name or description
     * 
     * @param searchTerm the search term
     * @return List of products matching the search term
     */
    List<Product> searchProducts(String searchTerm);

    /**
     * Get products by rating range
     * 
     * @param minRating the minimum rating
     * @param maxRating the maximum rating
     * @return List of products within the specified rating range
     */
    List<Product> getProductsByRatingRange(BigDecimal minRating, BigDecimal maxRating);

    /**
     * Get top rated products
     * 
     * @param pageable pagination information
     * @return List of top rated products
     */
    List<Product> getTopRatedProducts(Pageable pageable);

    /**
     * Get most viewed products
     * 
     * @param pageable pagination information
     * @return List of most viewed products
     */
    List<Product> getMostViewedProducts(Pageable pageable);

    /**
     * Get best selling products
     * 
     * @param pageable pagination information
     * @return List of best selling products
     */
    List<Product> getBestSellingProducts(Pageable pageable);

    /**
     * Update product stock quantity
     * 
     * @param id the product ID
     * @param quantity the quantity to add/subtract
     * @return the updated product
     */
    Product updateStockQuantity(Long id, int quantity);

    /**
     * Increment product view count
     * 
     * @param id the product ID
     * @return the updated product
     */
    Product incrementViewCount(Long id);

    /**
     * Increment product sold count
     * 
     * @param id the product ID
     * @param quantity the quantity sold
     * @return the updated product
     */
    Product incrementSoldCount(Long id, int quantity);

    /**
     * Update product rating
     * 
     * @param id the product ID
     * @param rating the new rating
     * @return the updated product
     */
    Product updateRating(Long id, int rating);

    /**
     * Set product as featured
     * 
     * @param id the product ID
     * @param featured whether the product should be featured
     * @return the updated product
     */
    Product setFeatured(Long id, boolean featured);

    /**
     * Set product as bestseller
     * 
     * @param id the product ID
     * @param bestseller whether the product should be marked as bestseller
     * @return the updated product
     */
    Product setBestseller(Long id, boolean bestseller);

    /**
     * Check if product is in stock
     * 
     * @param id the product ID
     * @return true if product is in stock, false otherwise
     */
    boolean isInStock(Long id);

    /**
     * Check if SKU exists
     * 
     * @param sku the SKU to check
     * @return true if SKU exists, false otherwise
     */
    boolean existsBySku(String sku);

    /**
     * Check if barcode exists
     * 
     * @param barcode the barcode to check
     * @return true if barcode exists, false otherwise
     */
    boolean existsByBarcode(String barcode);

    /**
     * Get product count by category
     * 
     * @param category the category to count
     * @return number of products in the specified category
     */
    long countByCategory(Product.ProductCategory category);

    /**
     * Get product count by status
     * 
     * @param status the status to count
     * @return number of products with the specified status
     */
    long countByStatus(Product.ProductStatus status);

    /**
     * Get active product count
     * 
     * @return number of active products
     */
    long countActiveProducts();

    /**
     * Get in stock product count
     * 
     * @return number of products that are in stock
     */
    long countInStockProducts();
}
