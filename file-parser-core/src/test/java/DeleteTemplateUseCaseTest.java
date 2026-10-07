import com.progressoft.training.fileparser.UseCaseExecutor;
import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;
import com.progressoft.training.fileparser.usecase.DeleteTemplateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DeleteTemplateUseCaseTest {
    private final Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));

    InMemoryTemplateRepository store = new InMemoryTemplateRepository(List.of(settlement));
    DeleteTemplateUseCase delete = new DeleteTemplateUseCase(store);

    @Test
    public void givenTemplateName_whenDelete_thenDeleteTemplate() {
        UseCaseExecutor.run(delete, "merchant-settlement");
        Assertions.assertTrue(store.findByName("merchant-settlement").isEmpty());
    }

    @Test
    public void givenInvalidTemplateName_whenDelete_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> UseCaseExecutor.run(delete, "invalid-test"));
    }


}
