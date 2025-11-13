package com.skillstorm.project1.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.repositories.UserRepository;
import com.skillstorm.project1.backend.repositories.WarehouseRepository;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, UserRepository userRepository){
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
    }

    public List<Warehouse> findAllWarehouses(){
        return warehouseRepository.findAll();
    }

    public List<Warehouse> findWarehousesByOwner(int userId) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new IllegalArgumentException("No such User found!");
        return warehouseRepository.findByUser(user.get());
    }

    public Warehouse createWarehouse(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }
}