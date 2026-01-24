package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesFileReader.class);

    private static class Holder {
        private static final Properties INSTANCE = loadProperties();

        private static Properties loadProperties() {
            Properties props = new Properties();
            try (InputStream is = FileHandler.openFileAsInputStream(Constants.CONFIG_FILE)) {
                LOGGER.info("Initializing property file: " + Constants.CONFIG_FILE);
                props.load(is);
                return props;
            } catch (Exception e) {
                LOGGER.error("Failed to load property file: " + Constants.CONFIG_FILE, e);
                throw new RuntimeException("Configuration failure", e);
            }
        }
    }

    public static Properties getPropertyFile() {
        return Holder.INSTANCE;
    }

    // Get URL
    public static String getURL() {
        return Holder.INSTANCE.getProperty("URL");
    }

    // Get Browser
    public static String getBrowser() {
        return Holder.INSTANCE.getProperty("BROWSER");
    }

    // Get timeout
    public static int getTimeout() {
        return Integer.parseInt(Holder.INSTANCE.getProperty("TIMEOUT"));
    }

    public static String getProperty(String key) {
        return Holder.INSTANCE.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("webdriver.chrome.driver"));
    }
}
