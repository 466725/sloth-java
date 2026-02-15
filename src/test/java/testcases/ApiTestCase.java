package testcases;

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
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Throwables.getStackTraceAsString;

/**
 * Base class of all API test cases related objects
 *
 * @author Weipeng Zheng
 *
 */
public class ApiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(ApiTestCase.class.getName());
    protected static OkHttpClient client = new OkHttpClient.Builder().build();
    // Better to read from environment, System.getenv("API_MANAGER_SUBSCRIPTION_KEY"));
    protected static String apiManagerSubscriptionKey = "5c8c64aa27dc4384b59bf3ebf5547895";
    protected CookieJar cookieJar = null;

    /**
     * Prepare per BeforeClass annotation.     *
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
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
     * Cleanup per AfterClass annotation.
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logger.info("----------------------Ending of class--------------------------");
    }

    /**
     * Cleanup per BeforeMethod annotation.
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        logger.info("----------------------Beginning of method--------------------------");
    }

    /**
     * Cleanup per AfterMethod annotation.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        logResultToExtent(result);
        logger.info("-----------------------Ending of method------------------------");
    }

    private void logResultToExtent(ITestResult result) {
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