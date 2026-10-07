package com.progressoft.training.fileparser.domain;

import com.progressoft.training.fileparser.exception.FileParserException;

import java.util.List;

public record RawRow(int rowNumber, List<String> values) {
    public RawRow {
        if (rowNumber < 0) {
            throw new FileParserException("Row number must be non-negative");
        }
        if (values == null) {
            throw new FileParserException("List of values must not be null");
        }
        values = List.copyOf(values);
    }
}
