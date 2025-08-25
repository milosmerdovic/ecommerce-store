package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for product management
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for handling HTTP requests related to products
 * 
 * Follows Open/Closed Principle:
 * - Can be extended without modifying existing code
 */
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management", description = "APIs for managing products in the e-commerce store")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the store")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data"),
        @ApiResponse(responseCode = "409", description = "Product with SKU or barcode already exists")
    })
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductById(@Parameter(description = "Product ID") @PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get product by SKU", description = "Retrieves a product by its SKU")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductBySku(@Parameter(description = "Product SKU") @PathVariable String sku) {
        return productService.findBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Get product by barcode", description = "Retrieves a product by its barcode")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductByBarcode(@Parameter(description = "Product barcode") @PathVariable String barcode) {
        return productService.findByBarcode(barcode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> updateProduct(@Parameter(description = "Product ID") @PathVariable Long id,
                                               @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Soft deletes a product by changing its status to DISCONTINUED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@Parameter(description = "Product ID") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<Product>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") String sortDir) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products", description = "Retrieves all active products with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Active products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<Product>> getActiveProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getActiveProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieves all products in a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByCategory(
            @Parameter(description = "Product category") @PathVariable Product.ProductCategory category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get products by status", description = "Retrieves all products with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByStatus(
            @Parameter(description = "Product status") @PathVariable Product.ProductStatus status) {
        List<Product> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured products", description = "Retrieves all featured products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        List<Product> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/bestsellers")
    @Operation(summary = "Get bestseller products", description = "Retrieves all bestseller products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bestseller products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getBestsellerProducts() {
        List<Product> products = productService.getBestsellerProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/in-stock")
    @Operation(summary = "Get in-stock products", description = "Retrieves all products that are currently in stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "In-stock products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> products = productService.getInStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieves products within a specified price range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get products by brand", description = "Retrieves all products from a specific brand")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByBrand(
            @Parameter(description = "Brand name") @PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    @Operation(summary = "Get products by manufacturer", description = "Retrieves all products from a specific manufacturer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByManufacturer(
            @Parameter(description = "Manufacturer name") @PathVariable String manufacturer) {
        List<Product> products = productService.getProductsByManufacturer(manufacturer);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Searches products by name or description")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products found",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String q) {
        List<Product> products = productService.searchProducts(q);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/rating-range")
    @Operation(summary = "Get products by rating range", description = "Retrieves products within a specified rating range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getProductsByRatingRange(
            @Parameter(description = "Minimum rating") @RequestParam BigDecimal minRating,
            @Parameter(description = "Maximum rating") @RequestParam BigDecimal maxRating) {
        List<Product> products = productService.getProductsByRatingRange(minRating, maxRating);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-rated")
    @Operation(summary = "Get top rated products", description = "Retrieves top rated products with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Top rated products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getTopRatedProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productService.getTopRatedProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/most-viewed")
    @Operation(summary = "Get most viewed products", description = "Retrieves most viewed products with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Most viewed products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getMostViewedProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productService.getMostViewedProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/best-selling")
    @Operation(summary = "Get best selling products", description = "Retrieves best selling products with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Best selling products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    public ResponseEntity<List<Product>> getBestSellingProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productService.getBestSellingProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> updateStockQuantity(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Quantity to add/subtract") @RequestParam int quantity) {
        Product updatedProduct = productService.updateStockQuantity(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/view-count")
    @Operation(summary = "Increment view count", description = "Increments the view count of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "View count incremented successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> incrementViewCount(@Parameter(description = "Product ID") @PathVariable Long id) {
        Product updatedProduct = productService.incrementViewCount(id);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/sold-count")
    @Operation(summary = "Increment sold count", description = "Increments the sold count of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sold count incremented successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> incrementSoldCount(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Quantity sold") @RequestParam int quantity) {
        Product updatedProduct = productService.incrementSoldCount(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/rating")
    @Operation(summary = "Update product rating", description = "Updates the average rating of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rating updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> updateRating(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "New rating") @RequestParam int rating) {
        Product updatedProduct = productService.updateRating(id, rating);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/featured")
    @Operation(summary = "Set featured status", description = "Sets whether a product is featured or not")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured status updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> setFeatured(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Whether product should be featured") @RequestParam boolean featured) {
        Product updatedProduct = productService.setFeatured(id, featured);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}/bestseller")
    @Operation(summary = "Set bestseller status", description = "Sets whether a product is a bestseller or not")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bestseller status updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> setBestseller(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Whether product should be marked as bestseller") @RequestParam boolean bestseller) {
        Product updatedProduct = productService.setBestseller(id, bestseller);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{id}/in-stock")
    @Operation(summary = "Check if product is in stock", description = "Checks whether a product is currently in stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock status checked successfully")
    })
    public ResponseEntity<Boolean> isInStock(@Parameter(description = "Product ID") @PathVariable Long id) {
        boolean inStock = productService.isInStock(id);
        return ResponseEntity.ok(inStock);
    }

    @GetMapping("/stats")
    @Operation(summary = "Get product statistics", description = "Retrieves various product statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductStats.class)))
    })
    public ResponseEntity<ProductStats> getProductStats() {
        ProductStats stats = new ProductStats();
        stats.setTotalProducts(productService.countActiveProducts());
        stats.setInStockProducts(productService.countInStockProducts());
        stats.setFeaturedProducts(productService.getFeaturedProducts().size());
        stats.setBestsellerProducts(productService.getBestsellerProducts().size());
        return ResponseEntity.ok(stats);
    }

    /**
     * DTO for product statistics
     */
    public static class ProductStats {
        private long totalProducts;
        private long inStockProducts;
        private long featuredProducts;
        private long bestsellerProducts;

        // Getters and Setters
        public long getTotalProducts() {
            return totalProducts;
        }

        public void setTotalProducts(long totalProducts) {
            this.totalProducts = totalProducts;
        }

        public long getInStockProducts() {
            return inStockProducts;
        }

        public void setInStockProducts(long inStockProducts) {
            this.inStockProducts = inStockProducts;
        }

        public long getFeaturedProducts() {
            return featuredProducts;
        }

        public void setFeaturedProducts(long featuredProducts) {
            this.featuredProducts = featuredProducts;
        }

        public long getBestsellerProducts() {
            return bestsellerProducts;
        }

        public void setBestsellerProducts(long bestsellerProducts) {
            this.bestsellerProducts = bestsellerProducts;
        }
    }
}
