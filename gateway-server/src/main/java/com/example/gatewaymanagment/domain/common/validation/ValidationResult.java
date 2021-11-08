package com.example.gatewaymanagment.domain.common.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationResult {
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
       errors.add(error);
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }
}
