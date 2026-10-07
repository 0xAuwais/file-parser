import com.progressoft.training.fileparser.UseCaseExecutor;
import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;

import com.progressoft.training.fileparser.usecase.UpdateTemplateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UpdateTemplateUseCaseTest {
    private final Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));

    InMemoryTemplateRepository store = new InMemoryTemplateRepository(List.of(settlement));
    UpdateTemplateUseCase update = new UpdateTemplateUseCase(store);

    @Test
    public void givenNewTemplate_whenUpdate_thenUpdateTemplate() {
        UseCaseExecutor.run(update, new Template("merchant-settlement", List.of(
                new FieldDefinition("transactionId", 10, true))
        ));
        Assertions.assertEquals(store.findByName("merchant-settlement").get(), new Template("merchant-settlement", List.of(
                new FieldDefinition("transactionId", 10, true))));
    }
}
