package ui.app;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.TestGroups;
import core.mobile.MobileAppTestCase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AndroidDeskClockSetAlarmTest extends MobileAppTestCase {
    private static final Logger logger = LogManager.getLogger(AndroidDeskClockSetAlarmTest.class.getName());
    private static final int TARGET_ALARM_HOUR = 7;
    private static final int TARGET_ALARM_MINUTE = 0;
    private static final String TARGET_ALARM_LABEL = "Sloth0700";
    private static final int DESKCLOCK_READY_TIMEOUT_SECONDS = 20;

    @Test(priority = 7, groups = {TestGroups.INTEGRATION, TestGroups.UI_MOBILE_APP})
    public void shouldSetDeskClockAlarm() {
        Assert.assertTrue(driver instanceof AndroidDriver, "Expected AndroidDriver session.");
        AndroidDriver androidDriver = (AndroidDriver) driver;
        List<String> deskClockPackages = getDeskClockPackages();
        List<String> errors = new ArrayList<>();

        boolean alarmSet = false;

        for (String appPackage : deskClockPackages) {
            logger.info("mobile.app.test.deskclock.alarm_set.attempt | package=" + appPackage + " | targetTime=07:00");
            try {
                androidDriver.activateApp(appPackage);
                if (!waitForCurrentPackage(androidDriver, appPackage, 15)) {
                    errors.add("Desk Clock package did not come to foreground. expected=" + appPackage + ", actual=" + androidDriver.getCurrentPackage());
                    continue;
                }
                if (!waitForDeskClockReady(androidDriver, appPackage, DESKCLOCK_READY_TIMEOUT_SECONDS)) {
                    errors.add("Desk Clock main UI did not become ready for package=" + appPackage);
                    continue;
                }
                navigateToAlarmTab(androidDriver, appPackage);
                waitForDeskClockReady(androidDriver, appPackage, 8);
                createAlarmViaUi(androidDriver, appPackage, TARGET_ALARM_HOUR, TARGET_ALARM_MINUTE);

                if (waitForAlarmEntry(androidDriver, TARGET_ALARM_LABEL, TARGET_ALARM_HOUR, TARGET_ALARM_MINUTE)) {
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
                By.xpath("//*[contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'alarm')]"),
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

    private void createAlarmViaUi(AndroidDriver androidDriver, String appPackage, int hour, int minute) {
        List<By> addAlarmLocators = List.of(
                By.id(appPackage + ":id/fab"),
                By.xpath("//*[contains(@content-desc,'Add alarm')]"),
                By.xpath("//*[contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'alarm') and @clickable='true']")
        );

        if (!clickFirstPresent(androidDriver, addAlarmLocators, "add_alarm_button")) {
            // Fallback: many DeskClock builds keep FAB near bottom-center.
            Dimension windowSize = androidDriver.manage().window().getSize();
            int tapX = windowSize.getWidth() / 2;
            int tapY = (int) (windowSize.getHeight() * 0.86);
            androidDriver.executeScript("mobile: clickGesture", Map.of("x", tapX, "y", tapY));
            logger.info("mobile.app.test.deskclock.alarm_set.ui_click_fallback | step=add_alarm_button | x=" + tapX + " | y=" + tapY);
            // One more direct lookup attempt after fallback tap.
            clickFirstPresent(androidDriver, addAlarmLocators, "add_alarm_button_retry");
        }

        clickFirstPresentRequired(androidDriver, List.of(By.id("android:id/hours")), "time_picker_hours");
        clickFirstPresentRequired(androidDriver, List.of(By.xpath("//*[contains(@content-desc,'" + hour + "')]")), "hour_value");

        clickFirstPresentRequired(androidDriver, List.of(By.id("android:id/minutes")), "time_picker_minutes");
        clickFirstPresentRequired(androidDriver, List.of(
                By.xpath("//*[contains(@content-desc,'0')]"),
                By.xpath("//*[contains(@content-desc,'00')]")
        ), "minute_value");

        clickIfPresent(androidDriver, List.of(By.id("android:id/am_label")));
        clickFirstPresentRequired(androidDriver, List.of(By.id("android:id/button1")), "time_picker_ok");
        logger.info("mobile.app.test.deskclock.alarm_set.ui_applied | hour=" + hour + " | minute=" + minute + " | am=true");
    }

    private boolean waitForAlarmEntry(AndroidDriver androidDriver, String label, int hour, int minute) {
        int timeoutSeconds = getIntEnvOrDefault("ANDROID_ALARM_SET_TIMEOUT_SECONDS", 20);
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(timeoutSeconds));

        By byLabel = By.xpath("//*[contains(@text,'" + label + "')]");
        String time12 = hour + ":" + String.format("%02d", minute);
        String time24 = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        By byTimeText = By.xpath("//*[contains(@text,'" + time12 + "') or contains(@text,'" + time24 + "')]");
        By byTimeContentDesc = By.xpath("//*[contains(@content-desc,'" + time12 + "') or contains(@content-desc,'" + time24 + "')]");

        try {
            return wait.until(driver ->
                    !driver.findElements(byLabel).isEmpty()
                            || !driver.findElements(byTimeText).isEmpty()
                            || !driver.findElements(byTimeContentDesc).isEmpty()
            );
        } catch (Exception exception) {
            logger.info("mobile.app.test.deskclock.alarm_set.verify_timeout | reason=" + exception.getMessage());
            return false;
        }
    }

    private boolean clickFirstPresent(AndroidDriver androidDriver, List<By> locators, String step) {
        for (By locator : locators) {
            List<WebElement> elements = androidDriver.findElements(locator);
            if (!elements.isEmpty()) {
                elements.get(0).click();
                logger.info("mobile.app.test.deskclock.alarm_set.ui_click | step=" + step + " | locator=" + locator);
                return true;
            }
        }
        return false;
    }

    private void clickFirstPresentRequired(AndroidDriver androidDriver, List<By> locators, String step) {
        if (!clickFirstPresent(androidDriver, locators, step)) {
            throw new IllegalStateException("Unable to locate UI element for step: " + step + ", locators=" + locators);
        }
    }

    private void clickIfPresent(AndroidDriver androidDriver, List<By> locators) {
        for (By locator : locators) {
            List<WebElement> elements = androidDriver.findElements(locator);
            if (!elements.isEmpty()) {
                elements.get(0).click();
                logger.info("mobile.app.test.deskclock.alarm_set.ui_click_optional | locator=" + locator);
                return;
            }
        }
    }

    private boolean waitForCurrentPackage(AndroidDriver androidDriver, String expectedPackage, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(timeoutSeconds));
        try {
            return wait.until(driver -> expectedPackage.equals(androidDriver.getCurrentPackage()));
        } catch (Exception exception) {
            logger.info("mobile.app.test.deskclock.foreground.wait_timeout | expected=" + expectedPackage + " | actual=" + androidDriver.getCurrentPackage());
            return false;
        }
    }

    private boolean waitForDeskClockReady(AndroidDriver androidDriver, String appPackage, int timeoutSeconds) {
        List<By> readyLocators = List.of(
                By.id(appPackage + ":id/alarm_recycler_view"),
                By.id(appPackage + ":id/fab"),
                By.xpath("//*[contains(@content-desc,'Alarm')]"),
                By.xpath("//*[@text='Alarm' or @text='ALARM']")
        );
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(timeoutSeconds));
        try {
            return wait.until(driver -> hasAnyLocator(androidDriver, readyLocators));
        } catch (Exception exception) {
            logger.info("mobile.app.test.deskclock.ready.wait_timeout | package=" + appPackage + " | reason=" + exception.getMessage());
            return false;
        }
    }

    private boolean hasAnyLocator(AndroidDriver androidDriver, List<By> locators) {
        for (By locator : locators) {
            if (!androidDriver.findElements(locator).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
