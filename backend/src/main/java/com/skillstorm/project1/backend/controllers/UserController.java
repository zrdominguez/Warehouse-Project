package com.skillstorm.project1.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project1.backend.models.User;
import com.skillstorm.project1.backend.services.UserService;

/**
 * REST controller providing read-access to User resources.
 *
 * <p>This controller exposes endpoints for retrieving user information by ID.
 * All responses follow a consistent error-handling structure:
 * <ul>
 *   <li><b>400 Bad Request</b> – Returned when the provided ID is invalid or
 *       when the service throws an {@link IllegalArgumentException}.</li>
 *   <li><b>500 Internal Server Error</b> – Returned for unexpected failures.</li>
 * </ul>
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Retrieves a user by ID.
     *
     * <p>Example request:
     * <pre>
     * GET /user/5
     * </pre>
     *
     * @param userId the ID of the user to retrieve
     * @return {@code 200 OK} with the user data if found,
     *         {@code 400 Bad Request} if the ID is invalid or the user does not exist,
     *         {@code 500 Internal Server Error} for unexpected errors.
     */
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
