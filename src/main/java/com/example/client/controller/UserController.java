package com.example.client.controller;


import com.example.client.dto.UserDTO;
import com.example.client.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO addUser(@RequestBody @Validated UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
}