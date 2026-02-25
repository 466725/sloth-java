package scenarios.ui.amazon.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.ScreenShotHandler;
import webpages.BaseWebPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AmazonSigninHooks {
    private static final Logger logger = LogManager.getLogger(AmazonSigninHooks.class.getName());
    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("cucumber.scenario.start | name=" + scenario.getName() + " | tags=" + scenario.getSourceTagNames());
        BaseWebPage.quitDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        WebDriver driver = BaseWebPage.getCurrentDriver();
        if (scenario.isFailed() && driver != null) {
            String safeScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9._-]", "_");
            String screenshotName = LocalDateTime.now().format(SCREENSHOT_TS) + "_cucumber_" + safeScenarioName;
            String screenshotPath = ScreenShotHandler.captureScreenShot(driver, screenshotName);
            if (screenshotPath != null) {
                logger.warn("cucumber.scenario.screenshot.saved | scenario=" + scenario.getName() + " | path=" + screenshotPath);
            } else {
                logger.warn("cucumber.scenario.screenshot.save_failed | scenario=" + scenario.getName());
            }

            try {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "failure-screenshot");
            } catch (Exception exception) {
                logger.warn("cucumber.scenario.screenshot.attach_failed | scenario=" + scenario.getName() + " | reason=" + exception.getMessage());
            }
        } else if (scenario.isFailed()) {
            logger.warn("cucumber.scenario.screenshot.skip | scenario=" + scenario.getName() + " | reason=driver_null");
        }

        logger.info("cucumber.scenario.end | name=" + scenario.getName() + " | status=" + scenario.getStatus());
        BaseWebPage.quitDriver();
    }
}
