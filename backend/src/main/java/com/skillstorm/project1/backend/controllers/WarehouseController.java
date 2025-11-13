package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.services.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> findAllWarehouses(){
        try{
            List<Warehouse> warehouses = warehouseService.findAllWarehouses();
            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Warehouse>> findAllUserWarehouses(@PathVariable int userId){
        try{
            List<Warehouse> warehouses = warehouseService.findWarehousesByOwner(userId);
            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse){
        try{
            return new ResponseEntity<>(warehouseService.createWarehouse(warehouse), HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    
}