package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);
    private static final String ERR_EMPTY_FILENAME = "Filename is empty!";

    public static InputStream openFileAsInputStream(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            LOGGER.error(ERR_EMPTY_FILENAME);
            throw new IllegalArgumentException(ERR_EMPTY_FILENAME);
        }
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}
