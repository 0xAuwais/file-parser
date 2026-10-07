package com.progressoft.training.fileparser.validation;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface RowRule {
    Optional<RowValidator.ValidatedRow> check(int rowNumber, List<FieldDefinition> fields, List<String> values);
}
