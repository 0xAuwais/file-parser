import com.progressoft.training.fileparser.domain.*;
import com.progressoft.training.fileparser.validation.FieldRule;
import com.progressoft.training.fileparser.validation.RowRule;
import com.progressoft.training.fileparser.validation.RowValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParseResultTest {
    Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));
    List<RawRow> rows = List.of(
            new RawRow(1, List.of("transactionId", "merchantId", "amount", "reference")),
            new RawRow(2, List.of("TXN-001", "ACME", "125.00", "Invoice 8842")),
            new RawRow(3, List.of("TXN-002", "", "80.50", "missing merchant")),
            new RawRow(4, List.of("TXN-003", "INITECH", "", "missing amount")));

    RowValidator validator = new RowValidator();

    List<RowValidator.ValidatedRow> results = rows.stream()
            .filter(row -> row.rowNumber() > 1)          // skip the header
            .map(row -> validator.validate(settlement, row))
            .toList();

    List<ParsedRow> valid = results.stream()
            .filter(RowValidator.ValidatedRow::isValid)
            .map(RowValidator.ValidatedRow::row)
            .toList();

    List<ValidationError> errors = results.stream()
            .map(RowValidator.ValidatedRow::errors)
            .flatMap(List::stream)
            .toList();

    ParseResult result = new ParseResult(valid, errors);

    @Test
    public void givenParseResult_whenHasErrors_thenReturnBoolean() {
        Assertions.assertTrue(result.hasErrors(), "Must return true if the parsed result contains errors");
    }

    @Test
    public void givenParseResult_whenValidCount_thenReturnValidRowsCount() {
        Assertions.assertEquals(1, result.validCount(), "Must return the number of valid parsed rows");
    }

    @Test
    public void givenParseResult_whenErrorCount_thenReturnErrorRowsCount() {
        Assertions.assertEquals(2, result.errorCount(), "Must return the number of parsed rows with errors");
    }

}
