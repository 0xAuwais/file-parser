package com.progressoft.training.fileparser.validation.rules;

import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.validation.RowRule;
import com.progressoft.training.fileparser.validation.RowValidator;

import java.util.List;
import java.util.Optional;

public class FieldCountRule implements RowRule {
    @Override
    public Optional<RowValidator.ValidatedRow> check(int rowNumber, List<FieldDefinition> fields, List<String> values) {
        if (values.size() != fields.size()) {
            return Optional.of(new RowValidator.ValidatedRow(null,
                    List.of(new ValidationError(rowNumber, "All", "Expected " + fields.size() + " fields found " + values.size()))));
        } else return Optional.empty();
    }
}
