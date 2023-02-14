package com.authhandler.authapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.authhandler.authapi.model.LoginRequest;
import com.authhandler.authapi.model.User;
import com.authhandler.authapi.repository.UserRepository;
import com.authhandler.authapi.service.UserService;
import com.common.JwtResponse;
import com.common.JwtUtil;
import com.mongodb.MongoWriteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceTests {

    private UserService userService;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setBCryptPasswordEncoder(bCryptPasswordEncoder);
        userService.setJwtUtil(jwtUtil);
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User("john", "pass");
        when(userRepository.findByUsername("john")).thenReturn(null);
        when(bCryptPasswordEncoder.encode("pass")).thenReturn("hashedPass");
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.createUser(user);
        assertEquals(user, savedUser);
    }

    @Test
    void testCreateUserWithExistingUsername() throws Exception {
        User user = new User("john", "pass");
        when(userRepository.findByUsername("john")).thenReturn(user);
        assertThrows(Exception.class, () -> userService.createUser(user));
    }

    @Test
    void testCreateUserWithMongoWriteException() {
        User user = new User("john", "pass");
        when(userRepository.findByUsername("john")).thenReturn(null);
        when(bCryptPasswordEncoder.encode("pass")).thenReturn("hashedPass");
        when(userRepository.save(user)).thenThrow(new MongoWriteException(null, null));
        assertThrows(MongoWriteException.class, () -> userService.createUser(user));
    }

    @Test
    void testLoginWithCorrectCredentials() {
        LoginRequest loginRequest = new LoginRequest("john", "pass");
        User user = new User("john", "hashedPass");
        when(userRepository.findByUsername("john")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("pass", "hashedPass")).thenReturn(true);
        when(jwtUtil.generateToken("john")).thenReturn("jwtToken");
        JwtResponse jwtResponse = userService.login(loginRequest);
        assertEquals(new JwtResponse("jwtToken"), jwtResponse);
    }

    @Test
    void testLoginWithIncorrectCredentials() {
        LoginRequest loginRequest = new LoginRequest("john", "wrongPass");
        User user = new User("john", "hashedPass");
        when(userRepository.findByUsername("john")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrongPass", "hashedPass")).thenReturn(false);
        JwtResponse jwtResponse = userService.login(loginRequest);
        assertEquals(null, jwtResponse);
    }

    @Test
    void testLoginWithNonexistentUser() {
        LoginRequest loginRequest = new LoginRequest("john", "pass");
        when(userRepository.findByUsername("john")).thenReturn(null);
        JwtResponse jwtResponse = userService.login(loginRequest);
        assertEquals(null, jwtResponse);
    }
}
