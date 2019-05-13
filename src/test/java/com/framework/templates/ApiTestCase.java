package com.framework.templates;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.LogStatus;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * Base class of all API test cases related objects
 * 
 * @author Weipeng Zheng
 *
 */
public class ApiTestCase extends TestCase {
	protected final static Logger logger = LogManager.getLogger(ApiTestCase.class.getName());
	protected static OkHttpClient client = new OkHttpClient();
	protected static MediaType mediaType = MediaType.parse("application/json");
	protected static ResponseBody responseBody = null;
	protected static String responseString = "";
	protected static JSONParser parser = new JSONParser();
	protected static String globalToken = "";
	protected static Faker faker = new Faker();

	/**
	 * Prepare per BeforeClass annotation.
	 */
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		logger.info("-----------------------Beginning of class----------------------");
	}

	/**
	 * Prepare per BeforeMethod annotation.
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		logger.info("-----------------------Beginning of method---------------------");
	}

	/**
	 * Cleanup per AfterMethod annotation.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		logger.info("***** Class: " + result.getTestClass().getName() + " *****");
		logger.info("***** Method: " + result.getName() + "(...) *****");
		int resultStatus = result.getStatus();
		StringWriter sw = new StringWriter();
		Throwable exception = result.getThrowable();
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();

		switch (resultStatus) {
		case ITestResult.SUCCESS:
			test.log(LogStatus.PASS, String.format("%s:  %s", className, methodName));
			break;
		case ITestResult.FAILURE:
			test.log(LogStatus.FAIL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.FAIL, sw.getBuffer().toString());
			logger.error("Exception is: ", exception);
			break;
		case ITestResult.SKIP:
			test.log(LogStatus.SKIP, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.SKIP, sw.getBuffer().toString());
			logger.error("Exception is: ", exception);
			break;
		default:
			test.log(LogStatus.FATAL, String.format("%s:  %s", className, methodName));
			exception.printStackTrace(new PrintWriter(sw));
			test.log(LogStatus.FATAL, sw.getBuffer().toString());
			logger.error("Exception is: ", exception);
			break;
		}
		extent.endTest(test);
		try {
			responseBody.close();
		} catch (Exception e) {
			logger.warn("Caught IOException during responseBody.close().");
		}
		logger.info("-----------------------Ending of method------------------------");
	}

	/**
	 * Cleanup per AfterClass annotation.
	 */
	@AfterClass(alwaysRun = true)
	public void afterClass() {
		logger.info("----------------------Ending of class-------------------------");
	}
	
	/**
	 * Send get request, and return response as JSON object
	 * 
	 * @param url URI of targeted API
	 * @return JSONObject response JSON object
	 */
	public static JSONObject getAPI(String url) throws IOException, ParseException {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		
		return (JSONObject) parser.parse(responseString);
	}
	
	/**
	 * Send get request, and return true or false
	 * 
	 * @param url URI of targeted API
	 * @return boolean true if everything fine, otherwise false
	 */
	public static boolean isGetSuccessful(String url) throws IOException, ParseException {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		
		return response.code() == 200;
	}
	
	/**
	 * Send delete request, and return response as JSON object
	 * 
	 * @param url URI of targeted API
	 * @return JSONObject response JSON object
	 */
	public static JSONObject deleteAPI(String url) throws IOException, ParseException {
		Request request = new Request.Builder()
				.url(url)
				.delete(null)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		
		return (JSONObject) parser.parse(responseString);
	}
	
	/**
	 * Send delete request, and return true or false
	 * 
	 * @param url URI of targeted API
	 * @return boolean true if everything fine, otherwise false
	 */
	public static boolean isDeleteSuccessful(String url) throws IOException, ParseException {
		Request request = new Request.Builder()
				.url(url)
				.delete(null)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		return response.code() == 200;
	}
}