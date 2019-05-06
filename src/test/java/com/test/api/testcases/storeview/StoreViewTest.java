package com.test.api.testcases.storeview;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.test.api.testcases.TokenManagementTest;

/**
 * Store view API test cases are all here, Add + Update + Delete...
 * 
 * @author Weipeng Zheng
 *
 */
public class StoreViewTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(StoreViewTest.class.getName());

	@Test(priority = 1)
	public static void generateToken() throws IOException, ParseException {
		test = extent.startTest("Store view API test: Generate Token");
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(TokenManagementTest.generateTokenStep());

		softly.assertAll();
	}

	@Test(priority = 2)
	public static void addSite() throws IOException, ParseException {
		test = extent.startTest("Store view API test: Add a site");
		SoftAssertions softly = new SoftAssertions();

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"300 Titus Avenue\",\r\n  \"cityId\": 4952206,\r\n  \"name\": \"SiteCreatedByAutomation1557149984\",\r\n  \"phoneNumber\": \"416-221-1132\",\r\n  \"postalCode\": \"L111X4\",\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder().url("https://dev.volantecloud.com/api/store/v1/sites").post(body)
				.addHeader("Content-Type", "application/json").addHeader("cache-control", "no-cache")
				.addHeader("access_token", TokenManagementTest.generateTokenStepReturnToken().toString()).build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);

		softly.assertThat(response.code() == 200);
		softly.assertThat(response.isSuccessful());
		softly.assertThat(responseJsonObject.get("access_token") != null);

		softly.assertAll();
	}

	@Test(priority = 3)
	public static void verifySiteCreated() throws IOException, ParseException {
		test = extent.startTest("Store view API test: Verify a site is created");
		SoftAssertions softly = new SoftAssertions();

		Request request = new Request.Builder()
				.url("https://dev.volantecloud.com/api/store/v1/sites/9bdce676-c85a-43bc-84b2-e4a108272ab3").get()
				.addHeader("Content-Type", "application/json").addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "b6394174-b143-494a-9995-8daf22b575a5").build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);

		softly.assertThat(response.code() == 200);
		softly.assertThat(response.isSuccessful());
		softly.assertThat(responseJsonObject.get("access_token") != null);

		softly.assertAll();
	}
}
