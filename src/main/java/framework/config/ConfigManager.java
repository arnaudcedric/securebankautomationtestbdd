package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads and exposes test configuration from a classpath properties file,
 * chosen via the {@code -Denv} system property (defaults to {@code qa}),
 * e.g. {@code -Denv=qa} loads {@code config-qa.properties} from
 * {@code src/test/resources}. Properties are loaded once into a static
 * block and are read-only afterwards via {@link #get(String)}.
 */
public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        String env = System.getProperty("env", "qa");

        String fileName = "config-" + env + ".properties";

        try (InputStream input =
                     ConfigManager.class.getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(
                        "Configuration file not found: " + fileName
                );
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load configuration: " + fileName,
                    e
            );
        }
    }

    private ConfigManager() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
