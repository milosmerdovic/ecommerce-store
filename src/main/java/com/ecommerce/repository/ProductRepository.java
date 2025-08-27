package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Product Repository interface
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for product data access operations
 * 
 * Follows Interface Segregation Principle:
 * - Clients depend only on methods they use
 * 
 * Follows Dependency Inversion Principle:
 * - High-level modules depend on this abstraction
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find product by SKU
     * 
     * @param sku the SKU to search for
     * @return Optional containing the product if found
     */
    Optional<Product> findBySku(String sku);

    /**
     * Find product by barcode
     * 
     * @param barcode the barcode to search for
     * @return Optional containing the product if found
     */
    Optional<Product> findByBarcode(String barcode);

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
     * Find products by category
     * 
     * @param category the category to search for
     * @return List of products in the specified category
     */
    List<Product> findByCategory(Product.ProductCategory category);

    /**
     * Find products by status
     * 
     * @param status the status to search for
     * @return List of products with the specified status
     */
    List<Product> findByStatus(Product.ProductStatus status);

    /**
     * Find active products
     * 
     * @return List of active products
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
    List<Product> findActiveProducts();

    /**
     * Find featured products
     * 
     * @return List of featured products
     */
    @Query("SELECT p FROM Product p WHERE p.featured = true AND p.status = 'ACTIVE'")
    List<Product> findFeaturedProducts();

    /**
     * Find bestseller products
     * 
     * @return List of bestseller products
     */
    @Query("SELECT p FROM Product p WHERE p.bestseller = true AND p.status = 'ACTIVE'")
    List<Product> findBestsellerProducts();

    /**
     * Find products in stock
     * 
     * @return List of products that are in stock
     */
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 AND p.status = 'ACTIVE'")
    List<Product> findInStockProducts();

    /**
     * Find products by price range
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return List of products within the specified price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.status = 'ACTIVE'")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                  @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Find products by brand
     * 
     * @param brand the brand to search for
     * @return List of products from the specified brand
     */
    List<Product> findByBrand(String brand);

    /**
     * Find products by manufacturer
     * 
     * @param manufacturer the manufacturer to search for
     * @return List of products from the specified manufacturer
     */
    List<Product> findByManufacturer(String manufacturer);

    /**
     * Search products by name or description
     * 
     * @param searchTerm the search term
     * @return List of products matching the search term
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "AND p.status = 'ACTIVE'")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    /**
     * Find products with pagination
     * 
     * @param pageable pagination information
     * @return Page of products
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * Find products by category with pagination
     * 
     * @param category the category to search for
     * @param pageable pagination information
     * @return Page of products in the specified category
     */
    Page<Product> findByCategory(Product.ProductCategory category, Pageable pageable);

    /**
     * Find products by status with pagination
     * 
     * @param status the status to search for
     * @param pageable pagination information
     * @return Page of products with the specified status
     */
    Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * Find active products with pagination
     * 
     * @param pageable pagination information
     * @return Page of active products
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
    Page<Product> findActiveProducts(Pageable pageable);

    /**
     * Find products by rating range
     * 
     * @param minRating the minimum rating
     * @param maxRating the maximum rating
     * @return List of products within the specified rating range
     */
    @Query("SELECT p FROM Product p WHERE p.ratingAverage BETWEEN :minRating AND :maxRating AND p.status = 'ACTIVE'")
    List<Product> findByRatingRange(@Param("minRating") BigDecimal minRating, 
                                   @Param("maxRating") BigDecimal maxRating);

    /**
     * Find top rated products
     * 
     * @param limit the number of products to return
     * @return List of top rated products
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.ratingAverage DESC, p.ratingCount DESC")
    List<Product> findTopRatedProducts(Pageable pageable);

    /**
     * Find most viewed products
     * 
     * @param limit the number of products to return
     * @return List of most viewed products
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.viewCount DESC")
    List<Product> findMostViewedProducts(Pageable pageable);

    /**
     * Find best selling products
     * 
     * @param limit the number of products to return
     * @return List of best selling products
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.soldCount DESC")
    List<Product> findBestSellingProducts(Pageable pageable);

    /**
     * Count products by category
     * 
     * @param category the category to count
     * @return number of products in the specified category
     */
    long countByCategory(Product.ProductCategory category);

    /**
     * Count products by status
     * 
     * @param status the status to count
     * @return number of products with the specified status
     */
    long countByStatus(Product.ProductStatus status);

    /**
     * Count active products
     * 
     * @return number of active products
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.status = 'ACTIVE'")
    long countActiveProducts();

    /**
     * Count products in stock
     * 
     * @return number of products that are in stock
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stockQuantity > 0 AND p.status = 'ACTIVE'")
    long countInStockProducts();
}
