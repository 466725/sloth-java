package unit;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import core.TestGroups;
import core.UnitTestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ServerErrorMonitor;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.expectThrows;

@Test(groups = {TestGroups.UNIT, TestGroups.SMOKE, TestGroups.REGRESSION})
public class ServerErrorMonitorTest extends UnitTestCase {
    @DataProvider
    public Object[][] responseStatuses() {
        return new Object[][] {
                {200, false}, {302, false}, {404, false}, {499, false},
                {500, true}, {502, true}, {503, true}, {599, true}, {600, false}
        };
    }

    @SuppressWarnings("unchecked")
    @Test(dataProvider = "responseStatuses")
    public void playwrightLogsOnlyServerErrors(int status, boolean expectedError) {
        AtomicReference<Consumer<Response>> listener = new AtomicReference<>();
        AtomicInteger refreshes = new AtomicInteger();
        Page page = (Page) Proxy.newProxyInstance(Page.class.getClassLoader(),
                new Class<?>[] {Page.class}, (proxy, method, args) -> {
                    if (method.getName().equals("onResponse")) {
                        listener.set((Consumer<Response>) args[0]);
                        return null;
                    }
                    if (method.getName().equals("evaluate")) {
                        assertEquals(args[0], "() => { setTimeout(() => window.location.reload(), 0); }");
                        refreshes.incrementAndGet();
                        // A response arriving during refresh must not cause another refresh.
                        listener.get().accept(response(status, "https://example.test/api"));
                        return null;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
        String url = "https://example.test/api";
        Response response = response(status, url);

        List<String> messages = new ArrayList<>();
        AbstractAppender appender = new AbstractAppender(
                "server-error-monitor-test", null, null, false, Property.EMPTY_ARRAY) {
            @Override
            public void append(LogEvent event) {
                messages.add(event.getMessage().getFormattedMessage());
            }
        };
        Logger monitorLogger = (Logger) LogManager.getLogger(ServerErrorMonitor.class);
        appender.start();
        monitorLogger.addAppender(appender);
        try {
            ServerErrorMonitor.monitor(page);
            assertNotNull(listener.get(), "A response listener must be registered before navigation");
            listener.get().accept(response);
            listener.get().accept(response);
            assertEquals(refreshes.get(), expectedError ? 1 : 0);
            assertEquals(messages, expectedError
                    ? List.of("SERVER ERROR " + status + " " + url,
                            "SERVER ERROR " + status + " " + url,
                            "SERVER ERROR " + status + " " + url) : List.of());
        } finally {
            monitorLogger.removeAppender(appender);
            appender.stop();
        }
    }

    @SuppressWarnings("unchecked")
    public void refreshFailureIsPropagatedAndNotRetried() {
        AtomicReference<Consumer<Response>> listener = new AtomicReference<>();
        AtomicInteger refreshes = new AtomicInteger();
        IllegalStateException failure = new IllegalStateException("Page closed");
        Page page = (Page) Proxy.newProxyInstance(Page.class.getClassLoader(),
                new Class<?>[] {Page.class}, (proxy, method, args) -> {
                    if (method.getName().equals("onResponse")) {
                        listener.set((Consumer<Response>) args[0]);
                        return null;
                    }
                    if (method.getName().equals("evaluate")) {
                        refreshes.incrementAndGet();
                        throw failure;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
        ServerErrorMonitor.monitor(page);
        Response response = response(503, "https://example.test/unavailable");
        assertEquals(expectThrows(IllegalStateException.class, () -> listener.get().accept(response)), failure);
        listener.get().accept(response);
        assertEquals(refreshes.get(), 1);
    }

    private static Response response(int status, String url) {
        return (Response) Proxy.newProxyInstance(Response.class.getClassLoader(),
                new Class<?>[] {Response.class}, (proxy, method, args) -> switch (method.getName()) {
                    case "status" -> status;
                    case "url" -> url;
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }

    public void rejectsDriversWithoutDevTools() {
        WebDriver driver = (WebDriver) Proxy.newProxyInstance(WebDriver.class.getClassLoader(),
                new Class<?>[] {WebDriver.class}, (proxy, method, args) -> {
                    throw new UnsupportedOperationException(method.getName());
                });
        IllegalArgumentException error = Objects.requireNonNull(expectThrows(IllegalArgumentException.class,
                () -> ServerErrorMonitor.monitor(driver)));
        assertEquals(error.getMessage(),
                "Server error monitoring requires a CDP-capable Selenium driver implementing HasDevTools.");
    }

    public void rejectsNullInputs() {
        expectThrows(NullPointerException.class, () -> ServerErrorMonitor.monitor((WebDriver) null));
        expectThrows(NullPointerException.class, () -> ServerErrorMonitor.monitor((Page) null));
    }
}
