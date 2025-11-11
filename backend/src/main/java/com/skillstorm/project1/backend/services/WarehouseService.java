package com.skillstorm.project1.backend.services;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.repositories.WarehouseRepository;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }
    
}