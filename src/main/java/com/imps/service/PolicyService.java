package com.imps.service;


import com.imps.dto.PolicyRequest;
import com.imps.dto.PolicyResponse;
import com.imps.entity.InsurancePolicy;
import com.imps.entity.User;
import com.imps.enums.Role;
import com.imps.exceptions.PolicyNotFoundException;
import com.imps.exceptions.UnauthorizedAccessException;
import com.imps.repository.PolicyRepository;
import com.imps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new policy (USER only)
    public PolicyResponse createPolicy(PolicyRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        InsurancePolicy policy = new InsurancePolicy();
        policy.setPolicyName(request.getPolicyName());
        policy.setPolicyType(request.getPolicyType());
        policy.setPremiumAmount(request.getPremiumAmount());
        policy.setUser(user);
        InsurancePolicy savedPolicy = policyRepository.save(policy);
        return mapToPolicyResponse(savedPolicy);
    }

    // Fetch all policies (ADMIN only)
    public List<PolicyResponse> getAllPolicies() {
        List<InsurancePolicy> policies = policyRepository.findAll();
        return policies.stream()
                .map(this::mapToPolicyResponse)
                .toList();
    }

    // Fetch a specific policy (USER or ADMIN)
    public PolicyResponse getPolicyById(Long id, Long userId, Role userRole) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found"));
        if (userRole == Role.USER && !policy.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You are not authorized to access this policy");
        }
        return mapToPolicyResponse(policy);
    }

    // Update a policy (USER only)
    public PolicyResponse updatePolicy(Long id, PolicyRequest request, Long userId) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found"));
        if (!policy.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You are not authorized to update this policy");
        }
        policy.setPolicyName(request.getPolicyName());
        policy.setPolicyType(request.getPolicyType());
        policy.setPremiumAmount(request.getPremiumAmount());
        InsurancePolicy updatedPolicy = policyRepository.save(policy);
        return mapToPolicyResponse(updatedPolicy);
    }

    // Delete a policy (USER or ADMIN)
    public void deletePolicy(Long id, Long userId, Role userRole) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found"));
        if (userRole == Role.USER && !policy.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this policy");
        }
        policyRepository.delete(policy);
    }

    // Helper method to map InsurancePolicy to PolicyResponse
    private PolicyResponse mapToPolicyResponse(InsurancePolicy policy) {
        PolicyResponse response = new PolicyResponse();
        response.setId(policy.getId());
        response.setPolicyName(policy.getPolicyName());
        response.setPolicyType(policy.getPolicyType());
        response.setPremiumAmount(policy.getPremiumAmount());
        response.setUserId(policy.getUser().getId());
        return response;
    }
}