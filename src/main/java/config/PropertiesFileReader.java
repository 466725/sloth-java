package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {
    private final static Logger logger = LogManager.getLogger(PropertiesFileReader.class.getName());
    private static volatile Properties prop;

    // Constructor (optional now, but kept for compatibility)
    public PropertiesFileReader() {
        ensureLoaded();
    }

    private static void ensureLoaded() {
        if (prop != null) return;

        synchronized (PropertiesFileReader.class) {
            if (prop != null) return;

            Properties loaded = new Properties();

            // 1) Prefer classpath resource (recommended)
            try (InputStream is = PropertiesFileReader.class.getClassLoader().getResourceAsStream("init-config.properties")) {
                if (is != null) {
                    loaded.load(is);
                    prop = loaded;
                    return;
                }
            } catch (Exception e) {
                throw new IllegalStateException("Failed to load config from classpath resource: config/init-config.properties", e);
            }

            // 2) Fallback to file path used in Constants (legacy behavior)
            try (FileInputStream fis = new FileInputStream(Constants.CONFIG_FILE)) {
                loaded.load(fis);
                prop = loaded;
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Config file not found or unreadable. Tried classpath 'config/init-config.properties' and file: "
                                + Constants.CONFIG_FILE,
                        e
                );
            }
        }
    }

    private static String getRequired(String key) {
        ensureLoaded();
        String value = prop.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value.trim();
    }

    // Get URL
    public static String getURL() {
        return getRequired("URL");
    }

    // Get Browser
    public static String getBrowser() {
        return getRequired("BROWSER");
    }

    // Get timeout
    public static int getTimeout() {
        ensureLoaded();
        String raw = prop.getProperty("GLOBAL_TIMEOUT");
        if (raw == null || raw.isBlank()) {
            return Constants.EXPLICIT_WAIT_TIME; // sensible default
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("GLOBAL_TIMEOUT must be an integer, but was: " + raw, e);
        }
    }

    static void main() {
        System.out.println(getURL());
        System.out.println(getBrowser());
        System.out.println(getTimeout());
    }
}
