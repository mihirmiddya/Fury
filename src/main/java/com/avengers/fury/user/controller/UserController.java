package com.avengers.fury.user.controller;

import com.avengers.fury.user.dto.CreateUserForm;
import com.avengers.fury.user.dto.UserResponse;
import com.avengers.fury.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{nameOrEmail}")
    public ResponseEntity<List<UserResponse>> getUser(@PathVariable String nameOrEmail) {
        return ResponseEntity.ok(userService.getUser(nameOrEmail));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserForm form) {
        return ResponseEntity.ok(userService.createUser(form));
    }
}
