package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
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

    // Returns Amazon base URL.
    public static String getAmazonURL() {
        return getRequired("AMAZON_URL");
    }

    // Returns Tangerine base URL.
    public static String getTangerineURL() {
        return getRequired("TANGERINE_URL");
    }

    // Returns barcode service base URL.
    public static String getBarcodeBaseURL() {
        return getRequired("BARCODE_BASE_URL");
    }

    // Returns CONNECT service URL.
    public static String getCONNECT_URL() {
        return getRequired("CONNECT_URL");
    }

    // Returns browser name.
    public static String getBrowser() {
        return getRequired("BROWSER");
    }

    // Returns global timeout in seconds.
    public static int getTimeout() {
        return Integer.parseInt(getRequired("GLOBAL_TIMEOUT"));
    }

    // Returns max retry count.
    public static int getMaxRetryCount() {
        return Integer.parseInt(getRequired("MAX_RETRY_COUNT"));
    }
    
    // Returns explicit wait timeout.
    public static Duration getExplicitWaitTime() {
    	int seconds = Integer.parseInt(getRequired("EXPLICIT_WAIT_TIME"));
		return java.time.Duration.ofSeconds(seconds);
    }
    
    // Returns explicit wait timeout in seconds as int.
    public static int getExplicitWaitTimeInt() {
		return Integer.parseInt(getRequired("EXPLICIT_WAIT_TIME"));
    }
    
    // Returns implicit wait timeout.
    public static Duration getImplicitWaitTime() {
		int seconds = Integer.parseInt(getRequired("IMPLICIT_WAIT_TIME"));
		return java.time.Duration.ofSeconds(seconds);
    }
    
    // Returns page load timeout.
    public static Duration getPageLoadTimeout() {
    	int seconds = Integer.parseInt(getRequired("PAGE_LOAD_TIMEOUT"));
		return java.time.Duration.ofSeconds(seconds);
    }
    
    // Returns page render timeout in seconds.
    public static int getPageRenderTimeout() {
		return Integer.parseInt(getRequired("PAGE_RENDER_TIMEOUT"));
    }
    
    public static void main(String[] args) {
        System.out.println(getAmazonURL());
        System.out.println(getTangerineURL());
        System.out.println(getBarcodeBaseURL());
        System.out.println(getCONNECT_URL());
        System.out.println(getBrowser());
        System.out.println(getTimeout());
        System.out.println(getMaxRetryCount());
        System.out.println(getExplicitWaitTime());
        System.out.println(getExplicitWaitTimeInt());
        System.out.println(getImplicitWaitTime());
        System.out.println(getPageLoadTimeout());
        System.out.println(getPageRenderTimeout());
        System.out.println("Main method executed successfully");
    }
}
