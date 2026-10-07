import com.progressoft.training.fileparser.UseCaseExecutor;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.repository.JsonTemplateRepository;
import com.progressoft.training.fileparser.usecase.GetTemplateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UseCaseExecutorTest {
    JsonTemplateRepository store = new JsonTemplateRepository(TestResources.path("templates.json"));
    Template template = store.findByName("merchant-settlement").get();

    GetTemplateUseCase get = new GetTemplateUseCase(store);

    @Test
    public void givenUseCase_whenRun_thenRunUseCase() {
        Assertions.assertEquals(UseCaseExecutor.run(get, "merchant-settlement"),
                Optional.of(template), "Should return valid optional");
    }

    @Test
    public void givenUseCase_whenRunTimed_thenTimedRunUseCase() {
        Assertions.assertEquals(UseCaseExecutor.runTimed(get, "merchant-settlement"),
                Optional.of(template), "Should return valid optional");
    }


}
