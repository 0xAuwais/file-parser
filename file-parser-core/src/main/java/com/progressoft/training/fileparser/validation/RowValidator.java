package com.progressoft.training.fileparser.validation;

import com.progressoft.training.fileparser.domain.*;
import com.progressoft.training.fileparser.validation.rules.AmountRule;
import com.progressoft.training.fileparser.validation.rules.FieldCountRule;
import com.progressoft.training.fileparser.validation.rules.LengthRule;
import com.progressoft.training.fileparser.validation.rules.RequiredRule;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RowValidator {
    List<FieldRule> fieldRules;
    List<RowRule> rowRules;

    public RowValidator() {
        fieldRules = new ArrayList<>();
        rowRules = new ArrayList<>();

        rowRules.add(new FieldCountRule());
        fieldRules.add(new RequiredRule());
        fieldRules.add(new LengthRule());
    }

    public RowValidator(Template template) {
        fieldRules = new ArrayList<>();
        rowRules = new ArrayList<>();

        rowRules.add(new FieldCountRule());
        fieldRules.add(new RequiredRule());
        fieldRules.add(new LengthRule());

        List<FieldDefinition> fields = template.fields();

        if (fields.stream().anyMatch(f -> f.name().equals("amount"))) {
            fieldRules.add(new AmountRule());
        }
    }

    public RowValidator(List<FieldRule> fieldRules, List<RowRule> rowRules) {
        this.fieldRules = fieldRules;
        this.rowRules = rowRules;
    }

    public record ValidatedRow(ParsedRow row, List<ValidationError> errors) {
        public boolean isValid() {
            return row != null;
        }
    }

    public ValidatedRow validate(Template template, RawRow row) {
        LinkedHashMap<String, String> rowValues = new LinkedHashMap<>();
        List<ValidationError> errors = new ArrayList<>();

        int rowNum = row.rowNumber();
        List<FieldDefinition> fields = template.fields();
        List<String> values = row.values();


        List<Optional<ValidatedRow>> validatedRows = rowRules.stream().map(rule -> rule.check(rowNum, fields, values)).toList();


        boolean hasRowError = validatedRows.stream().anyMatch(Optional::isPresent);
        if (hasRowError)
            return validatedRows.stream().filter(Optional::isPresent).findFirst().get().get();

        for (int i = 0; i < row.values().size(); i++) {

            FieldDefinition field = fields.get(i);
            String value = values.get(i).trim();
            fieldRules.forEach(it -> it.check(rowNum, field, value).ifPresent(errors::add));
        }

        if (errors.isEmpty()) {
            for (int i = 0; i < row.values().size(); i++) {
                rowValues.put(template.fields().get(i).name(), row.values().get(i));
            }
            return new ValidatedRow(new ParsedRow(row.rowNumber(), rowValues), errors);

        }
        return new ValidatedRow(null, errors);
    }
}



