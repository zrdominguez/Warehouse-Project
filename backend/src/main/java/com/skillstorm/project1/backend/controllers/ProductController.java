package com.skillstorm.project1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.dto.Product.UpdateProductRequest;
import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Product;
import com.skillstorm.project1.backend.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //Get all products
    @GetMapping
    public ResponseEntity<List<Product>>  findAllProducts(){
        try {
            List<Product> products = productService.findAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }

    //Find a product by ID
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
    
    //Find a product by SKU
    //Create a new Product
    
    //Update a product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody UpdateProductRequest dto){
        return new ResponseEntity<>(productService.updateProduct(productId, dto), HttpStatus.OK);
    }
}