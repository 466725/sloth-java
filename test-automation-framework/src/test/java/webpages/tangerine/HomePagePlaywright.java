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

    private final SelfHealingLocator signingButton;

    public HomePagePlaywright(Page page) {
        super(page);
        this.signingButton = locator("#login");
    }

    public SigningPagePlaywright gotoSigningPage() {
        logger.info("Navigating to Signing page (Playwright)");
        signingButton.click();
        return new SigningPagePlaywright(page);
    }
}
