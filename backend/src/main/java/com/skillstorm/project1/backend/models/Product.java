package com.skillstorm.project1.backend.models;

import java.time.LocalDateTime;

import java.util.UUID;

import com.skillstorm.project1.backend.models.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10)
    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40, name = "product_type")
    private ProductType productType;
    
    @Column(nullable = false)
    private String description;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public Product(String name, ProductType productType, String description) {
        this.name = name;
        this.productType = productType;
        this.description = description;
    }

    public Product() {
    }

    //When Entity is associated with the DB run this
    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
        // Used PRD for Product - may change later to have the prefix for product type instead
        this.sku = "PRD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    //When Entity is updated, change updatedAt time
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", sku=" + sku + ", productType=" + productType
        + ", description=" + description + ", createdAt=" + createdAt + "]";
    }

    
}