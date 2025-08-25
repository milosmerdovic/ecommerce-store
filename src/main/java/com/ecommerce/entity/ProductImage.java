package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ProductImage entity representing product images
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for product image data representation
 */
@Entity
@Table(name = "product_images")
@EntityListeners(AuditingEntityListener.class)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Size(max = 255, message = "Alt text cannot exceed 255 characters")
    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private boolean isPrimary = false;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Size(max = 100, message = "Image type cannot exceed 100 characters")
    @Column(name = "image_type")
    private String imageType;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "width_pixels")
    private Integer widthPixels;

    @Column(name = "height_pixels")
    private Integer heightPixels;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public ProductImage() {}

    public ProductImage(Product product, String imageUrl) {
        this.product = product;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public Integer getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(Integer widthPixels) {
        this.widthPixels = widthPixels;
    }

    public Integer getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(Integer heightPixels) {
        this.heightPixels = heightPixels;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Business methods
    public String getDisplayName() {
        return altText != null && !altText.trim().isEmpty() ? altText : "Product Image";
    }

    public boolean isThumbnail() {
        return "thumbnail".equalsIgnoreCase(imageType);
    }

    public boolean isLarge() {
        return "large".equalsIgnoreCase(imageType);
    }
}
