package com.progressoft.training.fileparser.domain;

public record ValidationError(int rowNumber, String fieldName, String message) {
    @Override
    public String toString(){
        return "row "+rowNumber+", field '"+fieldName+"': "+message;
    }
}
