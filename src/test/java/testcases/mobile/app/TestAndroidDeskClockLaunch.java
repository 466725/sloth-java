package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileAppTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestAndroidDeskClockLaunch extends MobileAppTestCase {
    private static final Logger logger = LogManager.getLogger(TestAndroidDeskClockLaunch.class.getName());
    private static final List<String> DEFAULT_DESKCLOCK_PACKAGES = Arrays.asList(
            "com.google.android.deskclock",
            "com.android.deskclock"
    );

    @Override
    protected WebDriver createDriverSession() throws Exception {
        if ("false".equalsIgnoreCase(System.getProperty(TestAndroidDeviceConnectivity.ANDROID_CONNECTIVITY_READY_PROPERTY))) {
            throw new SkipException("Skipping Desk Clock launch because Android connectivity probe already failed in this suite run.");
        }
        return super.createDriverSession();
    }

    @Test(groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
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
