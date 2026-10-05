package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.StringUtils;

import java.io.InputStream;

public final class ResourceLoader {
    private static final Logger logger = LogManager.getLogger(ResourceLoader.class);
    private static final String ERR_EMPTY_FILENAME = "Filename must not be null or empty.";
    private static final String ERR_RESOURCE_NOT_FOUND = "Resource not found on classpath: ";

    private ResourceLoader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static InputStream openFileAsInputStream(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            logger.error(ERR_EMPTY_FILENAME);
            throw new IllegalArgumentException(ERR_EMPTY_FILENAME);
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            String message = ERR_RESOURCE_NOT_FOUND + fileName;
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        return inputStream;
    }
}
