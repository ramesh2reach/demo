package com.imps.dto;

public class PolicyRequest {
    private String policyName;
    private String policyType;
    private double premiumAmount;

    public PolicyRequest() {
    }

    public PolicyRequest(String policyName, String policyType, double premiumAmount) {
        this.policyName = policyName;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    // Getters and Setters

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
}