import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.exception.FileParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldDefinitionTest {

    @Test
    public void givenBlankName_whenFieldDefinition_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new FieldDefinition("", 10, true)
                , "Must throw IllegalArgumentException");
    }

    @Test
    public void givenNegativeLength_whenFieldDefinition_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new FieldDefinition("Field", -1, true)
                , "Must throw IllegalArgumentException");
    }

}
