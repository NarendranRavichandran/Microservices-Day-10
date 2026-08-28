package com.oneenterprise.userservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oneenterprise.userservice.dto.UserResponse;
import com.oneenterprise.userservice.exception.UserNotFoundException;

@Service
public class UserService {

    private final Map<Long, UserResponse> users = new HashMap<>();

    public UserService() {

        users.put(
            1L,
            new UserResponse(
                1L,
                "Sachin",
                "sachin@example.com"
            )
        );

        users.put(
            2L,
            new UserResponse(
                2L,
                "Dhoni",
                "dhoni@example.com"
            )
        );

        users.put(
            3L,
            new UserResponse(
                3L,
                "Rohit",
                "rohit@example.com"
            )
        );
    }

    public UserResponse getUserById(Long id) {

    	UserResponse user = users.get(id);
    	if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }
}