package webpages.playwright.tangerine;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import selfhealing.SelfHealingLocator;
import webpages.playwright.PlaywrightBasePage;

/**
 * Tangerine sign-in page (Playwright).
 */
public class SigningPage extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(SigningPage.class.getName());

    private final SelfHealingLocator signupButton;

    public SigningPage(Page page) {
        super(page);
        this.signupButton = locator("#menu_signup");
    }

    public SignupPage gotoSignupPage() {
        logger.info("Navigating to Signup page (Playwright)");
        signupButton.click();
        return new SignupPage(page);
    }
}
