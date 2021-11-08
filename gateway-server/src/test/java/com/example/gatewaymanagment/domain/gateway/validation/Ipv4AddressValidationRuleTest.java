package com.example.gatewaymanagment.domain.gateway.validation;

import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.gateway.GatewayCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Ipv4AddressValidationRule Test")
class Ipv4AddressValidationRuleTest {
    @InjectMocks
    private Ipv4AddressValidationRule rule;

    @Test
    void test_invalidIpAddress_3Numbers() {
        // Given
        GatewayCommand command = new GatewayCommand();
        command.setIpv4Address(Optional.of("1.1.1"));
        ValidationResult result = new ValidationResult();

        // When
        rule.validate(result, command);

        // Then
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);

        assertEquals("invalid_value", error.getCode());
        assertEquals("ipv4Address", error.getPointer());
        assertEquals("Invalid IP address", error.getMessage());
    }

    @Test
    void test_invalidIpAddress2_NotInRange1_255() {
        // Given
        GatewayCommand command = new GatewayCommand();
        command.setIpv4Address(Optional.of("256.0.1.255"));
        ValidationResult result = new ValidationResult();

        // When
        rule.validate(result, command);

        // Then
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);

        assertEquals("invalid_value", error.getCode());
        assertEquals("ipv4Address", error.getPointer());
        assertEquals("Invalid IP address", error.getMessage());
    }

    @Test
    void test_invalidIpAddress_NotNumber() {
        // Given
        GatewayCommand command = new GatewayCommand();
        command.setIpv4Address(Optional.of("ABC.ZA.1.2"));
        ValidationResult result = new ValidationResult();

        // When
        rule.validate(result, command);

        // Then
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);

        assertEquals("invalid_value", error.getCode());
        assertEquals("ipv4Address", error.getPointer());
        assertEquals("Invalid IP address", error.getMessage());
    }
}