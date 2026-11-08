package core;

import config.EnvironmentConfig;
import com.relevantcodes.extentreports.LogStatus;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Throwables.getStackTraceAsString;

/**
 * Base class for API tests.
 *
 * @author Weipeng Zheng
 */
public class ApiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(ApiTestCase.class.getName());
    protected static OkHttpClient client = new OkHttpClient.Builder().build();
    protected static String apiManagerSubscriptionKey;
    protected CookieJar cookieJar = null;

    /**
     * Runs before each test class.
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        apiManagerSubscriptionKey = EnvironmentConfig.getRequired("API_MANAGER_SUBSCRIPTION_KEY");
        logger.info("-----------------------Beginning of class----------------------");
        cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
        client = new OkHttpClient.Builder().build();
    }

    /**
     * Runs after each test class.
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logger.info("----------------------Ending of class--------------------------");
    }

    /**
     * Runs after each test method.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        try {
            logger.info("***** Class: " + result.getTestClass().getName() + " *****");
            logger.info("***** Method: " + result.getName() + "(...) *****");
            String testName = result.getTestClass().getName() + "." + result.getName();
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.error("API test failed: " + testName, result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP && result.getThrowable() != null) {
                logger.warn("API test skipped or scheduled for retry: " + testName, result.getThrowable());
            }
            logResultToExtent(result);
        } finally {
            super.afterMethod(result);
        }
    }

    private void logResultToExtent(ITestResult result) {
        if (test == null) {
            logger.warn("ExtentTest is null in ApiTestCase.logResultToExtent; skipping report logging.");
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
    }
}
