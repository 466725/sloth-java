package webpages;

import com.microsoft.playwright.Page;
import webpages.selfhealing.HealingHints;
import webpages.selfhealing.SelfHealingLocator;

/**
 * Base type for Playwright page objects.
 */
public abstract class PlaywrightPageObject {
    protected final Page page;

    protected PlaywrightPageObject(Page page) {
        this.page = page;
    }

    protected SelfHealingLocator locator(String selector) {
        return SelfHealingLocator.of(page, selector);
    }

    protected SelfHealingLocator locator(String selector, HealingHints hints) {
        return SelfHealingLocator.of(page, selector).withHints(hints);
    }

    public String title() {
        return page.title();
    }
}
