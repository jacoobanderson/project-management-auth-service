package com.authhandler.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authhandler.authapi.model.LoginRequest;
import com.authhandler.authapi.model.User;
import com.authhandler.authapi.repository.UserRepository;
import com.common.JwtResponse;
import com.common.JwtUtil;
import com.mongodb.MongoWriteException;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public User createUser(User user) throws Exception {
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new Exception("Username already exists");
    }
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    try {
      return userRepository.save(user);
    } catch (MongoWriteException error) {
        if (error.getError().getCode() == 11000) {
          throw new Exception("Username already exists.");
        }
        throw error;
    }
  }

  public JwtResponse login(LoginRequest login) {
    User user = userRepository.findByUsername(login.getUsername());
    if (user != null && bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
      String jwt = jwtUtil.generateToken(user.getUsername());
      return new JwtResponse(jwt);
    }
    return null;
  }
}