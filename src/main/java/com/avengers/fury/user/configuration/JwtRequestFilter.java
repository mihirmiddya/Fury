package com.avengers.fury.user.configuration;


import com.avengers.fury.user.service.AuthService;
import com.avengers.fury.user.service.JwtUtil;
import com.avengers.fury.user.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.avengers.fury.user.service.RedisService.TOKEN_REDIS_KEY;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String email;
        final String token = request.getHeader("token");

        if (token != null) {
            email = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = this.authService.loadUserByUsername(email);
            // Check the token stored in Redis
            Object storedTokenObj = redisService.find(TOKEN_REDIS_KEY.concat(userDetails.getUsername()));
            if (!Objects.isNull(storedTokenObj) && token.equalsIgnoreCase((String) storedTokenObj)) {
                doAuthenticate(request, userDetails);
                chain.doFilter(request, response);
                return;
            }

            // Validate token and authenticate
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetails))) {
                    doAuthenticate(request, userDetails);
                    logger.info("Authenticated user: {}", email);
                } else {
                    logger.warn("Invalid token for user: {}", email);
                }
            } else if (email == null) {
                logger.warn("Invalid token");
            } else {
                logger.info("User already authenticated: {}", email);
            }
        } else {
            logger.warn("Token not provided");
        }

        chain.doFilter(request, response);
    }

    private static void doAuthenticate(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

