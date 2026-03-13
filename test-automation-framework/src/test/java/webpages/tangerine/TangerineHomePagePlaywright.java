package webpages.tangerine;

import com.microsoft.playwright.Page;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightPageObject;
import selfhealing.SelfHealingLocator;

/**
 * Tangerine home page (Playwright).
 */
public class TangerineHomePagePlaywright extends PlaywrightPageObject {
    protected final static Logger logger = LogManager.getLogger(TangerineHomePagePlaywright.class.getName());

    private final SelfHealingLocator signinButton;

    public TangerineHomePagePlaywright(Page page) {
        super(page);
        this.signinButton = locator("#login");
    }

    public TangerineSigninPagePlaywright gotoSigninPage() {
        logger.info("Navigating to Signin page (Playwright)");
        signinButton.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        signinButton.click();
        return new TangerineSigninPagePlaywright(page);
    }
}
