package webpages.tangerine;

import com.microsoft.playwright.Page;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightPageObject;
import webpages.selfhealing.SelfHealingLocator;

/**
 * Tangerine sign-in page (Playwright).
 */
public class TangerineSigninPagePlaywright extends PlaywrightPageObject {
    protected final static Logger logger = LogManager.getLogger(TangerineSigninPagePlaywright.class.getName());

    private final SelfHealingLocator signupButton;

    public TangerineSigninPagePlaywright(Page page) {
        super(page);
        this.signupButton = locator("#menu_signup");
    }

    public TangerineSignupPagePlaywright gotoSignupPage() {
        logger.info("Navigating to Signup page (Playwright)");
        signupButton.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        signupButton.click();
        return new TangerineSignupPagePlaywright(page);
    }
}
