package webpages.playwright;

import com.microsoft.playwright.Page;

/**
 * Base type for Playwright page objects.
 */
public abstract class PlaywrightPageObject {
    protected final Page page;

    protected PlaywrightPageObject(Page page) {
        this.page = page;
    }

    public String title() {
        return page.title();
    }
}

