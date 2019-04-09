package com.test.api.testcases;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.relevantcodes.extentreports.LogStatus;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class PostmanExample extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(PostmanExample.class.getName());

	@Test(priority = 1)
	public void getScheduleBeforeCreation() throws IOException, ParseException {
		test = extent.startTest("Schedule: Get a schedule prior creation");
		test.log(LogStatus.INFO, "Schedule: Get a schedule prior creation");

		Request request = new Request.Builder()
		  .url("https://aries-qa.volantecloud.com/aries/api/menu/v1/99/schedules")
		  .get()
		  .addHeader("Content-Type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("Postman-Token", "0a8f7c9a-414c-4638-88b3-cf23d241df6b")
		  .build();
		Response response = client.newCall(request).execute();

		Assert.assertTrue(response.isSuccessful());
		logger.info("Response code(): " + response.code());
		Assert.assertTrue(response.code() == 200);
		logger.info("Response headers().OkHttp-Sent-Millis: " + response.headers("OkHttp-Sent-Millis"));
		logger.info("Response headers().OkHttp-Received-Millis: " + response.headers("OkHttp-Received-Millis"));
		Long responseTime = (Long.parseLong(response.headers("OkHttp-Received-Millis").get(0))) - (Long.parseLong(response.headers("OkHttp-Sent-Millis").get(0)));
		Assert.assertTrue(responseTime < 500);
		logger.info("Response message(): " + response.message());
		Assert.assertTrue(response.message().contains("OK"));
		logger.info("Response body(): " + response.body());
		logger.info("Response toString(): " + response.toString());
		Assert.assertTrue(response.toString().contains("code=200"));
	}
}
