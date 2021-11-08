package com.example.gatewaymanagment.rest.common;

import com.example.gatewaymanagment.domain.common.validation.NotFoundException;
import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        List<Map<String, Object>> restErrors = new ArrayList<>();

        ValidationResult result = ex.getValidationResult();
        for (ValidationError validationError : result.getErrors()) {
            addError(restErrors, validationError);
        }

        Map<String, Object> restErrorResult = new HashMap<>();
        restErrorResult.put("errors", restErrors);
        return new ResponseEntity<>(restErrorResult, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        List<Map<String, Object>> restErrors = new ArrayList<>();
        addError(restErrors, ex);
        Map<String, Object> restErrorResult = new HashMap<>();
        restErrorResult.put("errors", restErrors);
        return new ResponseEntity<>(restErrorResult, HttpStatus.NOT_FOUND);
    }

    private void addError(List<Map<String, Object>> restErrors, ValidationError error) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", error.getCode());
        errorMap.put("message", error.getMessage());
        errorMap.put("pointer", error.getPointer());
        restErrors.add(errorMap);
    }

    private void addError(List<Map<String, Object>> restErrors, NotFoundException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("code", ex.getCode());
        errorMap.put("message", ex.getMessage());
        errorMap.put("pointer", ex.getPointer());
        restErrors.add(errorMap);
    }
}