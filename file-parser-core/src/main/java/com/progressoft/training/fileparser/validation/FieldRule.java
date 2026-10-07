package com.progressoft.training.fileparser.validation;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;

import java.util.Optional;

@FunctionalInterface
public interface FieldRule{
    Optional<ValidationError> check(int rowNumber, FieldDefinition field, String value);
}

