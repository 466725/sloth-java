package com.test.api.testcases;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SiteTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(SiteTest.class.getName());

	@Test(priority = 1)
	public void getScheduleBeforeCreation() throws IOException, ParseException {
		test = extent.startTest("Site API Test: Generate Token");
		test.log(LogStatus.INFO, "Site API Test: Generate Token");

		RequestBody body = RequestBody.create(mediaType, "{\n   \"grant_type\": \"password\",\n   \"email\": \"vetest@volantesystems.com\",\n   \"password\": \"test\"\n}");
		// RequestBody fileBody = RequestBody.create(mediaType, new File()); // Use @DataProvider to create request body with json File
		Request request = new Request.Builder()
		  .url("https://dev.volantecloud.com/auth/auth/token")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Basic dmV0ZXN0QHZvbGFudGVzeXN0ZW1zLmNvbTp0ZXN0")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "f6f82d1e-a5e4-4497-aca8-d7d04f136636")
		  .build();

		Response response = client.newCall(request).execute();
		
		logger.info("response.isSuccessful(): " + response.isSuccessful());
		logger.info("response.isRedirect(): " + response.isRedirect());
		logger.info("response.protocol(): " + response.protocol());
		logger.info("response.message(): " + response.message());
		logger.info("response.code(): " + response.code());
		logger.info("response.headers(): ");
		logger.info("***********************************************************************");
		logger.info("*************************response.headers()****************************");
		logger.info("***********************************************************************");
		logger.info(response.headers());
		logger.info("***********************************************************************");
		logger.info("*************************response.headers()****************************");
		logger.info("***********************************************************************");
		
		//Example to verify header info
		logger.info("Response headers().OkHttp-Sent-Millis: " + response.headers("OkHttp-Sent-Millis"));
		logger.info("Response headers().OkHttp-Received-Millis: " + response.headers("OkHttp-Received-Millis"));
		Long responseTime = (Long.parseLong(response.headers("OkHttp-Received-Millis").get(0))) - (Long.parseLong(response.headers("OkHttp-Sent-Millis").get(0)));
		Assert.assertTrue(responseTime < 500);
		
		responseBody = response.body();
		responseString = responseBody.string();
		logger.info("responseBody.contentType(): " + responseBody.contentType());
		logger.info("responseBody.string(): ");
		logger.info("***********************************************************************");
		logger.info("*************************response.string()*****************************");
		logger.info("***********************************************************************");
		logger.info(responseString);
		logger.info("***********************************************************************");
		logger.info("*************************response.string()*****************************");
		logger.info("***********************************************************************");

		// Example to verify response body info
		JSONObject obj = (JSONObject) parser.parse(responseString);
		logger.info("non_existing_token is: " + obj.get("non_existing_token"));
		logger.info("access_token is: " + obj.get("access_token"));
		logger.info("refresh_token is: " + obj.get("refresh_token"));

		Assert.assertTrue(response.code() == 200);
	}
}
