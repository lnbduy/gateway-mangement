package com.example.gatewaymanagment.domain.gateway.validation;

import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.gateway.DeviceCommand;
import com.example.gatewaymanagment.domain.gateway.GatewayCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MaximumDevicesValidationRule Test")
class MaximumDevicesValidationRuleTest {
    @InjectMocks
    private MaximumDevicesValidationRule rule;

    @Test
    void test_validateRule() {
        // Given
        GatewayCommand command = mock(GatewayCommand.class);
        List<DeviceCommand> deviceCommands = mock(List.class);
        when(command.getDeviceCommands()).thenReturn(deviceCommands);
        when(deviceCommands.size()).thenReturn(11);
        ValidationResult result = new ValidationResult();

        // When
        rule.validate(result, command);

        // Then
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);

        assertEquals("exceed_maximum_devices", error.getCode());
        assertEquals("devices", error.getPointer());
        assertEquals("Gateway does not allow more than 10 devices", error.getMessage());
    }
}