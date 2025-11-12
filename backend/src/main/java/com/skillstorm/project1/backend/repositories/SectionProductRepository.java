package com.skillstorm.project1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.project1.backend.models.SectionProduct;
import com.skillstorm.project1.backend.models.SectionProduct.SectionProductId;
import java.util.List;


public interface SectionProductRepository extends JpaRepository<SectionProduct, SectionProductId>{

    List<SectionProduct> findBySectionId(int sectionId);
}