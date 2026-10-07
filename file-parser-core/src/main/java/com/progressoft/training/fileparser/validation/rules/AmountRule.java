package com.progressoft.training.fileparser.validation.rules;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.validation.FieldRule;

import java.util.Optional;

public class AmountRule implements FieldRule {
    @Override
    public Optional<ValidationError> check(int rowNumber, FieldDefinition field, String value) {
        if (field.name().equals("amount")) {
            double amount;
            try {
                amount = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return Optional.of(new ValidationError(rowNumber, field.name(), "'amount' field has to contain parseable double"));
            }
            if (amount<=0) {
                return Optional.of(new ValidationError(rowNumber, field.name(), "'amount' field must contain a value larger than zero"));
            }
        } return Optional.empty();
    }
}
