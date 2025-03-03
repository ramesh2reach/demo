package com.imps.service;

import com.imps.dto.PolicyRequest;
import com.imps.dto.PolicyResponse;
import com.imps.entity.InsurancePolicy;
import com.imps.entity.User;
import com.imps.enums.Role;
import com.imps.exceptions.UnauthorizedAccessException;
import com.imps.repository.PolicyRepository;
import com.imps.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@Service
public class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PolicyService policyService;

    @Test
    void createPolicy_Success() {
        PolicyRequest request = new PolicyRequest("Travel Insurance", "TRAVEL", 200.0);
        User user = new User(1L, "user1", "password123", Role.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        InsurancePolicy policy = new InsurancePolicy(1L, "Travel Insurance", "TRAVEL", 200.0, user);
        when(policyRepository.save(any(InsurancePolicy.class))).thenReturn(policy);

        PolicyResponse response = policyService.createPolicy(request, 1L);

        assertNotNull(response);
        assertEquals("Travel Insurance", response.getPolicyName());
    }

    @Test
    void getPolicyById_Success() {
        User user = new User(1L, "user1", "password123", Role.USER);
        InsurancePolicy policy = new InsurancePolicy(1L, "Travel Insurance", "TRAVEL", 200.0, user);
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        PolicyResponse response = policyService.getPolicyById(1L, 1L, Role.USER);

        assertNotNull(response);
        assertEquals("Travel Insurance", response.getPolicyName());
    }

    @Test
    void getPolicyById_UnauthorizedAccess_ThrowsException() {
        User user = new User(1L, "user1", "password123", Role.USER);
        InsurancePolicy policy = new InsurancePolicy(1L, "Travel Insurance", "TRAVEL", 200.0, user);
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        assertThrows(UnauthorizedAccessException.class, () -> policyService.getPolicyById(1L, 2L, Role.USER));
    }
}