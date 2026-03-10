package webpages.tangerine;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.PlaywrightPageObject;

/**
 * Tangerine sign-up page (Playwright).
 */
public class TangerineSignupPagePlaywright extends PlaywrightPageObject {
    protected final static Logger logger = LogManager.getLogger(TangerineSignupPagePlaywright.class.getName());

    public TangerineSignupPagePlaywright(Page page) {
        super(page);
    }
}

