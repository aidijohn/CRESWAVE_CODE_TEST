package com.creswave.blog.controller;

import com.creswave.blog.dto.UpdateUserRequest;
import com.creswave.blog.exception.SetupException;
import com.creswave.blog.model.User;
import com.creswave.blog.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @GetMapping(value = "/admintest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello Admin");
    }

    //update admin details
    @PutMapping("updateAdminDetails/{userId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable("userId") Integer userId, @RequestBody UpdateUserRequest updateUserRequest) {

        logger.debug("Called AdminController.updateAdminDetails");

        User existingUser = userService.findById(userId);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Update admin details
        existingUser.setFirstname(updateUserRequest.getFirstname());
        existingUser.setSecondname(updateUserRequest.getSecondname());
        existingUser.setEmail(updateUserRequest.getEmail());
        existingUser.setPassword(updateUserRequest.getPassword());

        User updatedUser = userService.updateUser(existingUser);
        return ResponseEntity.ok(updatedUser);
    }


    // List all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get a user by ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        if (user == null) {
            String message = "User with id " + userId + " was not found";
            Map<String, String> responseMessage = new HashMap<>();
            responseMessage.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok(user);
    }


    // Delete user by ID
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("userId") Integer userId) {
        userService.deleteUserById(userId);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "User id " + userId + " successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }




}
