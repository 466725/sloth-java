package ui.tangerine.playwright;

import config.PropertiesFileReader;
import core.PlaywrightGuiTestCase;
import core.TestGroups;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class HomePageConsoleErrorPlaywrightTest extends PlaywrightGuiTestCase {
    final static Logger logger = LogManager.getLogger(HomePageConsoleErrorPlaywrightTest.class.getName());

    // Verifies console error capture on the Tangerine home page.
    @Test(groups = {TestGroups.PLAYWRIGHT, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldHaveNoConsoleErrors() {
        List<String> consoleErrors = new ArrayList<>();
        page.onConsoleMessage(msg -> {
            if ("error".equals(msg.type())) {
                consoleErrors.add(msg.text());
            }
        });

        logger.info("ui.web.tangerine.home.console_errors.start");
        page.navigate(PropertiesFileReader.getTangerineURL());
        page.waitForTimeout(5000);
        logger.info("ui.web.tangerine.home.console_errors.state | count=" + consoleErrors.size());
        assertTrue(consoleErrors.isEmpty(),
                "Console errors detected: " + consoleErrors);
    }
}