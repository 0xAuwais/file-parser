package com.progressoft.training.fileparser.exception;

public class DuplicateTemplateException extends FileParserException {
    public DuplicateTemplateException(String templateName) {
        super("A template named '" + templateName + "' already exists");
    }
}
