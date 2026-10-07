package com.progressoft.training.fileparser.domain;

import com.progressoft.training.fileparser.exception.FileParserException;

public record FieldDefinition (String name, int length, boolean required){

    public FieldDefinition {
        if (name.isBlank()) {
            throw new FileParserException("Field name must not be blank");
        }
        if (length < 0) {
            throw new FileParserException("Field length must be non-negative");
        }
    }
    public static class FieldBuilder {
        private String name;
        private int length;
        private boolean required;

        public void setName(String name){
            this.name = name;
        }
        public void setLength(int length){
            this.length = length;
        }
        public void setRequired(boolean required){
            this.required = required;
        }

        public FieldDefinition build(){
            return new FieldDefinition(name, length, required);
        }
    }
}
