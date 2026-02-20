package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestAndroidDeskClockLaunch extends MobileTestCase {
    private static final Logger logger = LogManager.getLogger(TestAndroidDeskClockLaunch.class.getName());
    private static final List<String> DEFAULT_DESKCLOCK_PACKAGES = Arrays.asList(
            "com.google.android.deskclock",
            "com.android.deskclock"
    );

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        String deviceName = getEnvOrDefault("ANDROID_DEVICE_NAME", "Android");
        int implicitWaitSeconds = getIntEnvOrDefault("ANDROID_IMPLICIT_WAIT_SECONDS", 5);

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName);

        driver = startAndroidSession(options, implicitWaitSeconds);
        logger.info("Android native session initialized for Desk Clock launch probe.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitDriver();
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.UI_MOBILE_APP, TestGroups.INTEGRATION})
    public void verifyDeskClockCanBeLaunched() {
        Assert.assertTrue(driver instanceof AndroidDriver, "Expected AndroidDriver session.");
        AndroidDriver androidDriver = (AndroidDriver) driver;
        List<String> deskClockPackages = getDeskClockPackages();

        List<String> launchErrors = new ArrayList<>();
        boolean launched = false;

        for (String appPackage : deskClockPackages) {
            try {
                androidDriver.activateApp(appPackage);
                String currentPackage = androidDriver.getCurrentPackage();
                if (appPackage.equals(currentPackage)) {
                    launched = true;
                    logger.info("Desk Clock app launched successfully. package=" + appPackage);
                    break;
                }
                launchErrors.add("Activated " + appPackage + " but current package is " + currentPackage);
            } catch (Exception exception) {
                launchErrors.add(appPackage + ": " + exception.getMessage());
            }
        }

        Assert.assertTrue(
                launched,
                "Failed to launch Android Desk Clock. Tried packages: " + deskClockPackages + ". Details: " + launchErrors
        );
    }

    private List<String> getDeskClockPackages() {
        String configuredPackages = getEnvOrDefault("ANDROID_DESKCLOCK_PACKAGES", "");
        if (configuredPackages.isBlank()) {
            return DEFAULT_DESKCLOCK_PACKAGES;
        }

        List<String> parsed = Arrays.stream(configuredPackages.split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());

        if (parsed.isEmpty()) {
            return DEFAULT_DESKCLOCK_PACKAGES;
        }
        return parsed;
    }
}
