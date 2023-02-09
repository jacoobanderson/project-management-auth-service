package com.authhandler.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

  public User login(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
      return user;
    }
    return null;
  }
}