package ui.tangerine.cucumber;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.RunConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import webpages.playwright.PlaywrightBasePage;

public class HomePageSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(RunConfig.isHeadless()));
        page = browser.newPage();
    }

    @After
    public void tearDown() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @Given("I open the Tangerine home page")
    public void openHomePage() {
        PlaywrightBasePage.gotoHomePage(page);
    }

    @Then("the page title should contain {string}")
    public void verifyTitle(String expected) {
        Assert.assertTrue(page.title().contains(expected), "Unexpected title: " + page.title());
    }
}
