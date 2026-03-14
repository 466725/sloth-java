package webpages.tangerine;

import com.microsoft.playwright.Page;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightBasePage;
import selfhealing.SelfHealingLocator;

/**
 * Tangerine home page (Playwright).
 */
public class HomePagePlaywright extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(HomePagePlaywright.class.getName());

    private final SelfHealingLocator signinButton;

    public HomePagePlaywright(Page page) {
        super(page);
        this.signinButton = locator("#login");
    }

    public SigninPagePlaywright gotoSigninPage() {
        logger.info("Navigating to Signin page (Playwright)");
        signinButton.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        signinButton.click();
        return new SigninPagePlaywright(page);
    }
}
