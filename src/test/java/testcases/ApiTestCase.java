package testcases;

import com.relevantcodes.extentreports.LogStatus;
import okhttp3.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.base.Throwables.getStackTraceAsString;

/**
 * Base class of all API test cases related objects
 *
 * @author Weipeng Zheng
 *
 */
public class ApiTestCase extends TestCase {
    protected final static Logger logger = LogManager.getLogger(ApiTestCase.class.getName());
    protected static final OkHttpClient client = new OkHttpClient();
    private static final String CONTENT_TYPE_JSON = "application/json";
    protected static final JSONParser parser = new JSONParser();
    public static String globalToken = "";

    /**
     * Prepare per BeforeClass annotation.     *
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {logger.info("-----------------------Beginning of class----------------------");}

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