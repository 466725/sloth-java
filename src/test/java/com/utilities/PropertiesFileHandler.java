package com.utilities;

import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileHandler {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesFileHandler.class);

    private static class Holder {
        private static final Properties INSTANCE = loadProperties();

        private static Properties loadProperties() {
            Properties props = new Properties();
            try (InputStream is = FileHandler.openFileAsInputStream(Constants.PROPERTY_FILE)) {
                LOGGER.info("Initializing property file: " + Constants.PROPERTY_FILE);
                props.load(is);
                return props;
            } catch (Exception e) {
                LOGGER.error("Failed to load property file: " + Constants.PROPERTY_FILE, e);
                throw new RuntimeException("Configuration failure", e);
            }
        }
    }

    public static Properties getPropertyFile() {
        return Holder.INSTANCE;
    }

    public static String getProperty(String key) {
        return Holder.INSTANCE.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("webdriver.chrome.driver"));
    }
}
