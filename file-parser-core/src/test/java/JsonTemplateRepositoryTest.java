import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;
import com.progressoft.training.fileparser.repository.JsonTemplateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class JsonTemplateRepositoryTest {
    JsonTemplateRepository store;
    JsonTemplateRepository testStore = new JsonTemplateRepository(TestResources.path("testtemplates.json"));

    @BeforeEach
    void init() {
        store = new JsonTemplateRepository(TestResources.path("templates.json"));
    }

    @Test
    public void givenNull_whenParameterizedConstructor_thenThrowException() {
        Assertions.assertThrows(FileParserException.class, () -> new InMemoryTemplateRepository(null), "Must throw IllegalArgumentException");
    }

    @Test
    public void givenTemplateName_whenFindByName_thenReturnTemplateOptional() {
        Assertions.assertEquals(Optional.of(
                new Template("merchant-settlement", List.of(
                        new FieldDefinition("transactionId", 12, true),
                        new FieldDefinition("merchantId", 8, true),
                        new FieldDefinition("amount", 15, true),
                        new FieldDefinition("reference", 30, false)))), store.findByName("merchant-settlement"), "Must return valid optional");
    }

    @Test
    public void givenInvalidTemplateName_whenFindByName_thenReturnEmptyOptional() {
        Assertions.assertEquals(Optional.empty(), store.findByName("invalid-name"), "Must return empty optional");
    }

    @Test
    public void givenInvalidTemplateName_whenDeleteByName_thenThrowException() {
        Assertions.assertThrows(TemplateNotFoundException.class, () -> store.deleteByName("invalid-template"), "Must throw TemplateNotFoundException");
    }
}

