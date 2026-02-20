package testcases.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class WebTestCase extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(WebTestCase.class.getName());

    protected static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";

    // Initializes a mobile web session (Chrome on Android) via Appium.
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 2);

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
