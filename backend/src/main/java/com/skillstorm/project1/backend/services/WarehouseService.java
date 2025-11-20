package com.skillstorm.project1.backend.services;


import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.dto.Product.ProductWithQuantity;
import com.skillstorm.project1.backend.dto.Warehouse.CreateWarehouseRequest;
import com.skillstorm.project1.backend.dto.Warehouse.DeleteWarehouseRequest;
import com.skillstorm.project1.backend.dto.Warehouse.AddRequest;
import com.skillstorm.project1.backend.dto.Warehouse.UpdateWarehouseRequest;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.models.Warehouse;
import com.skillstorm.project1.backend.repositories.SectionProductRepository;
import com.skillstorm.project1.backend.repositories.SectionRepository;
import com.skillstorm.project1.backend.repositories.UserRepository;
import com.skillstorm.project1.backend.repositories.WarehouseRepository;

import jakarta.transaction.Transactional;
@Transactional
@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final SectionProductRepository sectionProductRepository;
    private final SectionRepository sectionRepository;
    private final SectionProductService sectionProductService;

    public WarehouseService(
        WarehouseRepository warehouseRepository, 
        UserRepository userRepository, 
        SectionProductRepository sectionProductRepository,
        SectionRepository sectionRepository,
        SectionProductService sectionProductService
        ){
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.sectionProductRepository = sectionProductRepository;
        this.sectionRepository = sectionRepository;
        this.sectionProductService = sectionProductService;
    }

    private void checkCapacity(Warehouse warehouse, int load){
        int newLoad = warehouse.getCurrentLoad() + load;

        if(newLoad > warehouse.getCapacity()){
            throw new IllegalArgumentException("Warehouse capacity exceeded");
        }
    }

    public List<Warehouse> findAllWarehouses(){
        return warehouseRepository.findAll();
    }

    public List<Warehouse> findWarehousesByOwner(Integer userId) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new IllegalArgumentException("No such User found!");
        return warehouseRepository.findByUser(user.get());
    }

    @Transactional
    public Warehouse findWarehouseById(Integer warehouseId) throws IllegalArgumentException{
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if(warehouse.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");
        return warehouse.get();
    }

    public List<ProductWithQuantity> getProductsInWarehouse(Integer warehouseId) {
        return sectionProductRepository.findProductsbyWarehouse(warehouseId);
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

    public Warehouse updateWarehouse(Integer warehouseId, UpdateWarehouseRequest request) throws IllegalAccessException, AccessDeniedException{
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);

        if(warehouseOptional.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");

        Optional<User> opUser = userRepository.findById(request.userId());
        
        if(opUser.isEmpty()) throw new IllegalArgumentException("Must be a logged in User!");

        User user = opUser.get();
        Warehouse warehouse = warehouseOptional.get();

        if(!user.equals(warehouse.getUser()) || !user.getIsAdmin()) throw new AccessDeniedException("Admin access Denied or User does not own Warehouse!");

        if(request.name() != null) warehouse.setName(request.name());
        
        if(request.warehouseType() != null) warehouse.setWarehouseType(request.warehouseType());

        if(request.capacity() != 0){
            warehouse.setCapacity(request.capacity());
            checkCapacity(warehouse, 0);
        }

        if(request.location() != null) warehouse.setLocation(request.location());

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Integer warehouseId, DeleteWarehouseRequest dto){

        Optional<Warehouse> opWarehouse = warehouseRepository.findById(warehouseId);

        if(opWarehouse.isEmpty()) throw new IllegalArgumentException("Warehouse does not exist!");

        Optional<User> opUser = userRepository.findById(dto.userId());
        
        if(opUser.isEmpty()) throw new IllegalArgumentException("Must be a logged in User!");

        User user = opUser.get();
        Warehouse warehouse = opWarehouse.get();

        if(!user.equals(warehouse.getUser()) || !user.getIsAdmin()) throw new AccessDeniedException("Admin access Denied or User does not own Warehouse!");

        warehouseRepository.deleteById(warehouseId);
    }

    public void transferProduct(Integer fromWarehouseId, Integer toWarehouseId, AddRequest dto){
        Optional<Section> opFromSection = sectionProductRepository.findSectionByProductAndWarehouse(dto.productId(), fromWarehouseId);

        Section fromSection = opFromSection.get();

        Optional<Section> opToSection = sectionRepository.findByNameAndWarehouse(fromSection.getName(), toWarehouseId);
        
        Section toSection = opToSection.get();

        System.out.println(fromSection + " " + toSection);

        Warehouse toWarehouse = warehouseRepository.findById(toWarehouseId).get();

        checkCapacity(toWarehouse, dto.quantity());

        sectionProductService.transferProductToSection(fromSection, toSection, dto.productId(), dto.quantity());
    }

    public void addProductToWarehouse(Integer warehouseId, Integer sectionId, AddRequest dto){
        Optional<Warehouse> opWarehouse = warehouseRepository.findById(warehouseId);

        Warehouse warehouse = opWarehouse.get();

        checkCapacity(warehouse, dto.quantity());

        sectionProductService.addProductToSection(sectionId, dto.productId(), dto.quantity());
    }
}