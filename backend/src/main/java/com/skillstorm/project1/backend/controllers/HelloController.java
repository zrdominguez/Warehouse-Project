package com.skillstorm.project1.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @CrossOrigin(origins = {"http://localhost:5173"})
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello From Spring Boot!";
    }

}