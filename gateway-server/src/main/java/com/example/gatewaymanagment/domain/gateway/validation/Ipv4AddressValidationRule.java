package com.example.gatewaymanagment.domain.gateway.validation;

import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationRule;
import com.example.gatewaymanagment.domain.common.validation.ValidationResult;
import com.example.gatewaymanagment.domain.gateway.GatewayCommand;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Ipv4AddressValidationRule implements ValidationRule<GatewayCommand> {
    private static final String IPV4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    @Override
    public void validate(ValidationResult result, GatewayCommand command) {
        Pattern ipv4Pattern = Pattern.compile(IPV4_REGEX);
        if (command != null && command.getIpv4Address() != null && command.getIpv4Address().isPresent()) {
            Matcher matcher = ipv4Pattern.matcher(command.getIpv4Address().get());
            if (!matcher.matches()) {
                result.addError(new ValidationError("invalid_value", "ipv4Address", "Invalid IP address"));
            }
        }
    }
}
