package utils;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.latest.network.Network;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Logs HTTP 5xx responses and refreshes the page once per registration.
 * Register once per page (or Selenium tab) before navigating or triggering requests.
 */
public final class ServerErrorMonitor {
    private static final Logger logger = LogManager.getLogger(ServerErrorMonitor.class.getName());

    private ServerErrorMonitor() {
    }

    /**
     * Monitors the current tab of a CDP-capable Chromium driver.
     * The listener lives with the DevTools session; the caller still owns driver cleanup.
     *
     * @throws IllegalArgumentException if the driver does not expose DevTools
     */
    public static void monitor(WebDriver driver) {
        Objects.requireNonNull(driver, "driver");
        if (!(driver instanceof HasDevTools hasDevTools)) {
            throw new IllegalArgumentException(
                    "Server error monitoring requires a CDP-capable Selenium driver implementing HasDevTools.");
        }

        DevTools devTools = hasDevTools.getDevTools();
        devTools.createSessionIfThereIsNotOne(driver.getWindowHandle());
        devTools.send(Objects.requireNonNull(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty())));
        AtomicBoolean refreshRequested = new AtomicBoolean();
        devTools.addListener(Objects.requireNonNull(Network.responseReceived()), event -> {
            var response = event.getResponse();
            handleResponse(response.getStatus(), response.getUrl(), refreshRequested,
                    () -> devTools.send(Objects.requireNonNull(org.openqa.selenium.devtools.latest.page.Page.reload(
                            Optional.empty(), Optional.empty(), Optional.empty()))));
        });
    }

    /**
     * Monitors a Playwright page on any supported browser until the page is closed.
     * Playwright dispatches callbacks while its synchronous API processes events.
     */
    public static void monitor(Page page) {
        Objects.requireNonNull(page, "page");
        AtomicBoolean refreshRequested = new AtomicBoolean();
        page.onResponse(response -> handleResponse(response.status(), response.url(), refreshRequested,
                // Schedule in the browser rather than waiting for navigation inside a response callback.
                () -> page.evaluate("() => { setTimeout(() => window.location.reload(), 0); }")));
    }

    private static void handleResponse(int status, String url, AtomicBoolean refreshRequested, Runnable refresh) {
        if (status >= 500 && status < 600) {
            logger.error("SERVER ERROR " + status + " " + url);
            if (refreshRequested.compareAndSet(false, true)) {
                try {
                    refresh.run();
                } catch (RuntimeException error) {
                    logger.error("Failed to request page refresh after SERVER ERROR " + status + " " + url, error);
                    throw error;
                }
            }
        }
    }
}
