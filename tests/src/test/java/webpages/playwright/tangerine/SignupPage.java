package webpages.playwright.tangerine;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.playwright.PlaywrightBasePage;

/**
 * Tangerine sign-up page (Playwright).
 */
public class SignupPage extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(SignupPage.class.getName());

    public SignupPage(Page page) {
        super(page);
    }
}

