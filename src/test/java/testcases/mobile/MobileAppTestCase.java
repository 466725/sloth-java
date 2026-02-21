package testcases.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class MobileAppTestCase extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(MobileAppTestCase.class.getName());

    @Override
    protected WebDriver createDriverSession() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 5);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);

        WebDriver appiumDriver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android native app session initialized.");
        return appiumDriver;
    }
}
