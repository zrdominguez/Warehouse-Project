package com.skillstorm.project1.backend.services;


import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;
import com.skillstorm.project1.backend.dto.CreateWarehouseRequest;
import com.skillstorm.project1.backend.dto.UpdateWarehouseRequest;
import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.repositories.UserRepository;
import com.skillstorm.project1.backend.repositories.WarehouseRepository;

import jakarta.transaction.Transactional;
@Transactional
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

    public List<Warehouse> findWarehousesByOwner(Integer userId) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new IllegalArgumentException("No such User found!");
        return warehouseRepository.findByUser(user.get());
    }

    public Warehouse findWarehouseById(Integer warehouseId) throws IllegalArgumentException{
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if(warehouse.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");
        return warehouse.get();
    }

    public Warehouse createWarehouse(CreateWarehouseRequest request) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(request.userId());
        
        if(user.isEmpty()) throw new IllegalArgumentException("Must be a logged in User!");
        
        Warehouse warehouse = new Warehouse(
            user.get(), 
            request.name() != null ? request.name() : "Untitled", 
            request.warehouseType(), 
            request.capacity(), 
            request.location());

        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Integer warehouseId, UpdateWarehouseRequest request){
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);

        if(warehouseOptional.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");

        Optional<User> userOptional = userRepository.findById(request.userId());
        
        if(userOptional.isEmpty()) throw new IllegalArgumentException("Must be a logged in User!");

        User user = userOptional.get();
        Warehouse warehouse = warehouseOptional.get();

        if(!user.equals(warehouse.getUser()) || !user.getIsAdmin()) throw new AccessDeniedException("Admin access Denied or User does not own Warehouse!");

        if(request.name() != null) warehouse.setName(request.name());
        
        if(request.warehouseType() != null) warehouse.setWarehouseType(request.warehouseType());

        if(request.capacity() != 0) warehouse.setCapacity(request.capacity());

        if(request.location() != null) warehouse.setLocation(request.location());

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Integer warehouseId, Integer userId){

        
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);

        if(warehouseOptional.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");

        Optional<User> userOptional = userRepository.findById(userId);
        
        if(userOptional.isEmpty()) throw new IllegalArgumentException("Must be a logged in User!");

        User user = userOptional.get();
        Warehouse warehouse = warehouseOptional.get();

        System.out.println("ownerId = " + warehouse.getUser().getId());
        System.out.println("incoming userId = " + userId);

        if(!user.equals(warehouse.getUser()) || !user.getIsAdmin()) throw new AccessDeniedException("Admin access Denied or User does not own Warehouse!");

        warehouseRepository.deleteById(warehouseId);
    }
}