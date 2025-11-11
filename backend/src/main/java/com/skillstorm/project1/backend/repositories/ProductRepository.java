package com.skillstorm.project1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.project1.backend.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    
}