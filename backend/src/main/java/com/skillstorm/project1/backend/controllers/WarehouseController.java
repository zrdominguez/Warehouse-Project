package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.services.UserService;
import com.skillstorm.project1.backend.services.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
    }


    @GetMapping("/{userId}/warehouses")
    public ResponseEntity<List<Warehouse>> findAllUserWarehouses(@PathVariable int id){
        try{
            List<Warehouse> warehouses = warehouseService.findWarehousesByOwner(id);
            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().header("message", "Something went wrong!").build();
        }
    }
    
}