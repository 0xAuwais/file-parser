package com.progressoft.training.fileparser.usecase;

import com.progressoft.training.fileparser.UseCase;
import com.progressoft.training.fileparser.domain.ParseResult;
import com.progressoft.training.fileparser.domain.ParsedRow;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.exception.FileReadException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.mapper.RowMapper;
import com.progressoft.training.fileparser.parser.FileParserFactory;
import com.progressoft.training.fileparser.repository.TemplateReader;
import com.progressoft.training.fileparser.validation.RowValidator;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public class ParseFileUseCase<T> implements UseCase<ParseFileUseCase.ParseFileCommand, ParseResult<T>> {
    public record ParseFileCommand(String templateName, Path file) {
    }

    private record ValidatedRows(List<ParsedRow> valid, List<ValidationError> errors) {
    }

    private final TemplateReader templates;
    private final FileParserFactory fileParser;
    private final RowMapper<T> mapper;

    @SuppressWarnings("unchecked")
    public ParseFileUseCase(TemplateReader templates, FileParserFactory fileParser) {
        this(templates, fileParser, row -> (T) row);
    }

    public ParseFileUseCase(TemplateReader templates, FileParserFactory fileParser, RowMapper<T> mapper) {
        this.templates = templates;
        this.fileParser = fileParser;
        this.mapper = mapper;
    }

    @Override
    public ParseResult<T> execute(ParseFileCommand command) throws FileReadException {
        ValidatedRows validated = validateRows(command);
        List<T> mapped = validated.valid().stream().map(mapper::map).toList();
        return new ParseResult<>(mapped, validated.errors());
    }

    private ValidatedRows validateRows(ParseFileCommand command) throws FileReadException {
        Template template = templates.findByName(command.templateName()).orElseThrow(() -> new TemplateNotFoundException(command.templateName()));

        RowValidator validator = new RowValidator(template);
        try {
            List<RowValidator.ValidatedRow> results = fileParser.getParser(command.file())
                    .parse(command.file())
                    .stream()
                    .filter(row -> row.rowNumber() > 1)          // skip the header
                    .map(row -> validator.validate(template, row))
                    .toList();

            List<ParsedRow> valid = results.stream()
                    .filter(RowValidator.ValidatedRow::isValid)
                    .map(RowValidator.ValidatedRow::row)
                    .toList();

            List<ValidationError> errors = results.stream()
                    .map(RowValidator.ValidatedRow::errors)
                    .flatMap(List::stream)
                    .toList();

            return new ValidatedRows(valid, errors);
        } catch (FileNotFoundException e) {
            throw new FileReadException("Cannot read the provided file path", e);
        }
    }

}
