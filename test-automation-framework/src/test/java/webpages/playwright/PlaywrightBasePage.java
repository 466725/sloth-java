package webpages.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import selfhealing.SelfHealingLocator;
import webpages.playwright.tangerine.HomePage;

/**
 * Base type for Playwright page objects.
 */
public abstract class PlaywrightBasePage {
    private static final Logger logger = LogManager.getLogger(PlaywrightBasePage.class.getName());
    protected final Page page;

    protected PlaywrightBasePage(Page page) {
        this.page = page;
    }

    public static HomePage gotoHomePage(Page page) {
        String url = PropertiesFileReader.getTangerineURL();
        logger.info("playwright.goto | org= Tangerine | url=" + url);
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        return new HomePage(page);
    }

    protected SelfHealingLocator locator(String selector) {
        return SelfHealingLocator.of(page, selector);
    }

    public String title() {
        return page.title();
    }
}
