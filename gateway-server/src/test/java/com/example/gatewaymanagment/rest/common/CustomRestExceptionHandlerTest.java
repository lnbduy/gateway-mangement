package com.example.gatewaymanagment.rest.common;

import com.example.gatewaymanagment.domain.common.validation.NotFoundException;
import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("CustomRestExceptionHandler Test")
class CustomRestExceptionHandlerTest {
    private CustomRestExceptionHandler handler = new CustomRestExceptionHandler();

    @Test
    void handleValidationException() {
        // Given
        ValidationResult validationResult = new ValidationResult();
        validationResult.addError( new ValidationError("invalid_value", "ipv4Address", "Invalid IP address"));
        validationResult.addError( new ValidationError("exceed_max_devices", "devices", "Gateway does not have more than 10 devices"));
        ValidationException exception = new ValidationException(validationResult);

        // When
        ResponseEntity<?> response = handler.handleValidationException(exception);

        // Then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(((HashMap<String, List<Map<String, Object>>>) response.getBody()).get("errors"));
        assertEquals(2, ((HashMap<String, List<Map<String, Object>>>) response.getBody()).get("errors").size());
    }

    @Test
    void handleNotFoundException() {
        // Given
        NotFoundException notFoundException = new NotFoundException("not_found", "serialNumber", "Gateway with serial number 1 not found");

        // When
        ResponseEntity<?> response = handler.handleNotFoundException(notFoundException);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(((HashMap<String, List<Map<String, Object>>>) response.getBody()).get("errors"));
        assertEquals(1, ((HashMap<String, List<Map<String, Object>>>) response.getBody()).get("errors").size());
    }
}