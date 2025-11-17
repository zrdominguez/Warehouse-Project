package com.skillstorm.project1.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.dto.Product.UpdateProductRequest;
// import com.skillstorm.project1.backend.dto.Product.ProductDto;
import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Product;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.repositories.ProductRepository;
import com.skillstorm.project1.backend.repositories.WarehouseRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    // private final WarehouseRepository warehouseRepository;
    //, WarehouseRepository warehouseRepository
    
    private boolean isValidSku(String sku){
        return sku != null && sku.matches("^PRD-\\d{6}$");
    }

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
        //this.warehouseRepository = warehouseRepository;
    }

    
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(Integer productId) throws NotFoundException, IllegalArgumentException{
        if(productId == null) throw new IllegalArgumentException("productId cannot be null!");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) throw new NotFoundException("No such product with id " + productId + " exists!");
        return productRepository.findById(productId).get();
    }

    public Product updateProduct(Integer productId, UpdateProductRequest dto) throws IllegalArgumentException, NotFoundException{
        if(productId == null) throw new IllegalArgumentException("productId cannot be null!");
        Optional<Product> opProduct = productRepository.findById(productId);
        if(opProduct.isEmpty()) throw new NotFoundException("No such product with id " + productId + " exists!");
        Product product = opProduct.get();


        if(dto.name() != null && !dto.name().isBlank()) product.setName(dto.name());
        if(dto.description() != null && !dto.description().isBlank()) product.setDescription(dto.description());
        if(dto.sku() != null && !dto.sku().isBlank() && isValidSku(dto.sku())) product.setSku(dto.sku());

        return productRepository.save(product);
    }
}