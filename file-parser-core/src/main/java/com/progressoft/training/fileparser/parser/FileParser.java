package com.progressoft.training.fileparser.parser;

import com.progressoft.training.fileparser.domain.RawRow;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public interface FileParser {
    public List<RawRow> parse(Path file) throws FileNotFoundException;

    boolean supportedType(String type);
}
