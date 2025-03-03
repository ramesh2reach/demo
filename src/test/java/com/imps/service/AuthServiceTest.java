package com.imps.service;

import com.imps.dto.LoginRequest;
import com.imps.dto.RegisterRequest;
import com.imps.entity.User;
import com.imps.enums.Role;
import com.imps.exceptions.UsernameAlreadyExistsException;
import com.imps.repository.UserRepository;
import com.imps.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_Success() {
        RegisterRequest request = new RegisterRequest("user1", "password123", Role.USER);
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        User user = new User(1L, "user1", "encodedPassword", Role.USER);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authService.registerUser(request);

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_UsernameExists_ThrowsException() {
        RegisterRequest request = new RegisterRequest("user1", "password123", Role.USER);
        when(userRepository.existsByUsername("user1")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> authService.registerUser(request));
    }

    @Test
    void loginUser_Success() {
        LoginRequest request = new LoginRequest("user1", "password123");
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("jwtToken");

        String token = authService.authenticateUser(request);

        assertNotNull(token);
        assertEquals("jwtToken", token);
    }
}