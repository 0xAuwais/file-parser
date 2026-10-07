import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.repository.JsonTemplateRepository;
import com.progressoft.training.fileparser.usecase.GetTemplateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class GetTemplateUseCaseTest {
    JsonTemplateRepository store = new JsonTemplateRepository(TestResources.path("templates.json"));

    GetTemplateUseCase get = new GetTemplateUseCase(store);

    @Test
    public void givenInput_whenExecute_thenReturnTemplateOptional() {
        Assertions.assertEquals(Optional.of(new Template("merchant-settlement", List.of(
                        new FieldDefinition("transactionId", 12, true),
                        new FieldDefinition("merchantId", 8, true),
                        new FieldDefinition("amount", 15, true),
                        new FieldDefinition("reference", 30, false)))), get.execute("merchant-settlement")
                , "Must return valid optional");
    }

}
