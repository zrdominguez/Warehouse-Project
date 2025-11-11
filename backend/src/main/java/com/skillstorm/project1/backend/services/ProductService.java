package com.skillstorm.project1.backend.services;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
}