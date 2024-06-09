package com.avengers.fury.user.controller;

import com.avengers.fury.user.dto.CreateUserForm;
import com.avengers.fury.user.dto.UserResponse;
import com.avengers.fury.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{nameOrEmail}")
    public ResponseEntity<List<UserResponse>> getUser(@PathVariable String nameOrEmail) {
        logger.info("Fetching user(s) with name or email: {}", nameOrEmail);
        List<UserResponse> users = userService.getUser(nameOrEmail);
        logger.info("Found {} user(s) with name or email: {}", users.size(), nameOrEmail);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserForm form) {
        logger.info("Creating user with email: {}", form.getEmail());
        UserResponse userResponse = userService.createUser(form);
        logger.info("User created with name: {}", userResponse.getName());
        return ResponseEntity.ok(userResponse);
    }
}
