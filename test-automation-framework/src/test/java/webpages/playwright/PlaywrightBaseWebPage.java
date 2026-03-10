package webpages.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webpages.playwright.tangerine.TangerineHomePagePlaywright;

import java.util.Locale;

/**
 * Minimal Playwright counterpart to {@link webpages.BaseWebPage}.
 */
public class PlaywrightBaseWebPage {
    private static final Logger logger = LogManager.getLogger(PlaywrightBaseWebPage.class.getName());
    private static final String ORG_TANGERINE = "tangerine";

    private PlaywrightBaseWebPage() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static TangerineHomePagePlaywright gotoHomePage(Page page, String org) {
        String normalizedOrg = normalize(org, ORG_TANGERINE);
        if (!ORG_TANGERINE.equals(normalizedOrg)) {
            throw new IllegalArgumentException("Unsupported org for Playwright: " + org);
        }

        String url = PropertiesFileReader.getTangerineURL();
        logger.info("playwright.goto | org=" + normalizedOrg + " url=" + url);
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout(PropertiesFileReader.getTimeout() * 1000.0));
        return new TangerineHomePagePlaywright(page);
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}

