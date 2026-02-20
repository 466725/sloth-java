package testcases.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebTestCase extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(WebTestCase.class.getName());

    protected static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";

    // Initializes a mobile web session (Chrome on Android) via Appium.
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String runMode = getEnvOrDefault("MOBILE_WEB_RUN_MODE", "appium").trim().toLowerCase();
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 2);

        if ("selenium".equals(runMode)) {
            String seleniumRemoteUrl = getEnvOrDefault("SELENIUM_REMOTE_URL", "http://127.0.0.1:4444/wd/hub");
            String emulatedDeviceName = getEnvOrDefault("MOBILE_EMULATION_DEVICE", "Pixel 7");
            boolean headless = Boolean.parseBoolean(getEnvOrDefault("HEADLESS", "true"));

            ChromeOptions chromeOptions = new ChromeOptions();
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", emulatedDeviceName);
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            if (headless) {
                chromeOptions.addArguments("--headless=new");
            }

            driver = new RemoteWebDriver(new URL(seleniumRemoteUrl), chromeOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
            logger.info("Selenium mobile-emulated web session initialized. seleniumUrl=" + seleniumRemoteUrl
                    + ", emulatedDevice=" + emulatedDeviceName);
            return;
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);
        options.setCapability("browserName", "Chrome");

        driver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android mobile web session initialized.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitDriver();
    }
}
