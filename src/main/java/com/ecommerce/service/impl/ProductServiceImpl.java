package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        // Set default values for new products
        if (product.getStatus() == null) {
            product.setStatus(Product.ProductStatus.ACTIVE);
        }
        if (product.getRatingAverage() == null) {
            product.setRatingAverage(BigDecimal.ZERO);
        }
        if (product.getRatingCount() == null) {
            product.setRatingCount(0);
        }
        if (product.getViewCount() == null) {
            product.setViewCount(0);
        }
        if (product.getSoldCount() == null) {
            product.setSoldCount(0);
        }
        // featured and bestseller are already initialized to false in the entity
        
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    public Optional<Product> findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        
        // Update fields
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setOriginalPrice(product.getOriginalPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setSku(product.getSku());
        existingProduct.setBarcode(product.getBarcode());
        existingProduct.setWeightKg(product.getWeightKg());
        existingProduct.setDimensionsCm(product.getDimensionsCm());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setModel(product.getModel());
        existingProduct.setManufacturer(product.getManufacturer());
        existingProduct.setWarrantyMonths(product.getWarrantyMonths());
        existingProduct.setFeatured(product.isFeatured());
        existingProduct.setBestseller(product.isBestseller());
        
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setStatus(Product.ProductStatus.DISCONTINUED);
        productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getActiveProducts(Pageable pageable) {
        return productRepository.findActiveProducts(pageable);
    }

    @Override
    public List<Product> getProductsByCategory(Product.ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByStatus(Product.ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findActiveProducts();
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }

    @Override
    public List<Product> getBestsellerProducts() {
        return productRepository.findBestsellerProducts();
    }

    @Override
    public List<Product> getInStockProducts() {
        return productRepository.findInStockProducts();
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByManufacturer(String manufacturer) {
        return productRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<Product> searchProducts(String searchTerm) {
        return productRepository.searchProducts(searchTerm);
    }

    @Override
    public List<Product> getProductsByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        return productRepository.findByRatingRange(minRating, maxRating);
    }

    @Override
    public List<Product> getTopRatedProducts(Pageable pageable) {
        return productRepository.findTopRatedProducts(pageable);
    }

    @Override
    public List<Product> getMostViewedProducts(Pageable pageable) {
        return productRepository.findMostViewedProducts(pageable);
    }

    @Override
    public List<Product> getBestSellingProducts(Pageable pageable) {
        return productRepository.findBestSellingProducts(pageable);
    }

    @Override
    public Product updateStockQuantity(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.updateStockQuantity(quantity);
        return productRepository.save(product);
    }

    @Override
    public Product incrementViewCount(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.incrementViewCount();
        return productRepository.save(product);
    }

    @Override
    public Product incrementSoldCount(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.incrementSoldCount(quantity);
        return productRepository.save(product);
    }

    @Override
    public Product updateRating(Long id, int rating) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        // Calculate new average rating
        BigDecimal currentAverage = product.getRatingAverage();
        Integer currentCount = product.getRatingCount();
        
        if (currentCount == 0) {
            product.setRatingAverage(new BigDecimal(rating));
            product.setRatingCount(1);
        } else {
            BigDecimal totalRating = currentAverage.multiply(new BigDecimal(currentCount)).add(new BigDecimal(rating));
            product.setRatingCount(currentCount + 1);
            product.setRatingAverage(totalRating.divide(new BigDecimal(product.getRatingCount()), 2, BigDecimal.ROUND_HALF_UP));
        }
        
        return productRepository.save(product);
    }

    @Override
    public Product setFeatured(Long id, boolean featured) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setFeatured(featured);
        return productRepository.save(product);
    }

    @Override
    public Product setBestseller(Long id, boolean bestseller) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setBestseller(bestseller);
        return productRepository.save(product);
    }

    @Override
    public boolean isInStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        return product.isInStock();
    }

    @Override
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return productRepository.existsByBarcode(barcode);
    }

    @Override
    public long countByCategory(Product.ProductCategory category) {
        return productRepository.countByCategory(category);
    }

    @Override
    public long countByStatus(Product.ProductStatus status) {
        return productRepository.countByStatus(status);
    }

    @Override
    public long countActiveProducts() {
        return productRepository.countActiveProducts();
    }

    @Override
    public long countInStockProducts() {
        return productRepository.countInStockProducts();
    }
}
