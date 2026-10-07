import com.progressoft.training.fileparser.domain.ListTemplatesQuery;
import com.progressoft.training.fileparser.repository.JsonTemplateRepository;
import com.progressoft.training.fileparser.usecase.ListTemplatesUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ListTemplatesUseCaseTest {
    private final JsonTemplateRepository store = new JsonTemplateRepository(TestResources.path("templates.json"));
    ListTemplatesUseCase list = new ListTemplatesUseCase(store);

    @Test
    public void givenListTemplatesQuery_whenExecute_thenReturnTemplatesList() {
        Assertions.assertEquals(store.findAll(), list.execute(new ListTemplatesQuery()), "Should return templates list");
    }


}
