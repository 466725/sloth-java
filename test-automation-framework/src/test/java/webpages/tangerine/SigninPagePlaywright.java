package webpages.tangerine;

import com.microsoft.playwright.Page;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightBasePage;
import selfhealing.SelfHealingLocator;

/**
 * Tangerine sign-in page (Playwright).
 */
public class SigninPagePlaywright extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(SigninPagePlaywright.class.getName());

    private final SelfHealingLocator signupButton;

    public SigninPagePlaywright(Page page) {
        super(page);
        this.signupButton = locator("#menu_signup");
    }

    public SignupPagePlaywright gotoSignupPage() {
        logger.info("Navigating to Signup page (Playwright)");
        signupButton.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        signupButton.click();
        return new SignupPagePlaywright(page);
    }
}
