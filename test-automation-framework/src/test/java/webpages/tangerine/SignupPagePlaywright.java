package webpages.tangerine;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightBasePage;

/**
 * Tangerine sign-up page (Playwright).
 */
public class SignupPagePlaywright extends PlaywrightBasePage {
    protected final static Logger logger = LogManager.getLogger(SignupPagePlaywright.class.getName());

    public SignupPagePlaywright(Page page) {
        super(page);
    }
}

