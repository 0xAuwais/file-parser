package com.progressoft.training.fileparser.parser;

import com.progressoft.training.fileparser.exception.FileReadException;
import com.progressoft.training.fileparser.exception.UnsupportedFileTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileParserFactory {
    private final List<FileParser> fileParsers;

    public FileParserFactory() {
        fileParsers = new ArrayList<>();
        fileParsers.add(new TxtFileParser());
        fileParsers.add(new CsvFileParser());
    }

    public FileParserFactory(List<FileParser> fileParsers) {

        this.fileParsers = fileParsers;
    }

    public FileParser getParser(Path file) {
        Optional<FileParser> fileParser = fileParsers.stream().filter(parser -> {
            try {
                return parser.supportedType(Files.probeContentType(file));
            } catch (IOException e) {
                throw new FileReadException("Error reading the provided file", e);
            }
        }).findFirst();
        if (fileParser.isEmpty()) throw new UnsupportedFileTypeException("File type not supported");
        return fileParser.get();
    }
}



