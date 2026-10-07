import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.ParseResult;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.mapper.RowMapper;
import com.progressoft.training.fileparser.parser.FileParserFactory;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;
import com.progressoft.training.fileparser.usecase.ParseFileUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ParseFileUseCaseMapperTest {

    record MerchantSettlementRow(String transactionId, String merchantId, double amount, String reference) {
    }

    @Test
    public void givenRowMapper_whenExecuteParseFile_thenReturnMappedTargetObjects(@TempDir Path tempDir) throws IOException {
        Template settlement = new Template("merchant-settlement", List.of(
                new FieldDefinition("transactionId", 12, true),
                new FieldDefinition("merchantId", 8, true),
                new FieldDefinition("amount", 15, true),
                new FieldDefinition("reference", 30, false)));

        InMemoryTemplateRepository store = new InMemoryTemplateRepository(List.of(settlement));

        Path rowsFile = tempDir.resolve("rows.csv");
        Files.writeString(rowsFile, """
                transactionId,merchantId,amount,reference
                TXN-001,ACME,125.00,Invoice 8842
                """);

        ParseResult<MerchantSettlementRow> result = getMerchantSettlementRowParseResult(store, rowsFile);

        Assertions.assertEquals(
                List.of(new MerchantSettlementRow("TXN-001", "ACME", 125.00, "Invoice 8842")),
                result.validRows(),
                "Must return the valid rows mapped to the target object");
        Assertions.assertTrue(result.errors().isEmpty(), "Must return no errors for a fully valid file");
    }

    private ParseResult<MerchantSettlementRow> getMerchantSettlementRowParseResult(InMemoryTemplateRepository store, Path rowsFile) {
        RowMapper<MerchantSettlementRow> mapper = row -> new MerchantSettlementRow(
                row.get("transactionId"),
                row.get("merchantId"),
                Double.parseDouble(row.get("amount")),
                row.get("reference"));

        ParseFileUseCase<MerchantSettlementRow> parse = new ParseFileUseCase<>(store, new FileParserFactory(), mapper);

        ParseResult<MerchantSettlementRow> result = parse.execute(
                new ParseFileUseCase.ParseFileCommand("merchant-settlement", rowsFile));
        return result;
    }
}
