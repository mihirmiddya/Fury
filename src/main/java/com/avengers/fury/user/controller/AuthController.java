package com.avengers.fury.user.controller;


import com.avengers.fury.user.dto.UserLoginForm;
import com.avengers.fury.user.dto.UserLoginResponse;
import com.avengers.fury.user.service.AuthService;
import com.avengers.fury.user.service.JwtUtil;
import com.avengers.fury.user.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static com.avengers.fury.user.service.RedisService.TOKEN_REDIS_KEY;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginForm loginForm) {
        logger.info("Login attempt for user: {}", loginForm.getEmail());
        this.doAuthenticate(loginForm.getEmail(), loginForm.getPassword());
        UserDetails userDetails = authService.loadUserByUsername(loginForm.getEmail());
        String token = this.jwtUtil.generateToken(userDetails);
        logger.info("Token generated for user: {}", loginForm.getEmail());
        redisService.saveWithExpiry(TOKEN_REDIS_KEY.concat(userDetails.getUsername()), token, 1800, TimeUnit.SECONDS);
        return new ResponseEntity<>(new UserLoginResponse(token), HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
            logger.debug("User authenticated successfully: {}", email);
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", email);
            throw new BadCredentialsException("Invalid Username or Password!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String token) {
        // 
        return ResponseEntity.ok("Logged out successfully");
    }

}

