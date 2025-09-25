package bot.movie.mate.utils;

import bot.movie.mate.exception.ConfigLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    private ConfigUtils() {}

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new ConfigLoadException("Not found config.properties in resources");
            }

            properties.load(input);

        } catch (IOException ex) {
            throw new ConfigLoadException("Error loading config.properties", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}