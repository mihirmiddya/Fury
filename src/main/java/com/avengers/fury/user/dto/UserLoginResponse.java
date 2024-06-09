package com.avengers.fury.user.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginResponse {
    private String token;

    public UserLoginResponse(String token) {
        this.token = token;
    }
}
