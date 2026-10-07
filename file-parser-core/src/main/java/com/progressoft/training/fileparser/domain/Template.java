package com.progressoft.training.fileparser.domain;

import com.progressoft.training.fileparser.exception.FileParserException;

import java.util.*;

public record Template(String name, List<FieldDefinition> fields) {
    public Template(String name, List<FieldDefinition> fields) {
        if (name.isBlank()) {
            throw new FileParserException("Template name must not be blank");
        }
        if (fields.isEmpty()) {
            throw new FileParserException("Fields list must not be empty");
        }
        this.name = name;
        this.fields = List.copyOf(fields);
        long distinctFieldNames = fields.stream().map(FieldDefinition::name).distinct().count();
        if (fields.size() != distinctFieldNames) {
            throw new FileParserException("Template '" + name + "' has duplicate fields");
        }
    }

    public Optional<FieldDefinition> field(String fieldname) {
        return fields.stream().filter(field -> field.name().equals(fieldname)).findFirst();
    }

    public List<String> fieldNames() {
        List<String> fieldsResult = new ArrayList<>();
        for (FieldDefinition field : fields) {
            fieldsResult.add(field.name());
        }
        return fieldsResult;
    }

}
