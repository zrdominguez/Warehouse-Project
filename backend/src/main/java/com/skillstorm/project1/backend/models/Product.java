package com.skillstorm.project1.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private String sku;

    @Column
    private String type;
    
    @Column
    private String description;
    
}