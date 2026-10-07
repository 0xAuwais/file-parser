import com.progressoft.training.fileparser.domain.RawRow;
import com.progressoft.training.fileparser.exception.FileParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RawRowTest {
    @Test
    public void givenInvalidRowNumber_whenNewRawRow_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new RawRow(-1, List.of()), "Must throw IllegalArgumentException");
    }

    @Test
    public void givenNullValueList_whenNewRawRow_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new RawRow(0, null), "Must throw IllegalArgumentException");
    }

    @Test
    public void givenValidRow_whenNewRawrow_thenCreateRow() {
        RawRow myRow = new RawRow(1, List.of("Value1", "Value2"));
        Assertions.assertEquals(new RawRow(1, List.of("Value1", "Value2")), myRow, "Must create valid row");
    }
}
