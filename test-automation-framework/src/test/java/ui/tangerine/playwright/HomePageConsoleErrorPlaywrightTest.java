package ui.tangerine.playwright;

import com.microsoft.playwright.*;
import config.PropertiesFileReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class HomePageConsoleErrorPlaywrightTest {
    static Playwright playwright;
    static Browser browser;

    List<String> consoleErrors = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterClass(alwaysRun = true)
    public static void teardown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @Test
    public void shouldHaveNoConsoleErrors() {
        consoleErrors.clear();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.onConsoleMessage(msg -> {
            if ("error".equals(msg.type())) {
                consoleErrors.add(msg.text());
            }
        });

        try {
            page.navigate(PropertiesFileReader.getTangerineURL());
            page.waitForTimeout(5000);
            assertTrue(!consoleErrors.isEmpty(),
                    "Console errors detected: " + consoleErrors);
        } finally {
            context.close();
        }
    }
}
