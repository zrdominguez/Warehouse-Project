package com.skillstorm.project1.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.exception.NotFoundException;
import com.skillstorm.project1.backend.models.Section;
import com.skillstorm.project1.backend.services.SectionProductService;
import com.skillstorm.project1.backend.services.SectionService;

@RestController
@RequestMapping("/section")
@CrossOrigin(origins = "http://localhost:5173")
public class SectionController {
    
    SectionService sectionService;
    SectionProductService sectionProductService;
    
    public SectionController(SectionService sectionService, SectionProductService sectionProductService){
        this.sectionService = sectionService;
        this.sectionProductService = sectionProductService;
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
    
    //Update quantity in section
    @PutMapping("/{sectionId}/products/{productId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable int sectionId, @PathVariable int productId, @RequestBody int quantity){
        try{
            sectionProductService.updateQuantity(sectionId, productId, quantity);
            return ResponseEntity.noContent().build();
        }catch(NotFoundException e){
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().header("message", "Something went wrong!").build();
        }
    }
}
