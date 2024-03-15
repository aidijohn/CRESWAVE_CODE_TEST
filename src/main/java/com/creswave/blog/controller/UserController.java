package com.creswave.blog.controller;

import com.creswave.blog.dto.UpdateUserRequest;
import com.creswave.blog.model.User;
import com.creswave.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @GetMapping(value = "/usertest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {

        logger.debug("Called UserController.usertest");

        return ResponseEntity.ok("Hello User");
    }

    @PutMapping("updateUserDetails/{userId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable("userId") Integer userId, @RequestBody UpdateUserRequest updateUserRequest) {

        logger.debug("Called UserController.updateUserDetails");

        User existingUser = userService.findById(userId);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Update user details
        existingUser.setFirstname(updateUserRequest.getFirstname());
        existingUser.setSecondname(updateUserRequest.getSecondname());
        existingUser.setEmail(updateUserRequest.getEmail());
        existingUser.setPassword(updateUserRequest.getPassword());

        User updatedUser = userService.updateUser(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

}
