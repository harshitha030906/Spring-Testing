package com.TestingApplication.TestingApp.controllers;

import com.TestingApplication.TestingApp.dto.SignUpDto;
import com.TestingApplication.TestingApp.dto.UserDto;
import com.TestingApplication.TestingApp.entity.User;
import com.TestingApplication.TestingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserDto signup(SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

}
