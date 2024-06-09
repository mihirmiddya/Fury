package com.avengers.fury.user.service;

import com.avengers.fury.user.dto.CreateUserForm;
import com.avengers.fury.user.dto.UserResponse;
import com.avengers.fury.user.model.User;
import com.avengers.fury.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(CreateUserForm form) {
        logger.info("Creating user with email: {}", form.getEmail());
        User user = new User(form);
        userRepository.save(user);
        logger.info("User created with email: {}", form.getEmail());
        return new UserResponse(user);
    }

    public List<UserResponse> getUser(String nameOrEmail) {
        logger.info("Fetching user(s) with name or email: {}", nameOrEmail);
        List<User> userList = userRepository.findByEmailIgnoreCaseOrNameIgnoreCase(nameOrEmail, nameOrEmail);
        logger.info("Found {} user(s) with name or email: {}", userList.size(), nameOrEmail);
        return userList.stream().map(UserResponse::new).collect(Collectors.toList());
    }
}
