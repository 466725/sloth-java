package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class FileHandler {
    private static final Logger logger = LogManager.getLogger(FileHandler.class);
    private static final String ERR_EMPTY_FILENAME = "Filename is empty!";

    public static InputStream openFileAsInputStream(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            logger.error(ERR_EMPTY_FILENAME);
            throw new IllegalArgumentException(ERR_EMPTY_FILENAME);
        }
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
