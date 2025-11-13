package com.skillstorm.project1.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.repositories.WarehouseRepository;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }

    public List<Warehouse> findWarehousesByOwner(int userId) throws IllegalArgumentException{
        return warehouseRepository.findByUserId(userId);
    }
    
}