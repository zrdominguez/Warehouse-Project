package com.skillstorm.project1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.project1.backend.models.Section;

public interface SectionRepository extends JpaRepository<Section, Integer>{
    
}
