package testcases.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class MobileWebTestCase extends MobileTestCase {
    private static final String RUN_MODE_SELENIUM = "selenium";
    private static final String RUN_MODE_APPIUM = "appium";
    private static final String DEFAULT_SELENIUM_URL = "http://127.0.0.1:4444/wd/hub";
    private static final String DEFAULT_EMULATED_DEVICE = "Pixel 7";
    protected static final String DEFAULT_AMAZON_SIGNIN_URL = "https://www.amazon.com/ap/signin";
    private static final Logger logger = LogManager.getLogger(MobileWebTestCase.class.getName());

    @Override
    protected WebDriver createDriverSession() throws Exception {
        String requestedRunMode = getEnvOrDefault("MOBILE_WEB_RUN_MODE", RUN_MODE_APPIUM).trim().toLowerCase();
        String runMode = requestedRunMode;
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 2);

        if (!RUN_MODE_SELENIUM.equals(runMode) && !RUN_MODE_APPIUM.equals(runMode)) {
            // Keep this resilient for CI by falling back to Appium when run mode is misspelled.
            logger.warn("mobile.web.run_mode.invalid | requested=" + requestedRunMode + " | fallback=" + RUN_MODE_APPIUM);
            runMode = RUN_MODE_APPIUM;
        }

        logger.info("mobile.web.session.init.start | runMode=" + runMode + " | deviceName=" + deviceName);

        if (RUN_MODE_SELENIUM.equals(runMode)) {
            return createSeleniumMobileSession(implicitWaitSeconds);
        }
        return createAppiumMobileWebSession(deviceName, implicitWaitSeconds);
    }

    protected boolean hasElement(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected boolean isChallengeUrl() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("/ap/signin") || currentUrl.contains("/ap/cvf") || currentUrl.contains("/errors/validateCaptcha") || currentUrl.contains("/ap/challenge");
    }

    private WebDriver createSeleniumMobileSession(int implicitWaitSeconds) throws Exception {
        String seleniumRemoteUrl = getEnvOrDefault("SELENIUM_REMOTE_URL", DEFAULT_SELENIUM_URL);
        String emulatedDeviceName = getEnvOrDefault("MOBILE_EMULATION_DEVICE", DEFAULT_EMULATED_DEVICE);
        boolean headless = Boolean.parseBoolean(getEnvOrDefault("HEADLESS", "true"));

        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", emulatedDeviceName);
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        if (headless) {
            chromeOptions.addArguments("--headless=new");
        }

        WebDriver seleniumDriver = new RemoteWebDriver(new URL(seleniumRemoteUrl), chromeOptions);
        seleniumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
        logger.info("mobile.web.session.init.selenium | seleniumUrl=" + seleniumRemoteUrl + " | emulatedDevice=" + emulatedDeviceName + " | headless=" + headless);
        return seleniumDriver;
    }

    private WebDriver createAppiumMobileWebSession(String deviceName, int implicitWaitSeconds) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options().setPlatformName("Android").setAutomationName("UiAutomator2").setDeviceName(deviceName);
        applyDefaultAndroidTimeoutCapabilities(options);
        options.setCapability("browserName", "Chrome");

        WebDriver appiumDriver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("mobile.web.session.init.appium | browserName=Chrome | deviceName=" + deviceName);
        return appiumDriver;
    }
}
