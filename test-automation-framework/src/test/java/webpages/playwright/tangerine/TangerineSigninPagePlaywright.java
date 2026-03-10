package webpages.playwright.tangerine;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.playwright.PlaywrightPageObject;

/**
 * Tangerine sign-in page (Playwright).
 */
public class TangerineSigninPagePlaywright extends PlaywrightPageObject {
    protected final static Logger logger = LogManager.getLogger(TangerineSigninPagePlaywright.class.getName());

    private final Locator signupButton;

    public TangerineSigninPagePlaywright(Page page) {
        super(page);
        this.signupButton = page.locator("#menu_signup");
    }

    public TangerineSignupPagePlaywright gotoSignupPage() {
        logger.info("Navigating to Signup page (Playwright)");
        signupButton.waitFor(new Locator.WaitForOptions()
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        signupButton.click();
        return new TangerineSignupPagePlaywright(page);
    }
}

