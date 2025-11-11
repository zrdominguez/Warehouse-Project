package com.skillstorm.project1.backend.services;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
}