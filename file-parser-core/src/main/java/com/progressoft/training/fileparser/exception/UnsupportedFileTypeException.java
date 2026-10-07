package com.progressoft.training.fileparser.exception;

public class UnsupportedFileTypeException extends FileParserException {
    public UnsupportedFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnsupportedFileTypeException(String message) {
        super(message);
    }
}
