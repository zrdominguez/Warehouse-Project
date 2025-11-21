package com.skillstorm.project1.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.models.Warehouse;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>{

    List<Warehouse> findByUser(User user);
    
    List<Warehouse> findAll();
}