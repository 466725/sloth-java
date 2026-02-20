package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import testcases.TestCase;

import java.net.URL;
import java.time.Duration;

/**
 * Shared mobile test base for Android/Appium session creation helpers.
 */
public abstract class MobileTestCase extends TestCase {
    protected static final Logger logger = LogManager.getLogger(MobileTestCase.class.getName());
    protected static final String DEFAULT_APPIUM_URL = "http://127.0.0.1:4723";

    protected AndroidDriver driver;

    protected AndroidDriver startAndroidSession(UiAutomator2Options options, int implicitWaitSeconds) throws Exception {
        String appiumServerUrl = getEnvOrDefault("APPIUM_SERVER_URL", DEFAULT_APPIUM_URL);
        AndroidDriver androidDriver = new AndroidDriver(new URL(appiumServerUrl), options);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
        logger.info("Android session started. appiumUrl=" + appiumServerUrl + ", deviceName=" + options.getDeviceName());
        return androidDriver;
    }

    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    protected static int getIntEnvOrDefault(String key, int defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            logger.warn("Invalid integer for env '" + key + "': " + value + ". Using default: " + defaultValue);
            return defaultValue;
        }
    }
}
