package com.example.gatewaymanagment.domain.gateway.validation;

import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.common.validation.ValidationRule;
import com.example.gatewaymanagment.domain.gateway.GatewayCommand;
import org.springframework.stereotype.Component;

@Component
public class MaximumDevicesValidationRule implements ValidationRule<GatewayCommand> {
    @Override
    public void validate(ValidationResult result, GatewayCommand command) {
        if (command != null && command.getDeviceCommands() != null && command.getDeviceCommands().size() > 10) {
            result.addError(new ValidationError("exceed_maximum_devices", "devices", "Gateway does not allow more than 10 devices"));
        }
    }
}
