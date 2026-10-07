package com.progressoft.training.fileparser.parser;

import com.progressoft.training.fileparser.domain.RawRow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParser implements FileParser {

    public List<RawRow> parse(Path file) throws FileNotFoundException {
        List<RawRow> rows = new ArrayList<>();
        try {
            int rowCount = 0;
            List<String> rawContent = Files.readAllLines(file);
            for (String row : rawContent) {
                if (row.isEmpty()) {
                    continue;
                }
                rowCount++;
                if (rowCount > 1) {
                    rows.add(new RawRow(rowCount, List.of(row.split(",", -1))));
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return rows;
    }

    @Override
    public boolean supportedType(String type) {
        return "text/csv".equals(type);
    }
}
