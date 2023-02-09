package com.authhandler.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authhandler.authapi.model.LoginRequest;
import com.authhandler.authapi.model.User;
import com.authhandler.authapi.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User createUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User login(LoginRequest login) {
    User user = userRepository.findByUsername(login.getUsername());
    if (user != null && bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
      return user;
    }
    return null;
  }
}