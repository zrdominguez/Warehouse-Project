package com.skillstorm.project1.backend.services;

import java.util.Optional;

import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.repositories.SectionRepository;


public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository){
        this.sectionRepository = sectionRepository;
    }

    public Section findSectionByName(String name, Integer warehouseId){
        Optional<Section> section = sectionRepository.findByNameAndWarehouse(name, warehouseId);
        System.out.println(section.get());
        return section.get();
    }
}
