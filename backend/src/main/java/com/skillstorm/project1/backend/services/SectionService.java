package com.skillstorm.project1.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.repositories.SectionRepository;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }

    public Section findSectionById(Integer sectionId){
        Optional<Section> section = sectionRepository.findById(sectionId);
        return section.get();
    }

    public Section findSectionByName(String name, Integer warehouseId){
        Optional<Section> section = sectionRepository.findByNameAndWarehouse(name, warehouseId);
        return section.get();
    }
}
