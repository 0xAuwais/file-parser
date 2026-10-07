package com.progressoft.training.fileparser.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record ParsedRow(int rowNumber, Map<String, String> values) {
    public ParsedRow {
        values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
    }

    public String get(String fieldName) {
        return values.get(fieldName);
    }
}
