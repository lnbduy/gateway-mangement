package com.example.gatewaymanagment.domain.gateway;

import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.gateway.validation.Ipv4AddressValidationRule;
import com.example.gatewaymanagment.domain.gateway.validation.MaximumDevicesValidationRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GatewayValidator test")
class GatewayValidatorTest {
    private Ipv4AddressValidationRule ipv4AddressValidationRule = new Ipv4AddressValidationRule();
    private MaximumDevicesValidationRule maximumDevicesValidationRule = new MaximumDevicesValidationRule();
    private GatewayValidator validator = new GatewayValidator(ipv4AddressValidationRule, maximumDevicesValidationRule);

    @Test
    void test_validate() {
        // Given
        GatewayCommand command = new GatewayCommand();
        command.setIpv4Address(Optional.of("1.1.1"));

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validator.validate(command);
        });

        ValidationError error = exception.getValidationResult().getErrors().get(0);

        // Then
        assertEquals("invalid_value", error.getCode());
        assertEquals("ipv4Address", error.getPointer());
        assertEquals("Invalid IP address", error.getMessage());
    }
}