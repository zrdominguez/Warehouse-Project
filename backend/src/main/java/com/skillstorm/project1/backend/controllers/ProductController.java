package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.dto.Product.CreateProductRequest;
import com.skillstorm.project1.backend.dto.Product.UpdateProductRequest;
import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Product;
import com.skillstorm.project1.backend.services.ProductService;

/**
 * REST controller for managing products.
 *
 * <p>Provides endpoints for creating, updating, deleting, and retrieving products.
 * All responses include standard HTTP status codes and error handling.</p>
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     *
     * @return HTTP 200 with a list of all products, or
     *         HTTP 500 if an unexpected server error occurs.
     */
    @GetMapping
    public ResponseEntity<List<Product>>  findAllProducts(){
        try {
            List<Product> products = productService.findAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve.
     * @return HTTP 200 with the product,
     *         HTTP 404 if the product is not found,
     *         HTTP 400 if the ID is invalid,
     *         HTTP 500 for server errors.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable int productId){
        try{
            return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    /**
     * Creates a new product.
     *
     * @param dto the request body containing product creation details.
     * @return HTTP 201 with the created product,
     *         HTTP 400 if validation fails,
     *         HTTP 500 for server errors.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(
        @RequestBody CreateProductRequest dto){
        try{
            return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! "+ e.getMessage()).build();
        }
    }
    
    /**
     * Updates an existing product.
     *
     * @param productId the ID of the product to update.
     * @param dto       the request body containing updated fields.
     * @return HTTP 200 with the updated product,
     *         HTTP 404 if not found,
     *         HTTP 400 for invalid input,
     *         HTTP 500 for errors.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody UpdateProductRequest dto){
        try{
            return new ResponseEntity<>(productService.updateProduct(productId, dto), HttpStatus.OK);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! ").build();
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete.
     * @return HTTP 204 upon successful deletion,
     *         HTTP 404 if the product does not exist,
     *         HTTP 400 for invalid ID,
     *         HTTP 500 for unexpected errors.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build();
        }catch(NotFoundException e){
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong! ").build();
        }
    }
}