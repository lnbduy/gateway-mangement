package com.example.gatewaymanagment.domain.common.validation;

public interface ValidationRule<C> {
    void validate(ValidationResult result, C command);
}
