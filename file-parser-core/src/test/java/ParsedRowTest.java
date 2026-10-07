import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ParsedRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

public class ParsedRowTest {
    LinkedHashMap<String,String> values = new LinkedHashMap<>();

    @Test
    public void givenValidParsedRow_whenNewParsedRow_thenCreateNewRow(){
        values.put("field1","value1");
        values.put("field2","value2");

        ParsedRow myRow = new ParsedRow(1, values);
        Assertions.assertEquals(new ParsedRow(1,values), myRow, "Must create valid parsed row");
    }

    @Test
    public void givenFieldName_whenGet_thenReturnFieldValue(){
        values.put("field1","value1");
        values.put("field2","value2");
        ParsedRow myRow = new ParsedRow(1, values);
        Assertions.assertEquals("value2", myRow.get("field2"), "Must return correct field value");
    }

    @Test
    public void givenValidParsedRow_whenNewParsedRow_thenMaintainOrderOfFields(){
        values.put("transactionId","TX-01");
        values.put("merchantId","SHOP");
        values.put("amount","9.0");
        values.put("reference","");
        ParsedRow myRow = new ParsedRow(1,values);
        Assertions.assertEquals(List.of("transactionId","merchantId","amount","reference"), List.copyOf(myRow.values().keySet()),"Fields must be ordered correctly");
    }
}
