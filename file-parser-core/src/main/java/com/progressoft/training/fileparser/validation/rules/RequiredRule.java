package com.progressoft.training.fileparser.validation.rules;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.validation.FieldRule;

import java.util.Optional;

public class RequiredRule implements FieldRule {
    @Override
    public Optional<ValidationError> check(int rowNumber, FieldDefinition field, String value) {
        if (value.isEmpty() && field.required()) {
            return Optional.of(new ValidationError(rowNumber, field.name(), "is required but was empty"));
        } else return Optional.empty();
    }
}
