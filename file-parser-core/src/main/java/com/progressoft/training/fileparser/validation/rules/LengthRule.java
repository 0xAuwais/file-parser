package com.progressoft.training.fileparser.validation.rules;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.validation.FieldRule;

import java.util.Optional;

public class LengthRule implements FieldRule {
    @Override
    public Optional<ValidationError> check(int rowNumber, FieldDefinition field, String value) {
        if (value.length() > field.length()) {
            return Optional.of(new ValidationError(rowNumber, field.name(), "is " + value.length() + " characters, maximum is " + field.length()));
        } else return Optional.empty();
    }
}
