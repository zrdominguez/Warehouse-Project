package com.skillstorm.project1.backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "SECTIONS_PRODUCTS")
public class SectionProduct {

    @EmbeddedId
    private SectionProductId id;

    @ManyToOne
    @MapsId("sectionId")
    @JoinColumn(name = "section_id")
    private Section section;
    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(0)
    @Column(nullable = false)
    private int quantity = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public SectionProduct() {
    }

    public SectionProduct(Section section, Product product, int quantity) {
        this.section = section;
        this.product = product;
        this.quantity = quantity;
        this.id = new SectionProductId(section.getId(), product.getId());
    }

    @Embeddable
    public static class SectionProductId implements Serializable{
        
        @Column(name = "section_id")
        private int sectionId;

        @Column(name = "product_id")
        private int productId;

        public SectionProductId(){
        }

        public SectionProductId(int sectionId, int productId){
            this.sectionId = sectionId;
            this.productId = productId;
        }

        public int getSectionId(){
            return sectionId;
        }

        public int getProductId(){
            return productId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sectionId, productId);
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;
            if(!(obj instanceof SectionProductId)) return false;
            SectionProductId other = (SectionProductId) obj;
            return sectionId == other.sectionId && productId == other.productId;
        }
    }


    public SectionProductId getId() {
        return id;
    }

    public Section getSection() {
        return section;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, product);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof SectionProduct)) return false;
        SectionProduct other = (SectionProduct) obj;
        return Objects.equals(section, other.section) &&
               Objects.equals(product, other.product);
    }
}