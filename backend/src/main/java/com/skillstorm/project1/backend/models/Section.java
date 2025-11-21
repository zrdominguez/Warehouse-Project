package com.skillstorm.project1.backend.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillstorm.project1.backend.dto.Product.ProductWithQuantity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "SECTIONS")
public class Section {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @JsonIgnore
    private Warehouse warehouse;

    //Might have to use a Enum for this or somehow
    //get it to relate to the product type
    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<SectionProduct> sectionProducts = new HashSet<>();

    @JsonProperty("products")
    public List<ProductWithQuantity> getProducts() {
        List<ProductWithQuantity> list = new ArrayList<>();

        for (SectionProduct sp : sectionProducts) {
            Product product = sp.getProduct();
            int quantity = sp.getQuantity();
            list.add(new ProductWithQuantity(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getProductType(),
                product.getDescription(), 
                quantity));
        }
        return list;
    }
    
    public Section() {
    }

    public Section(Warehouse warehouse, String name) {
        this.warehouse = warehouse;
        this.name = name;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public String getName() {
        return name;
    }

    public Set<SectionProduct> getSectionProducts() {
        return sectionProducts;
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

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Section)) return false;
        Section other = (Section) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Section [id=" + id + ", name=" + name + ", createdAt=" + createdAt + "]";
    }

    public void addProduct(Product product, int quantity){
        SectionProduct sectionProduct = new SectionProduct(this, product, quantity);
        sectionProducts.add(sectionProduct);
        product.getSectionProducts().add(sectionProduct);
    }

    public void removeProduct(Product product){
        sectionProducts.removeIf(sectionProduct -> sectionProduct.getProduct().equals(product));
        product.getSectionProducts().removeIf(sectionProduct -> sectionProduct.getSection().equals(this));
    }
}
