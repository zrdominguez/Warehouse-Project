package com.skillstorm.project1.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Product;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.models.SectionProduct;
import com.skillstorm.project1.backend.models.SectionProduct.SectionProductId;
import com.skillstorm.project1.backend.repositories.ProductRepository;
import com.skillstorm.project1.backend.repositories.SectionProductRepository;
import com.skillstorm.project1.backend.repositories.SectionRepository;

import jakarta.transaction.Transactional;
@Service
public class SectionProductService {
    
    private final SectionProductRepository sectionProductRepository;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;


    public SectionProductService(SectionProductRepository sectionProductRepository, SectionRepository sectionRepository, ProductRepository productRepository){
        this.sectionProductRepository = sectionProductRepository;
        this.sectionRepository = sectionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void updateQuantity(Integer sectionId, Integer productId, Integer newQuantity){
        Optional<SectionProduct> opSectionProduct = sectionProductRepository.findById(new SectionProductId(sectionId, productId));
        
        SectionProduct sectionProduct = opSectionProduct.get();

        sectionProduct.setQuantity(newQuantity);

        sectionProductRepository.save(sectionProduct);
    }

    @Transactional
    public void addProductToSection(Integer sectionId, Integer productId, int quantity) throws IllegalArgumentException{
        if (sectionId == null || productId == null) throw new IllegalArgumentException("sectionId or productId cannot be null!");
        Optional<Section> section = sectionRepository.findById(sectionId);
        SectionProductId id = new SectionProductId(sectionId, productId);
        Optional<Product> product = productRepository.findById(productId);
        
        if(sectionProductRepository.existsById(id)){
            Optional<SectionProduct> opSectionProduct = sectionProductRepository.findById(id);
            SectionProduct sectionProduct = opSectionProduct.get();
            int newQuantity = sectionProduct.getQuantity() + quantity;
            sectionProduct.setQuantity(newQuantity);
            sectionProductRepository.save(sectionProduct);
        }else{
            SectionProduct sectionProduct = new SectionProduct(
            section.get(),
            product.get(),
            quantity);
            sectionProductRepository.save(sectionProduct);
        }
    }

    @Transactional
    public void transferProductToSection(
        Section fromSection, 
        Section toSection, 
        Integer productId, 
        int quantity
    ){
        if (fromSection == null || productId == null || toSection == null)
        throw new IllegalArgumentException("Source section, productId or target section cannot be null!");
        
        Optional<SectionProduct> opSectionProduct = sectionProductRepository.findById(new SectionProductId(fromSection.getId(), productId));

        if(opSectionProduct.isEmpty()) throw new NotFoundException("Product not found in warehouse section!");

        SectionProduct sectionProduct = opSectionProduct.get();

        if(sectionProduct.getQuantity() < quantity) throw new IllegalArgumentException("Not enough quantity to transfer!");

        int updatedQty = sectionProduct.getQuantity() - quantity;

        updateQuantity(fromSection.getId(), productId, updatedQty);
        addProductToSection(toSection.getId(), productId, quantity);
    }

    @Transactional
    public void removeProductFromSection(
        Integer sectionId,
        Integer productId
    ){
        if (sectionId == null || productId == null)
        throw new IllegalArgumentException("sectionId or productId cannot be null!");

        SectionProductId id = new SectionProductId(sectionId, productId);

        if(!sectionProductRepository.existsById(id))
        throw new IllegalArgumentException("This product is not in this section!");

        sectionProductRepository.deleteById(id);
    }
}
