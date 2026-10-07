package com.progressoft.training.fileparser.exception;

public class TemplateNotFoundException extends FileParserException{
    public TemplateNotFoundException(String templateName){
        super("No template named "+templateName);
    }
}
