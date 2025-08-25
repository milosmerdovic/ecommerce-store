package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ProductReview entity representing product reviews
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for product review data representation
 */
@Entity
@Table(name = "product_reviews")
@EntityListeners(AuditingEntityListener.class)
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    @Column(nullable = false)
    private Integer rating;

    @Size(max = 1000, message = "Review title cannot exceed 1000 characters")
    @Column(name = "review_title")
    private String reviewTitle;

    @Size(max = 5000, message = "Review content cannot exceed 5000 characters")
    @Column(name = "review_content", columnDefinition = "TEXT")
    private String reviewContent;

    @Column(name = "is_verified_purchase")
    private boolean verifiedPurchase = false;

    @Column(name = "is_helpful_count")
    private Integer helpfulCount = 0;

    @Column(name = "is_not_helpful_count")
    private Integer notHelpfulCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status = ReviewStatus.PENDING;

    @Column(name = "admin_notes")
    private String adminNotes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public ProductReview() {}

    public ProductReview(Product product, User user, Integer rating) {
        this.product = product;
        this.user = user;
        this.rating = rating;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public boolean isVerifiedPurchase() {
        return verifiedPurchase;
    }

    public void setVerifiedPurchase(boolean verifiedPurchase) {
        this.verifiedPurchase = verifiedPurchase;
    }

    public Integer getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(Integer helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public Integer getNotHelpfulCount() {
        return notHelpfulCount;
    }

    public void setNotHelpfulCount(Integer notHelpfulCount) {
        this.notHelpfulCount = notHelpfulCount;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
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
    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void incrementNotHelpfulCount() {
        this.notHelpfulCount++;
    }

    public boolean isApproved() {
        return ReviewStatus.APPROVED.equals(status);
    }

    public boolean isRejected() {
        return ReviewStatus.REJECTED.equals(status);
    }

    public boolean isPending() {
        return ReviewStatus.PENDING.equals(status);
    }

    public String getDisplayTitle() {
        return reviewTitle != null && !reviewTitle.trim().isEmpty() ? reviewTitle : "Review";
    }

    // Enum definition
    public enum ReviewStatus {
        PENDING, APPROVED, REJECTED, SPAM
    }
}
