import com.progressoft.training.fileparser.domain.*;
import com.progressoft.training.fileparser.validation.FieldRule;
import com.progressoft.training.fileparser.validation.RowRule;
import com.progressoft.training.fileparser.validation.RowValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RowValidatorTest {
    Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));

    List<RawRow> rows = List.of(
            new RawRow(1, List.of("transactionId", "merchantId", "amount", "reference")),
            new RawRow(2, List.of("TXN-001", "ACME", "125.00", "Invoice 8842")),
            new RawRow(3, List.of("TXN-002", "", "80.50", "missing merchant")),
            new RawRow(4, List.of("TXN-003", "INITECH", "94494994994949494455454534343434343", "missing amount")),
            new RawRow(5, List.of("TXN-004", "This must result in an error")));

    RowValidator validator = new RowValidator(settlement);

    List<RowValidator.ValidatedRow> results = rows.stream()
            .filter(row -> row.rowNumber() > 1)          // skip the header
            .map(row -> validator.validate(settlement, row))
            .toList();

    @Test
    public void givenInvalidFieldCount_whenValidateRows_thenAddErrorAndReturn(){
        Assertions.assertFalse(results.get(3).isValid(), "Must return false");
    }
    @Test
    public void givenEmptyField_whenValidateRow_thenAddError(){
        Assertions.assertFalse(results.get(1).isValid(), "Must return false");
    }
    @Test
    public void givenInvalidFieldLength_whenValidateRows_thenAddError(){
        Assertions.assertFalse(results.get(2).isValid(), "Must return false");
    }
    @Test
    public void givenValidRow_whenValidateRows_thenReturnParsedRow(){
        Assertions.assertTrue(results.get(0).isValid(), "Must return true");
    }
}
