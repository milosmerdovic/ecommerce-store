package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Address entity representing user addresses
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for address data representation
 */
@Entity
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address cannot exceed 255 characters")
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Size(max = 100, message = "Street address 2 cannot exceed 100 characters")
    @Column(name = "street_address_2")
    private String streetAddress2;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State/Province is required")
    @Size(max = 100, message = "State/Province cannot exceed 100 characters")
    @Column(name = "state_province", nullable = false)
    private String stateProvince;

    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    @Column(nullable = false)
    private String country;

    @Size(max = 100, message = "Phone number cannot exceed 100 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_default")
    private boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType type = AddressType.SHIPPING;

    @Size(max = 255, message = "Additional notes cannot exceed 255 characters")
    @Column(name = "additional_notes")
    private String additionalNotes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Address() {}

    public Address(User user, String streetAddress, String city, String stateProvince, String postalCode, String country) {
        this.user = user;
        this.streetAddress = streetAddress;
        this.city = city;
        this.stateProvince = stateProvince;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
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
    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder(streetAddress);
        if (streetAddress2 != null && !streetAddress2.trim().isEmpty()) {
            fullAddress.append(", ").append(streetAddress2);
        }
        fullAddress.append(", ").append(city)
                  .append(", ").append(stateProvince)
                  .append(" ").append(postalCode)
                  .append(", ").append(country);
        return fullAddress.toString();
    }

    // Enum definition
    public enum AddressType {
        SHIPPING, BILLING, BOTH
    }
}
