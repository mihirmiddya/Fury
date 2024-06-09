package com.avengers.fury.user.dto;

import com.avengers.fury.user.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private String email;

    public UserResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
