package testcases.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class AppTestCase extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(AppTestCase.class.getName());

    // Initializes a native Android app session through Appium.
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        if (driver == null) {
            driver = newDriver();
            logger.info("Android app session initialized.");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitDriver();
    }

    // Builds an Android app driver session from environment-driven defaults.
    protected AndroidDriver newDriver() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android Emulator");
        String appPackage = getEnvOrDefault("ANDROID_APP_PACKAGE", "com.example.android");
        String appActivity = getEnvOrDefault("ANDROID_APP_ACTIVITY", ".MainActivity");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 10);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName(deviceName)
                .setAutomationName("UiAutomator2")
                .setAppPackage(appPackage)
                .setAppActivity(appActivity);

        AndroidDriver androidDriver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android app session capabilities applied. appPackage=" + appPackage + ", appActivity=" + appActivity);
        return androidDriver;
    }
}
