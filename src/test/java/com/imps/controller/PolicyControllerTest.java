package com.imps.controller;

import com.imps.dto.PolicyRequest;
import com.imps.dto.PolicyResponse;
import com.imps.service.PolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PolicyController.class)
public class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PolicyService policyService;


    @InjectMocks
    private PolicyController policyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void createPolicy_Success() throws Exception {
        PolicyRequest request = new PolicyRequest("Travel Insurance", "TRAVEL", 200.0);
        PolicyResponse response = new PolicyResponse(1L, "Travel Insurance", "TRAVEL", 200.0, 1L);

        when(policyService.createPolicy(any(PolicyRequest.class), anyLong())).thenReturn(response);

        mockMvc.perform(post("/api/policies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"policyName\":\"Travel Insurance\",\"policyType\":\"TRAVEL\",\"premiumAmount\":200.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.policyName").value("Travel Insurance"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllPolicies_Success() throws Exception {
        PolicyResponse response = new PolicyResponse(1L, "Travel Insurance", "TRAVEL", 200.0, 1L);

        when(policyService.getAllPolicies()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/api/policies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyName").value("Travel Insurance"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void getPolicyById_Success() throws Exception {
        PolicyResponse response = new PolicyResponse(1L, "Travel Insurance", "TRAVEL", 200.0, 1L);

        when(policyService.getPolicyById(anyLong(), anyLong(), any())).thenReturn(response);

        mockMvc.perform(get("/api/policies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyName").value("Travel Insurance"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void updatePolicy_Success() throws Exception {
        PolicyRequest request = new PolicyRequest("Updated Travel Insurance", "TRAVEL", 250.0);
        PolicyResponse response = new PolicyResponse(1L, "Updated Travel Insurance", "TRAVEL", 250.0, 1L);

        when(policyService.updatePolicy(anyLong(), any(PolicyRequest.class), anyLong())).thenReturn(response);

        mockMvc.perform(put("/api/policies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"policyName\":\"Updated Travel Insurance\",\"policyType\":\"TRAVEL\",\"premiumAmount\":250.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyName").value("Updated Travel Insurance"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void deletePolicy_Success() throws Exception {
        mockMvc.perform(delete("/api/policies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}