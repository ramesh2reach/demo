package com.imps.controller;

import com.imps.dto.PolicyRequest;
import com.imps.dto.PolicyResponse;
import com.imps.enums.Role;
import com.imps.exceptions.UnauthorizedAccessException;
import com.imps.security.UserPrincipal;
import com.imps.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    // Create a new policy (USER only)
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(@RequestBody PolicyRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        //Long userId = Long.parseLong(userDetails.getUsername());
        UserPrincipal userPrincipal = (UserPrincipal) userDetails;
        Long userId = userPrincipal.getId(); // Correctly get user ID
        PolicyResponse response = policyService.createPolicy(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Fetch all policies (ADMIN only)
    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getAllPolicies(@AuthenticationPrincipal UserDetails userDetails) {
        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new UnauthorizedAccessException("Only ADMIN can fetch all policies");
        }
        List<PolicyResponse> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    // Fetch a specific policy (USER or ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        Role userRole = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ? Role.ADMIN : Role.USER;
        PolicyResponse response = policyService.getPolicyById(id, userId, userRole);
        return ResponseEntity.ok(response);
    }

    // Update a policy (USER only)
    @PutMapping("/{id}")
    public ResponseEntity<PolicyResponse> updatePolicy(@PathVariable Long id, @RequestBody PolicyRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        PolicyResponse response = policyService.updatePolicy(id, request, userId);
        return ResponseEntity.ok(response);
    }

    // Delete a policy (USER or ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        Role userRole = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ? Role.ADMIN : Role.USER;
        policyService.deletePolicy(id, userId, userRole);
        return ResponseEntity.noContent().build();
    }
}