package com.skillstorm.project1.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.services.SectionService;

@RestController
@RequestMapping("/section")
@CrossOrigin(origins = "http://localhost:5173")
public class SectionController {
    
    SectionService sectionService;
    
    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    //find section by id
    @GetMapping("/{sectionId}")
    public ResponseEntity<Section>  findSectionById(@PathVariable int sectionId){
        try{
            return new ResponseEntity<>(sectionService.findSectionById(sectionId), HttpStatus.OK);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }
    //find sections in a warehouse
}
