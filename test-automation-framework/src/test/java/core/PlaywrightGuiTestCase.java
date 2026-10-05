package core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.Constants;
import config.PropertiesFileReader;
import config.RunConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Base class for Playwright UI test lifecycle and screenshot handling.
 */
public class PlaywrightGuiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(PlaywrightGuiTestCase.class.getName());

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logger.info("-----------------------Beginning of class----------------------");
        ensureBrowserStarted();
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void beforeMethod(Method method) {
        super.beforeMethod(method);
        ensureBrowserStarted();
        context = browser.newContext(new Browser.NewContextOptions()
                .setLocale("en-US")
                .setViewportSize(1920, 1080)
                .setExtraHTTPHeaders(Map.of("Accept-Language", "en-US,en;q=0.9")));
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void afterMethod(ITestResult result) {
        try {
            logger.info("***** Class: " + result.getTestClass().getName() + " *****");
            logger.info("***** Method: " + result.getName() + "(...) *****");

            String screenShotPath = null;
            if (page != null) {
                String screenshotName = LocalDateTime.now().format(SCREENSHOT_TS) + "_" + result.getName();
                screenShotPath = captureScreenShot(page, screenshotName);
            } else {
                logger.warn("Page is null in @AfterMethod; skipping screenshot.");
            }

            logResultToExtent(result, screenShotPath);
        } finally {
            safeCloseContext();
            super.afterMethod(result);
        }
    }

    @AfterTest(alwaysRun = true)
    @Override
    public void afterTest() {
        try {
            safeCloseContext();
            safeCloseBrowser();
            safeClosePlaywright();
        } finally {
            super.afterTest();
            logger.info("----------------------Ending of test--------------------------");
        }
    }

    private void ensureBrowserStarted() {
        if (browser != null) {
            return;
        }

        playwright = Playwright.create();
        browser = launchBrowser(playwright, resolveBrowserName());
    }

    private Browser launchBrowser(Playwright playwright, String browserName) {
        String normalized = (browserName == null ? "" : browserName).trim().toLowerCase(Locale.ROOT);

        boolean headless = RunConfig.isHeadless();
        List<String> chromiumArgs = headless
                ? List.of("--no-sandbox", "--disable-dev-shm-usage")
                : null;

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        // CI/Docker stability: avoid /dev/shm crashes and sandbox issues when running headless in containers.
        // These flags are safe on local too, but we keep them headless-only to minimize surprises.
        if (chromiumArgs != null) {
            options.setArgs(chromiumArgs);
        }

        // Allow "chrome"/"msedge" channels while defaulting safely to bundled Chromium.
        if ("chrome".equals(normalized) || "googlechrome".equals(normalized)) {
            return launchChromiumWithOptionalChannel(playwright, headless, chromiumArgs, "chrome");
        }
        if ("edge".equals(normalized) || "msedge".equals(normalized)) {
            return launchChromiumWithOptionalChannel(playwright, headless, chromiumArgs, "msedge");
        }

        return switch (normalized) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            case "chromium" -> playwright.chromium().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }

    private Browser launchChromiumWithOptionalChannel(Playwright playwright, boolean headless, List<String> chromiumArgs, String channel) {
        try {
            BrowserType.LaunchOptions channelOptions = new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setChannel(channel);
            if (chromiumArgs != null) {
                channelOptions.setArgs(chromiumArgs);
            }
            return playwright.chromium().launch(channelOptions);
        } catch (RuntimeException e) {
            logger.warn("Failed to launch channel '" + channel + "'. Falling back to bundled Chromium.", e);
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
            if (chromiumArgs != null) {
                options.setArgs(chromiumArgs);
            }
            return playwright.chromium().launch(options);
        }
    }

    private String resolveBrowserName() {
        String prop = System.getProperty("playwright.browser");
        if (prop != null && !prop.isBlank()) {
            return prop.trim();
        }
        String env = System.getenv("PLAYWRIGHT_BROWSER");
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        // Reuse existing config key when present (often "chrome").
        try {
            return PropertiesFileReader.getBrowser();
        } catch (Exception ignored) {
            return "chromium";
        }
    }

    private String captureScreenShot(Page page, String screenshotName) {
        try {
            Path outDir = Path.of(Constants.SCREENSHOT_FOLDER);
            Files.createDirectories(outDir);
            Path outFile = outDir.resolve(screenshotName + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(outFile).setFullPage(true));
            return "ScreenShot/" + screenshotName + ".png";
        } catch (Exception e) {
            logger.error("Failed to capture Playwright screenshot.", e);
            return null;
        }
    }

    private void logResultToExtent(ITestResult result, String screenShotPath) {
        if (test == null) {
            logger.warn("ExtentTest is null in PlaywrightGuiTestCase.logResultToExtent; skipping report logging.");
            return;
        }

        String className = result.getTestClass().getName();
        String methodName = result.getMethod().getMethodName();
        Throwable exception = result.getThrowable();
        String logDetails = String.format("%s:  %s", className, methodName);

        LogStatus status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> LogStatus.PASS;
            case ITestResult.FAILURE -> LogStatus.FAIL;
            case ITestResult.SKIP -> LogStatus.SKIP;
            default -> LogStatus.FATAL;
        };

        test.log(status, logDetails);
        if (status != LogStatus.PASS && exception != null) {
            test.log(status, getStackTraceAsString(exception));
        }

        if (screenShotPath != null) {
            test.log(status, test.addScreenCapture(screenShotPath));
        } else {
            logger.warn("No screenshot path available to attach to report.");
        }
    }

    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void safeCloseContext() {
        try {
            if (page != null) {
                page.close();
            }
        } catch (Exception ignored) {
        } finally {
            page = null;
        }

        try {
            if (context != null) {
                context.close();
            }
        } catch (Exception ignored) {
        } finally {
            context = null;
        }
    }

    private void safeCloseBrowser() {
        try {
            if (browser != null) {
                browser.close();
            }
        } catch (Exception ignored) {
        } finally {
            browser = null;
        }
    }

    private void safeClosePlaywright() {
        try {
            if (playwright != null) {
                playwright.close();
            }
        } catch (Exception ignored) {
        } finally {
            playwright = null;
        }
    }
}
