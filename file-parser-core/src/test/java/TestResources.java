import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public final class TestResources {

    private TestResources() {
    }

    public static Path path(String name) {
        try {
            return Path.of(Objects.requireNonNull(
                    TestResources.class.getClassLoader().getResource(name),
                    "Test resource not found on classpath: " + name
            ).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
