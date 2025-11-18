package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.dto.Product.ProductWithQuantity;
import com.skillstorm.project1.backend.dto.Warehouse.CreateWarehouseRequest;
import com.skillstorm.project1.backend.dto.Warehouse.UpdateWarehouseRequest;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.services.WarehouseService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
    }

    //Show all warehouses
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

    //Find all warehouses owned by a user
    @GetMapping("/user/{userId}")
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

    //Find warehouse by its id
    @GetMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> findWarehouseById(@PathVariable int warehouseId){
        try{
            return new ResponseEntity<>(warehouseService.findWarehouseById(warehouseId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    //Find All Products in a warehouse
     @GetMapping("/{warehouseId}/products")
    public ResponseEntity<List<ProductWithQuantity>> getProductsInWarehouse(@PathVariable int warehouseId) {
        List<ProductWithQuantity> products = warehouseService.getProductsInWarehouse(warehouseId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //create a new warehouse
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(
        @RequestBody CreateWarehouseRequest request){
        try{
            return new ResponseEntity<>(warehouseService.createWarehouse(request), HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    //Update warehouse details
    @PutMapping("/{warehouseId}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable int warehouseId, @RequestBody UpdateWarehouseRequest request) {
        try{
            return new ResponseEntity<>(warehouseService.updateWarehouse(warehouseId, request), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    //Delete a warehouse
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void>  deleteWarehouse(@PathVariable int warehouseId, @RequestParam int userId){
        try{
            warehouseService.deleteWarehouse(warehouseId, userId);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }
}