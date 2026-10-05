package webpages.playwright.tangerine;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import selfhealing.SelfHealingLocator;
import webpages.playwright.PlaywrightBasePage;


/**
 * Tangerine home page (Playwright).
 */
public class HomePage extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());

    private final SelfHealingLocator signingButton;

    public HomePage(Page page) {
        super(page);
        this.signingButton = locator("#login");
    }

    public SigningPage gotoSigningPage() {
        logger.info("Navigating to Signing page (Playwright)");
        signingButton.click();
        return new SigningPage(page);
    }
}
