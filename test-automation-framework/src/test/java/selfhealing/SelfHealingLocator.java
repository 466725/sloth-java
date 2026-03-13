package selfhealing;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * A minimal wrapper around Playwright {@link Locator} that retries actions with a healed selector
 * if the original locator is broken.
 */
public final class SelfHealingLocator {
    private static final Logger logger = LogManager.getLogger(SelfHealingLocator.class.getName());

    private static final ConcurrentMap<String, String> HEALED_CACHE = new ConcurrentHashMap<>();

    private final Page page;
    private final String originalSelector;
    private final HealingHints hints;

    private volatile String healedSelector;

    private SelfHealingLocator(Page page, String originalSelector, HealingHints hints) {
        this.page = Objects.requireNonNull(page, "page");
        this.originalSelector = Objects.requireNonNull(originalSelector, "originalSelector").trim();
        this.hints = hints == null ? HealingHints.none() : hints;
        this.healedSelector = HEALED_CACHE.get(cacheKey());
    }

    public static SelfHealingLocator of(Page page, String selector) {
        return new SelfHealingLocator(page, selector, HealingHints.none());
    }

    public SelfHealingLocator withHints(HealingHints hints) {
        HealingHints merged = (hints == null ? HealingHints.none() : hints).merge(this.hints);
        return new SelfHealingLocator(page, originalSelector, merged);
    }

    public Locator locator() {
        return page.locator(currentSelector());
    }

    public void waitFor(Locator.WaitForOptions options) {
        runWithHealing(l -> {
            l.waitFor(options);
            return null;
        });
    }

    public void click() {
        runWithHealing(l -> {
            l.click();
            return null;
        });
    }

    public void click(Locator.ClickOptions options) {
        runWithHealing(l -> {
            l.click(options);
            return null;
        });
    }

    public void fill(String value) {
        runWithHealing(l -> {
            l.fill(value);
            return null;
        });
    }

    public String textContent() {
        return runWithHealing(Locator::textContent);
    }

    private <T> T runWithHealing(Function<Locator, T> action) {
        String selectorUsed = currentSelector();
        try {
            return action.apply(page.locator(selectorUsed));
        } catch (PlaywrightException e) {
            if (!SelfHealingConfig.isEnabled()) {
                throw e;
            }
            if (!looksLikeMissingElement(e)) {
                throw e;
            }

            HealedSelector healed = attemptHeal();
            if (healed == null || healed.selector() == null || healed.selector().isBlank()) {
                throw e;
            }
            if (healed.selector().trim().equals(selectorUsed)) {
                // Avoid infinite loop if the engine reproduces the same selector.
                throw e;
            }

            healedSelector = healed.selector();
            HEALED_CACHE.put(cacheKey(), healedSelector);

            logger.warn("Self-healing applied."
                    + " original=" + originalSelector
                    + " healed=" + healedSelector
                    + " score=" + healed.score()
                    + " matchedTag=" + healed.matchedTag()
                    + " matchedId=" + healed.matchedId()
                    + " matchedClasses=" + healed.matchedClasses());

            return action.apply(page.locator(healedSelector));
        }
    }

    private HealedSelector attemptHeal() {
        try {
            String html = page.content();
            return LocatorHealer.healHtml(html, originalSelector, hints);
        } catch (RuntimeException ex) {
            logger.warn("Self-healing failed during DOM parsing/scoring. selector=" + originalSelector, ex);
            return null;
        }
    }

    private String currentSelector() {
        if (healedSelector != null && !healedSelector.isBlank()) {
            return healedSelector;
        }
        return originalSelector;
    }

    private String cacheKey() {
        // Keep it simple: selector-level cache.
        return originalSelector;
    }

    private static boolean looksLikeMissingElement(PlaywrightException e) {
        String msg = e.getMessage();
        if (msg == null) {
            return false;
        }
        String m = msg.toLowerCase();
        return m.contains("timeout") || m.contains("waiting for") || m.contains("no node found") || m.contains("strict mode violation");
    }
}
