package com.test.api.testcases;

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
import com.utilities.StringWrapper;

/**
 * Token management test cases are all here, Add + Update + Delete...
 * 
 * @author Weipeng Zheng
 *
 */
public class TokenManagementTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(TokenManagementTest.class.getName());

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 1)
	public static void generateToken() throws IOException, ParseException {
		test = extent.startTest("Token management test: Generate Token");
		SoftAssertions softly = new SoftAssertions();

		//RequestBody body = RequestBody.create(contentType, file);
		RequestBody body = RequestBody.create(mediaType,
				"{\n   \"grant_type\": \"password\","
				+ "\n   \"email\": \"vetest@volantesystems.com\","
				+ "\n   \"password\": \"test\"\n}");
		Request request = new Request.Builder()
				.url("https://dev.volantecloud.com/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Basic dmV0ZXN0QHZvbGFudGVzeXN0ZW1zLmNvbTp0ZXN0")
				.addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "f6f82d1e-a5e4-4497-aca8-d7d04f136636")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);

		softly.assertThat(response.code() == 200);
		softly.assertThat(response.isSuccessful());
		softly.assertThat(response.isRedirect());
		softly.assertThat(response.protocol().equals("http/1.1"));
		softly.assertThat(response.message().equals("OK"));
		softly.assertThat(response.code() == 200);
		softly.assertThat((responseJsonObject.get("non_existing_token") != null)); //if you are not familiar with java syntax, it's fine 
		softly.assertThat(StringWrapper.isObjectNull(responseJsonObject.get("non_existing_token")));
		softly.assertThat(responseJsonObject.get("access_token") != null);
		softly.assertThat(responseJsonObject.get("refresh_token") != null);
		
		Long responseReceivedTime = Long.parseLong(response.headers("OkHttp-Received-Millis").get(0));
		Long requestSentTime = Long.parseLong(response.headers("OkHttp-Sent-Millis").get(0));
		Long responseTime = responseReceivedTime - requestSentTime;
		softly.assertThat(responseTime < 500);

		logger.info("response.isSuccessful(): " + response.isSuccessful());
		logger.info("response.isRedirect(): " + response.isRedirect());
		logger.info("response.protocol(): " + response.protocol());
		logger.info("response.headers(): " + response.headers());
		logger.info("response.message(): " + response.message());
		logger.info("response.code(): " + response.code());
		logger.info("responseBody.contentType(): " + responseBody.contentType());
		logger.info("responseBody.string(): " + responseString);
		logger.info("non_existing_token is: " + responseJsonObject.get("non_existing_token"));
		logger.info("access_token is: " + responseJsonObject.get("access_token"));
		logger.info("responseReceivedTime is: " + responseReceivedTime);
		logger.info("requestSentTime is: " + requestSentTime);
		logger.info("responseTime is: " + responseTime);

		softly.assertAll();
	}
	
	public static boolean generateTokenStep() throws IOException, ParseException {
		RequestBody body = RequestBody.create(mediaType,
				"{\n   \"grant_type\": \"password\","
				+ "\n   \"email\": \"vetest@volantesystems.com\","
				+ "\n   \"password\": \"test\"\n}");
		Request request = new Request.Builder()
				.url("https://dev.volantecloud.com/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Basic dmV0ZXN0QHZvbGFudGVzeXN0ZW1zLmNvbTp0ZXN0")
				.addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "f6f82d1e-a5e4-4497-aca8-d7d04f136636")
				.build();

		Response response = client.newCall(request).execute();

		return response.isSuccessful();
	}
	
	public static Object generateTokenStepReturnToken() throws IOException, ParseException {
		RequestBody body = RequestBody.create(mediaType,
				"{\n   \"grant_type\": \"password\","
				+ "\n   \"email\": \"vetest@volantesystems.com\","
				+ "\n   \"password\": \"test\"\n}");
		Request request = new Request.Builder()
				.url("https://dev.volantecloud.com/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Basic dmV0ZXN0QHZvbGFudGVzeXN0ZW1zLmNvbTp0ZXN0")
				.addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "f6f82d1e-a5e4-4497-aca8-d7d04f136636")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);

		return responseJsonObject.get("access_token");
	}
}
