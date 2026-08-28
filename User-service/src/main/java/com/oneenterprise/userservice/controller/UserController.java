package com.oneenterprise.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, UserData> users =
            new HashMap<>();

    public UserController() {

        users.put(
                1L,
                new UserData(
                        1L,
                        "Sachin",
                        "sachin@example.com"
                )
        );

        users.put(
                2L,
                new UserData(
                        2L,
                        "Dhoni",
                        "dhoni@example.com"
                )
        );

        users.put(
                3L,
                new UserData(
                        3L,
                        "Rohit",
                        "rohit@example.com"
                )
        );
    }

    @GetMapping("/{id}")
    public UserData getUserById(
            @PathVariable Long id,
            @RequestParam(
                    name = "delayMs",
                    required = false
            ) Long delayMs) {

        if (delayMs != null && delayMs > 0) {

            try {

                Thread.sleep(delayMs);

            } catch (InterruptedException exception) {

                Thread.currentThread()
                        .interrupt();

                throw new RuntimeException(
                        "User Service interrupted"
                );
            }
        }

        UserData user = users.get(id);

        if (user == null) {

            throw new RuntimeException(
                    "User not found: " + id
            );
        }

        return user;
    }

    public static class UserData {

        private Long id;

        private String name;

        private String email;

        public UserData() {
        }

        public UserData(
                Long id,
                String name,
                String email) {

            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}