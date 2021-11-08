package com.example.gatewaymanagment.domain.common.validation;

public class ValidationException extends DomainException {
    private ValidationResult validationResult;

    public ValidationException(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public ValidationException(String code, String field, String message) {
        validationResult = new ValidationResult();
        validationResult.addError(new ValidationError(code, field, message));
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

}
