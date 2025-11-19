package com.skillstorm.project1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skillstorm.project1.backend.dto.Product.ProductWithQuantity;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.models.SectionProduct;
import com.skillstorm.project1.backend.models.SectionProduct.SectionProductId;
import java.util.List;
import java.util.Optional;


public interface SectionProductRepository extends JpaRepository<SectionProduct, SectionProductId>{

    
    @Query("""
    SELECT new com.skillstorm.project1.backend.dto.Product.ProductWithQuantity(
        p.id,
        p.name,
        p.sku,
        p.productType,
        p.description,
        sp.quantity
    )
    FROM SectionProduct sp
    JOIN sp.product p
    JOIN sp.section s
    WHERE s.warehouse.id = :warehouseId
    """)
    List<ProductWithQuantity> findProductsbyWarehouse(@Param("warehouseId") Integer warehouseId);

    @Query("""
    SELECT sp.section
    FROM SectionProduct sp
    WHERE sp.product.id = :productId
      AND sp.section.warehouse.id = :warehouseId
    """)
    Optional<Section> findSectionByProductAndWarehouse(
        @Param("productId") Integer productId,
        @Param("warehouseId") Integer warehouseId
    );
}