import com.progressoft.training.fileparser.domain.FieldDefinition;
import com.progressoft.training.fileparser.domain.Template;
import com.progressoft.training.fileparser.exception.FileParserException;
import com.progressoft.training.fileparser.exception.TemplateNotFoundException;
import com.progressoft.training.fileparser.repository.InMemoryTemplateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InMemoryTemplateRepositoryTest {
    private final Template settlement = new Template("merchant-settlement", List.of(
            new FieldDefinition("transactionId", 12, true),
            new FieldDefinition("merchantId", 8, true),
            new FieldDefinition("amount", 15, true),
            new FieldDefinition("reference", 30, false)));

    InMemoryTemplateRepository store = new InMemoryTemplateRepository(List.of(settlement));

    @Test
    public void givenNoParameters_whenDefaultConstructor_thenGenerateEmptyRepo() {
        InMemoryTemplateRepository test = new InMemoryTemplateRepository();
        Assertions.assertEquals(List.of(), test.findAll(), "Return empty list");
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
    public void givenTemplateRepo_whenFindAll_thenReturnTemplateList() {
        Assertions.assertEquals(List.of(new Template("merchant-settlement", List.of(
                new FieldDefinition("transactionId", 12, true),
                new FieldDefinition("merchantId", 8, true),
                new FieldDefinition("amount", 15, true),
                new FieldDefinition("reference", 30, false)))), store.findAll(), "Must return valid list");
    }

    @Test
    public void givenTemplate_whenSave_thenSaveAndReturnTemplate() {
        Template test = new Template("test-template", List.of(
                new FieldDefinition("myField", 10, true),
                new FieldDefinition("myOtherField", 20, false)
        ));
        store.save(test);
        Template myNewTest = new Template("test-template", List.of(
                new FieldDefinition("myNewField", 10, true),
                new FieldDefinition("myNewOtherField", 20, false)
        ));
        Assertions.assertEquals(test, store.save(myNewTest), "Must return previous template");
    }

    @Test
    public void givenTemplateName_whenDeleteByName_thenDeleteTemplate() {
        store.deleteByName("merchant-settlement");
        Assertions.assertEquals(store.findAll(), List.of(), "Must return empty list now");
    }

    @Test
    public void givenInvalidTemplateName_whenDeleteByName_thenThrowException() {
        Assertions.assertThrows(TemplateNotFoundException.class, () -> store.deleteByName("invalid-template"), "Must throw TemplateNotFoundException");
    }
}
