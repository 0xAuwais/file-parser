import com.progressoft.training.fileparser.domain.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationErrorTest {
    int rowNumber = 1;
    String fieldName = "my-field";
    String message = "error message";
    ValidationError myError = new ValidationError(rowNumber, fieldName, message);

    @Test
    public void givenValidParameters_whenToString_thenReturnValidString() {

        Assertions.assertEquals(("row " + rowNumber + ", field '" + fieldName + "': " + message), myError.toString(), "Must return valid string");
    }
}
