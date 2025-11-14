package com.skillstorm.project1.backend.models;

import java.time.LocalDateTime;

import com.skillstorm.project1.backend.models.enums.WarehouseType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "WAREHOUSES")
public class Warehouse {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name = "Untitled";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40, name = "warehouse_type")
    private WarehouseType warehouseType;

    @Column
    private int capacity = 0;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Warehouse() {
    }
    
    public Warehouse(User user, String name, WarehouseType warehouseType, String location) {
        this.user = user;
        this.name = name;
        this.warehouseType = warehouseType;
        this.location = location;
    }
    
    public Warehouse(User user, String name, WarehouseType warehouseType, int capacity, String location) {
        this.user = user;
        this.name = name;
        this.warehouseType = warehouseType;
        this.capacity = capacity;
        this.location = location;
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

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
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

    public void setWarehouseType(WarehouseType warehouseType){
        this.warehouseType = warehouseType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Warehouse)) return false;
        Warehouse other = (Warehouse) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Warehouse [id=" + id + ", name=" + name + ", warehouseType=" + warehouseType
        + ", capacity=" + capacity + ", location=" + location + ", createdAt=" + createdAt + "]";
    }
}