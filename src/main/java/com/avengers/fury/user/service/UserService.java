package com.avengers.fury.user.service;

import com.avengers.fury.event.service.EventService;
import com.avengers.fury.event.service.KafkaProducer;
import com.avengers.fury.user.dto.CreateUserForm;
import com.avengers.fury.user.dto.UserResponse;
import com.avengers.fury.user.model.User;
import com.avengers.fury.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.avengers.fury.event.constants.EventConstants.ADD_USER_EVENT;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EventService eventService;

    public UserResponse createUser(CreateUserForm form) {
        try {
            User user = new User(form);
            userRepository.save(user);
            logger.info("User created with email: {}", form.getEmail());

            Map<String, Object> map = new HashMap<>();
            map.put("userid", user.getId());
            eventService.createEvent(ADD_USER_EVENT, objectMapper.writeValueAsString(map));
            return new UserResponse(user);
        } catch (Exception ex) {
            logger.error("Error while creating user.", ex);
            throw new RuntimeException("Error while creating user.", ex);
        }
    }

    public List<UserResponse> getUser(String nameOrEmail) {
        logger.info("Fetching user(s) with name or email: {}", nameOrEmail);
        List<User> userList = userRepository.findByEmailIgnoreCaseOrNameIgnoreCase(nameOrEmail, nameOrEmail);
        logger.info("Found {} user(s) with name or email: {}", userList.size(), nameOrEmail);
        return userList.stream().map(UserResponse::new).collect(Collectors.toList());
    }
}
