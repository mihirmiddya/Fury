package com.avengers.fury.user.service;

import com.avengers.fury.user.dto.CreateUserForm;
import com.avengers.fury.user.dto.UserResponse;
import com.avengers.fury.user.model.User;
import com.avengers.fury.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(CreateUserForm form) {
        User user = new User(form);
        userRepository.save(user);
        return new UserResponse(user);
    }

    public List<UserResponse> getUser(String nameOrEmail) {
        List<User> userList = userRepository.findByEmailIgnoreCaseOrNameIgnoreCase(nameOrEmail, nameOrEmail);
        return userList.stream().map(UserResponse::new).collect(Collectors.toList());
    }
}
