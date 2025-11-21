package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.dto.Product.ProductWithQuantity;
import com.skillstorm.project1.backend.dto.Warehouse.CreateWarehouseRequest;
import com.skillstorm.project1.backend.dto.Warehouse.DeleteWarehouseRequest;
import com.skillstorm.project1.backend.dto.Warehouse.AddRequest;
import com.skillstorm.project1.backend.dto.Warehouse.UpdateWarehouseRequest;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.services.SectionProductService;
import com.skillstorm.project1.backend.services.WarehouseService;

import org.springframework.web.bind.annotation.PutMapping;

/**
 * REST controller for managing warehouses, warehouse sections, and
 * the products stored within them.
 *
 * Base URL: /warehouse
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/warehouse")
public class WarehouseController {

    private final SectionProductService sectionProductService;

    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService, SectionProductService sectionProductService){
        this.warehouseService = warehouseService;
        this.sectionProductService = sectionProductService;
    }

    /**
    * Retrieves all warehouses in the system.
    *
    * <p>This endpoint returns every warehouse regardless of owner.
    * It returns HTTP 200 on success, HTTP 400 if the request is invalid,
    * and HTTP 500 if an unexpected error occurs.</p>
    *
    * @return ResponseEntity containing the list of warehouses and the appropriate HTTP status.
    */
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

    /**
    * Retrieves all warehouses owned by a specific user.
    *
    * <p>The provided userId must match an existing user.  
    * Returns HTTP 200 with the user's warehouses, HTTP 400 if the userId is invalid,
    * and HTTP 500 for unexpected errors.</p>
    *
    * @param userId the ID of the user whose warehouses should be returned
    * @return ResponseEntity containing the list of warehouses for the user and the appropriate HTTP status.
    */
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

    /**
    * Retrieves a warehouse by its ID.
    *
    * <p>Returns the warehouse if it exists.  
    * Returns HTTP 200 on success, HTTP 400 if the ID is invalid,
    * and HTTP 500 for unexpected internal errors.</p>
    *
    * @param warehouseId the unique ID of the warehouse
    * @return ResponseEntity containing the warehouse and the appropriate HTTP status.
    */
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

    /**
    * Retrieves all products and their quantities inside a warehouse.
    *
    * @param warehouseId ID of the warehouse.
    * @return 200 OK with list of product+quantity objects.
    */
    @GetMapping("/{warehouseId}/products")
    public ResponseEntity<List<ProductWithQuantity>> getProductsInWarehouse(@PathVariable int warehouseId) {
        List<ProductWithQuantity> products = warehouseService.getProductsInWarehouse(warehouseId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
    * Creates a new warehouse owned by a user.
    *
    * @param request DTO containing name, warehouseType, capacity, location, and userId.
    * @return 201 Created with the created warehouse,
    *         400 Bad Request for validation errors,
    *         500 Internal Server Error for unexpected issues.
    */
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

    /**
    * Adds a product to a specific warehouse section.
    *
    * @param warehouseId ID of the warehouse.
    * @param sectionId ID of the section.
    * @param dto DTO containing productId and quantity.
    * @return 204 No Content on success,
    *         400 Bad Request on invalid data,
    *         500 Internal Server Error on unexpected issues.
    */
    @PostMapping("/{warehouseId}/sections/{sectionId}/products")
    public ResponseEntity<Void> addProductToWarehouse(
        @PathVariable int warehouseId,
        @PathVariable int sectionId, 
        @RequestBody AddRequest dto){
        try{
            warehouseService.addProductToWarehouse(warehouseId, sectionId, dto);
            return ResponseEntity.noContent().build();            
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    /**
    * Removes a product from a warehouse section.
    *
    * @param sectionId ID of the section.
    * @param productId ID of the product.
    * @return 204 No Content on success,
    *         400 Bad Request for invalid IDs,
    *         500 Internal Server Error for unexpected issues.
    */
    @DeleteMapping("/sections/{sectionId}/product/{productId}")
    public ResponseEntity<Void> removeFromWarehouse(@PathVariable int sectionId, @PathVariable int productId){
        try{
            sectionProductService.removeProductFromSection(sectionId, productId);
            return ResponseEntity.noContent().build();            
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    /**
    * Transfers product quantity between two warehouses.
    *
    * @param fromWarehouseId Warehouse sending the product.
    * @param toWarehouseId Warehouse receiving the product.
    * @param dto DTO containing productId and quantity.
    * @return 204 No Content on success,
    *         400 Bad Request on invalid data,
    *         500 Internal Server Error on unexpected issues.
    */
    @PostMapping("/{fromWarehouseId}/transfer/{toWarehouseId}")
    public ResponseEntity<Void> transferProduct(
        @PathVariable int fromWarehouseId, 
        @PathVariable int toWarehouseId,
        @RequestBody AddRequest dto){
        try{
            warehouseService.transferProduct(fromWarehouseId, toWarehouseId, dto);
            return ResponseEntity.noContent().build();            
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }

    /**
    * Updates warehouse details such as name, type, location, and capacity.
    *
    * @param warehouseId ID of the warehouse.
    * @param request DTO containing updated warehouse fields.
    * @return 200 OK with the updated warehouse,
    *         400 Bad Request on validation issues,
    *         500 Internal Server Error on unexpected issues.
    */
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

    /**
    * Deletes a warehouse if the user owns it.
    *
    * @param warehouseId ID of the warehouse.
    * @param dto DTO containing userId for authentication.
    * @return 204 No Content on success,
    *         400 Bad Request for validation issues,
    *         500 Internal Server Error on unexpected issues.
    */
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void>  deleteWarehouse(@PathVariable int warehouseId, @RequestBody DeleteWarehouseRequest dto){
        try{
            warehouseService.deleteWarehouse(warehouseId, dto);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! " + e.getMessage()).build();
        }
    }
}