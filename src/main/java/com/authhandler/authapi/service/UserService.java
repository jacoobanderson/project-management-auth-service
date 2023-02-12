package com.authhandler.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authhandler.authapi.model.LoginRequest;
import com.authhandler.authapi.model.User;
import com.authhandler.authapi.repository.UserRepository;
import com.common.JwtResponse;
import com.common.JwtUtil;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  public User createUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
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