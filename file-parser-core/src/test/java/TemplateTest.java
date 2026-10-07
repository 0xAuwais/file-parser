import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TemplateTest {
    private final Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));

    @Test
    public void givenBlankName_whenNewTemplate_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new Template("", List.of(
                        new FieldDefinition("transactionId", 12, true),
                        new FieldDefinition("merchantId", 8, true),
                        new FieldDefinition("amount", 15, true),
                        new FieldDefinition("reference", 30, false)))
                , "Must throw IllegalArgumentException");
    }

    @Test
    public void givenEmptyList_whenNewTemplate_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new Template("merchant-settlement", List.of())
                , "Must throw IllegalArgumentException");
    }

    @Test
    public void givenFieldName_whenField_thenReturnOptional() {
        Assertions.assertEquals(settlement.field("amount"),
                Optional.of(new FieldDefinition("amount", 15, true)), "Method must return valid optional");
    }

    @Test
    public void givenInvalidFieldName_whenField_thenReturnEmptyOptional() {
        Assertions.assertEquals(settlement.field("invalid-field"),
                Optional.empty(), "Method must return empty optional");
    }

    @Test
    public void givenNothing_whenFieldNames_thenReturnFieldNames() {
        Assertions.assertIterableEquals(settlement.fieldNames(), List.of("transactionId", "merchantId", "amount", "reference"), "Method must return list of field names");

    }

    @Test
    public void givenDuplicateFields_whenNewTemplate_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new Template("merchant-settlement", List.of(
                new FieldDefinition("transactionId", 12, true),
                new FieldDefinition("transactionId", 12, true))), "Must throw IllegalArgumentException");
    }

}
