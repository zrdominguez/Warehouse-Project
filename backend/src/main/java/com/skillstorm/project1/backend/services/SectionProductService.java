package com.skillstorm.project1.backend.services;

import java.util.Optional;

import com.skillstorm.project1.backend.models.Product;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.models.SectionProduct;
import com.skillstorm.project1.backend.models.SectionProduct.SectionProductId;
import com.skillstorm.project1.backend.repositories.ProductRepository;
import com.skillstorm.project1.backend.repositories.SectionProductRepository;
import com.skillstorm.project1.backend.repositories.SectionRepository;

public class SectionProductService {
    
    private final SectionProductRepository sectionProductRepository;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;


    public SectionProductService(SectionProductRepository sectionProductRepository, SectionRepository sectionRepository, ProductRepository productRepository){
        this.sectionProductRepository = sectionProductRepository;
        this.sectionRepository = sectionRepository;
        this.productRepository = productRepository;
    }

    public void updateQuantity(Integer sectionId, Integer productId, int newQuantity){
        Optional<SectionProduct> opSectionProduct = sectionProductRepository.findById(new SectionProductId(sectionId, productId));
        
        SectionProduct sectionProduct = opSectionProduct.get();

        sectionProduct.setQuantity(newQuantity);

        sectionProductRepository.save(sectionProduct);
    }

    public SectionProduct addProductToSection(Integer sectionId, Integer productId, int quantity) throws IllegalArgumentException{
        Optional<Section> section = sectionRepository.findById(sectionId);
        SectionProductId id = new SectionProductId(sectionId, productId);
        if(sectionProductRepository.existsById(id)){
            throw new IllegalArgumentException("This already Exists");
        }

        Optional<Product> product = productRepository.findById(productId);

        SectionProduct sectionProduct = new SectionProduct(
            section.get(),
            product.get(),
            quantity
        );

        return sectionProductRepository.save(sectionProduct);
    }

}
