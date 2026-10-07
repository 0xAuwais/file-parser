import com.progressoft.training.fileparser.domain.ParseResult;
import com.progressoft.training.fileparser.domain.ParsedRow;
import com.progressoft.training.fileparser.domain.ValidationError;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.parser.FileParserFactory;
import com.progressoft.training.fileparser.repository.JsonTemplateRepository;
import com.progressoft.training.fileparser.usecase.ParseFileUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;


public class ParseFileUseCaseTest {
    Path file = TestResources.path("templates.json");
    JsonTemplateRepository store = new JsonTemplateRepository(file);
    FileParserFactory fileParserFactory = new FileParserFactory();
    Path rowsFile = TestResources.path("samplerows.csv");

    ParseFileUseCase parse = new ParseFileUseCase(store, fileParserFactory);
    ParseResult result = parse.execute(new ParseFileUseCase.ParseFileCommand("merchant-settlement", rowsFile));


    @Test
    public void givenInvalidTemplateName_whenExecuteParseFile_thenThrowException() {
        Assertions.assertThrows(TemplateNotFoundException.class, () -> parse.execute(
                new ParseFileUseCase.ParseFileCommand("invalid-template", rowsFile)), "Must throw TemplateNotFoundException");
    }

    @Test
    public void givenParseFileCommand_whenExecuteParseFile_thenReturnValidParse() {
        Assertions.assertEquals(
                new ParseResult(
                        List.of(new ParsedRow(2, Map.of("transactionId", "TXN-001",
                                "merchantId", "ACME",
                                "amount", "125.00",
                                "reference", "Invoice 8842"))),
                        List.of(new ValidationError(3, "merchantId", "is required but was empty"),
                                new ValidationError(4, "amount", "is required but was empty"),
                                new ValidationError(4, "amount", "'amount' field has to contain parseable double"),
                                new ValidationError(5, "All", "Expected 4 fields found 3")))
                , result, "Must return valid ParseResult");
    }

    @Test
    public void givenParseFileCommand_whenInvalidPath_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> parse.execute(new ParseFileUseCase.ParseFileCommand("a", Path.of("invalid"))));
    }

}
