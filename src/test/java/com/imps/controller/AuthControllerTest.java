package com.imps.controller;

import com.imps.dto.LoginRequest;
import com.imps.dto.RegisterRequest;
import com.imps.entity.User;
import com.imps.enums.Role;
import com.imps.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() throws Exception {
        RegisterRequest request = new RegisterRequest("user1", "password123", Role.USER);
        when(authService.registerUser(any(RegisterRequest.class))).thenReturn(new User(1L, "user1", "encodedPassword", Role.USER));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user1\",\"password\":\"password123\",\"role\":\"USER\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void loginUser_Success() throws Exception {
        LoginRequest request = new LoginRequest("user1", "password123");
        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn("jwtToken");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user1\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }
}