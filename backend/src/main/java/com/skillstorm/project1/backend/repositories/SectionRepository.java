package com.skillstorm.project1.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skillstorm.project1.backend.models.Section;

public interface SectionRepository extends JpaRepository<Section, Integer>{
    
    @Query("""
    SELECT s
    FROM Section s
    WHERE s.warehouse.id = :warehouseId
      AND LOWER(s.name) = LOWER(:name)
    """)
    Optional<Section> findByNameAndWarehouse(
        @Param("name") String name,
        @Param("warehouseId") Integer warehouseId
    );
}
