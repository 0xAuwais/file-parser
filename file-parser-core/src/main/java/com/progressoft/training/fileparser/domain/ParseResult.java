package com.progressoft.training.fileparser.domain;

import java.util.List;

public record ParseResult<T>(List<T> validRows, List<ValidationError> errors) {

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public int validCount() {
        return validRows.size();
    }

    public int errorCount() {
        return errors.size();
    }
}
