import com.progressoft.training.fileparser.UseCaseExecutor;
import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;
import com.progressoft.training.fileparser.usecase.CreateTemplateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreateTemplateUseCaseTest {

    InMemoryTemplateRepository store = new InMemoryTemplateRepository();
    CreateTemplateUseCase create = new CreateTemplateUseCase(store);

    Template myTemplate = new Template("my-template", List.of(new FieldDefinition("myField", 12, true)));


    @Test
    public void givenNewTemplate_whenCreate_thenAddTemplate() {
        UseCaseExecutor.run(create, myTemplate);
        Assertions.assertEquals(myTemplate, store.findByName("my-template").get());
    }

    @Test
    public void givenDuplicateTemplateName_whenCreate_thenThrowException() {
        UseCaseExecutor.run(create, myTemplate);
        Assertions.assertThrows(FileParserException.class, () -> UseCaseExecutor.run(create, myTemplate));

    }
}
