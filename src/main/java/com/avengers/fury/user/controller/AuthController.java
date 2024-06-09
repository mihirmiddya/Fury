package com.avengers.fury.user.controller;


import com.avengers.fury.user.dto.UserLoginForm;
import com.avengers.fury.user.dto.UserLoginResponse;
import com.avengers.fury.user.service.AuthService;
import com.avengers.fury.user.service.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginForm loginForm) {
        this.doAuthenticate(loginForm.getEmail(), loginForm.getPassword());
        UserDetails userDetails = authService.loadUserByUsername(loginForm.getEmail());
        String token = this.jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new UserLoginResponse(token), HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
            logger.debug("User authenticated successfully.");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String token) {
        // 
        return ResponseEntity.ok("Logged out successfully");
    }

}
