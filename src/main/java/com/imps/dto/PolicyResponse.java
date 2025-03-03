package com.imps.dto;

public class PolicyResponse {
    private Long id;
    private String policyName;
    private String policyType;
    private double premiumAmount;
    private Long userId;

    public PolicyResponse() {
    }

    public PolicyResponse(Long id, String policyName, String policyType, double premiumAmount, Long userId) {
        this.id = id;
        this.policyName = policyName;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.userId = userId;
    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}