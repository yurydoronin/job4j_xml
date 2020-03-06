package ru.job4j.magnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Class Config.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.02.2020
 */
public class Config {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private final Properties prop = new Properties();

    public Config() {
        this.init();
    }

    public void init() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("sqlite.properties")) {
            prop.load(Objects.requireNonNull(in));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public String get(String key) {
        return this.prop.getProperty(key);
    }
}
