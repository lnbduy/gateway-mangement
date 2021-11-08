package com.example.gatewaymanagment.domain.gateway;

import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.gateway.validation.Ipv4AddressValidationRule;
import com.example.gatewaymanagment.domain.gateway.validation.MaximumDevicesValidationRule;
import org.springframework.stereotype.Component;

@Component
public class GatewayValidator {
    private final Ipv4AddressValidationRule ipv4AddressValidationRule;
    private final MaximumDevicesValidationRule maximumDevicesValidationRule;

    public GatewayValidator(Ipv4AddressValidationRule ipv4AddressValidationRule, MaximumDevicesValidationRule maximumDevicesValidationRule) {
        this.ipv4AddressValidationRule = ipv4AddressValidationRule;
        this.maximumDevicesValidationRule = maximumDevicesValidationRule;
    }

    public void validate(GatewayCommand command) {
        ValidationResult result = new ValidationResult();
        ipv4AddressValidationRule.validate(result, command);
        maximumDevicesValidationRule.validate(result, command);
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
    }
}
