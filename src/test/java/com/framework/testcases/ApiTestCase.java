package com.framework.testcases;

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.LogStatus;
import okhttp3.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

    // ... existing code ...
    /**
     * Cleanup per AfterMethod annotation.
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("***** Class: " + result.getTestClass().getName() + " *****");
        logger.info("***** Method: " + result.getName() + "(...) *****");

        if (!result.isSuccess()) {
            StringWriter sw = new StringWriter();
            Throwable exception = result.getThrowable();
            exception.printStackTrace(new PrintWriter(sw));

            LogStatus status = (result.getStatus() == ITestResult.SKIP) ? LogStatus.SKIP : LogStatus.FAIL;
            if (test != null) {
                test.log(status, sw.getBuffer().toString());
            }
            logger.error("Exception is: ", exception);
        }
        logger.info("-----------------------Ending of method------------------------");
    }

    private static Response executeRequest(String url, String method) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer " + globalToken);

        if ("DELETE".equalsIgnoreCase(method)) {
            builder.delete();
        } else {
            builder.get();
        }

        return client.newCall(builder.build()).execute();
    }

    public static JSONObject getAPI(String url) throws IOException, ParseException {
        try (Response response = executeRequest(url, "GET")) {
            return (JSONObject) parser.parse(response.body().string());
        }
    }

    public static boolean isGetSuccessful(String url) throws IOException {
        try (Response response = executeRequest(url, "GET")) {
            return response.code() == 200;
        }
    }

    public static JSONObject deleteAPI(String url) throws IOException, ParseException {
        try (Response response = executeRequest(url, "DELETE")) {
            return (JSONObject) parser.parse(response.body().string());
        }
    }

    public static boolean isDeleteSuccessful(String url) throws IOException {
        try (Response response = executeRequest(url, "DELETE")) {
            return response.code() == 200;
        }
    }

    public static boolean verifyCreatedSuccessful(String url) throws IOException, ParseException {
        JSONObject json = getAPI(url);
        return json.get("createTime") != null;
    }

    public static boolean verifyDeleteSuccessful(String url) throws IOException, ParseException {
        JSONObject json = getAPI(url);
        return Boolean.TRUE.equals(json.get("deleted"));
    }
}