package com.skillstorm.project1.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    public User findUserById(int userId) throws IllegalArgumentException{
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) return user.get();
        else throw new IllegalArgumentException("No such User excists!");
    }

}