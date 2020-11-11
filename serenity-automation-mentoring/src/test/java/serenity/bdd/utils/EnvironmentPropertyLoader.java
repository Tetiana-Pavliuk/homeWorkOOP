package serenity.bdd.utils;

import io.cucumber.core.logging.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvironmentPropertyLoader {
    private static Logger log = (Logger) LoggerFactory.getLogger(EnvironmentPropertyLoader.class);

    private EnvironmentPropertyLoader() {
        String propertyName = System.getProperty("environment.config");
        try {
            property.load(new FileInputStream(propertyName));
            log.log(Level.INFO, "Used environment: {0}", propertyName.substring(0, 4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    private static EnvironmentPropertyLoader instance = new EnvironmentPropertyLoader();

    public static EnvironmentPropertyLoader getInstance() {
        return instance;
    }

    private static final Properties property = new Properties();

    public String getProperty(final String propertyName) {
        return property.getProperty(propertyName);
    }
}
