package testcases.tangerine;

import com.microsoft.playwright.*;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class TangerineHomePageConsoleErrorPlaywrightTest {
    static Playwright playwright;
    static Browser browser;

    List<String> consoleErrors = new ArrayList<>();

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void teardown() {
        browser.close();
        playwright.close();
    }

    @Test
    void shouldHaveNoConsoleErrors() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.onConsoleMessage(msg -> {
            if ("error".equals(msg.type())) {
                consoleErrors.add(msg.text());
            }
        });

        page.navigate("https://www.tangerine.ca/en/personal");
        page.waitForTimeout(5000);
        assertTrue(consoleErrors.isEmpty(),
                "Console errors detected: " + consoleErrors);
    }
}
