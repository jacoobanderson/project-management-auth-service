package com.authhandler.authapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authhandler.authapi.model.LoginRequest;
import com.authhandler.authapi.model.User;
import com.authhandler.authapi.service.UserService;

@RestController
public class UserController {
  
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public User registerUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PostMapping("/login")
  public User login(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }
}