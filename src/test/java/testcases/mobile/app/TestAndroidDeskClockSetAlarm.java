package testcases.mobile.app;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcases.TestGroups;
import testcases.mobile.MobileAppTestCase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAndroidDeskClockSetAlarm extends MobileAppTestCase {
    private static final Logger logger = LogManager.getLogger(TestAndroidDeskClockSetAlarm.class.getName());
    private static final int TARGET_ALARM_HOUR = 7;
    private static final int TARGET_ALARM_MINUTE = 0;
    private static final String TARGET_ALARM_LABEL = "Sloth 07:00";

    @Test(priority = 7, groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
    public void verifyClockAlarmCanBeSet() {
        Assert.assertTrue(driver instanceof AndroidDriver, "Expected AndroidDriver session.");
        AndroidDriver androidDriver = (AndroidDriver) driver;
        List<String> deskClockPackages = getDeskClockPackages();
        List<String> errors = new ArrayList<>();

        boolean alarmSet = false;

        for (String appPackage : deskClockPackages) {
            logger.info("mobile.app.test.deskclock.alarm_set.attempt | package=" + appPackage + " | targetTime=07:00");
            try {
                androidDriver.activateApp(appPackage);
                navigateToAlarmTab(androidDriver, appPackage);
                createAlarmViaIntent(androidDriver, TARGET_ALARM_HOUR, TARGET_ALARM_MINUTE, TARGET_ALARM_LABEL);
                navigateToAlarmTab(androidDriver, appPackage);

                if (waitForAlarmEntry(androidDriver, TARGET_ALARM_LABEL)) {
                    alarmSet = true;
                    logger.info("mobile.app.test.deskclock.alarm_set.success | package=" + appPackage + " | label=" + TARGET_ALARM_LABEL);
                    break;
                }
                errors.add("Alarm entry not visible after set-alarm intent for package=" + appPackage);
            } catch (Exception exception) {
                errors.add("package=" + appPackage + " | reason=" + exception.getMessage());
                logger.info("mobile.app.test.deskclock.alarm_set.error | package=" + appPackage + " | reason=" + exception.getMessage());
            }
        }

        Assert.assertTrue(
                alarmSet,
                "Failed to set 7:00 AM alarm in Android Desk Clock. Tried packages: " + deskClockPackages + ". Details: " + errors
        );
    }

    private void navigateToAlarmTab(AndroidDriver androidDriver, String appPackage) {
        List<By> alarmTabLocators = List.of(
                By.id(appPackage + ":id/tab_menu_alarm"),
                By.id(appPackage + ":id/alarm_tab"),
                By.xpath("//*[@text='Alarm' or @text='ALARM']"),
                By.xpath("//*[contains(@content-desc,'Alarm')]")
        );

        for (By locator : alarmTabLocators) {
            List<WebElement> elements = androidDriver.findElements(locator);
            if (!elements.isEmpty()) {
                elements.get(0).click();
                logger.info("mobile.app.test.deskclock.alarm_tab.opened | locator=" + locator);
                return;
            }
        }

        logger.info("mobile.app.test.deskclock.alarm_tab.opened | locator=not_found_assuming_default_tab");
    }

    private void createAlarmViaIntent(AndroidDriver androidDriver, int hour, int minute, String label) {
        Map<String, Object> args = new HashMap<>();
        args.put("command", "am");
        args.put("args", List.of(
                "start",
                "-a", "android.intent.action.SET_ALARM",
                "--ei", "android.intent.extra.alarm.HOUR", String.valueOf(hour),
                "--ei", "android.intent.extra.alarm.MINUTES", String.valueOf(minute),
                "--ez", "android.intent.extra.alarm.SKIP_UI", "true",
                "--es", "android.intent.extra.alarm.MESSAGE", label
        ));
        args.put("includeStderr", true);
        args.put("timeout", 10000);

        Object shellResult = androidDriver.executeScript("mobile: shell", args);
        logger.info("mobile.app.test.deskclock.alarm_set.intent_result | result=" + shellResult);
    }

    private boolean waitForAlarmEntry(AndroidDriver androidDriver, String label) {
        int timeoutSeconds = getIntEnvOrDefault("ANDROID_ALARM_SET_TIMEOUT_SECONDS", 20);
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(timeoutSeconds));

        By byLabel = By.xpath("//*[contains(@text,'" + label + "')]");
        By bySeven = By.xpath("//*[contains(@text,'7:00') or contains(@text,'07:00')]");
        By bySevenContentDesc = By.xpath("//*[contains(@content-desc,'7:00') or contains(@content-desc,'07:00')]");

        try {
            return wait.until(driver ->
                    !driver.findElements(byLabel).isEmpty()
                            || !driver.findElements(bySeven).isEmpty()
                            || !driver.findElements(bySevenContentDesc).isEmpty()
            );
        } catch (Exception exception) {
            logger.info("mobile.app.test.deskclock.alarm_set.verify_timeout | reason=" + exception.getMessage());
            return false;
        }
    }
}
