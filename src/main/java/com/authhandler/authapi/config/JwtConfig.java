package com.authhandler.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.common.JwtUtil;

@Configuration
public class JwtConfig {
  @Bean
  public JwtUtil jwtUtil() {
    return new JwtUtil();
  }
}