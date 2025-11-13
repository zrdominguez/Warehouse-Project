package com.skillstorm.project1.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable int userId){
        try{
            return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().header("message", e.getMessage()).build();
        }catch (Exception e){
            return ResponseEntity.badRequest().header("message", "Something went wrong!").build();
        }
    }
}
