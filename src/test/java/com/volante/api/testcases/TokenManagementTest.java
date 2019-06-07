package com.volante.api.testcases;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.framework.templates.ApiTestCase;
import com.framework.templates.TestCase;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import config.Constants;

/**
 * Token management test cases are all here, Add + Update + Delete...
 * 
 * @author Weipeng Zheng
 *
 */
public class TokenManagementTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(TokenManagementTest.class.getName());

	public static void generateToken() throws IOException, ParseException {
		RequestBody body = RequestBody.create(mediaType,
				"{\n   \"grant_type\": \"password\","
				+ "\n   \"email\": \"vetest@volantesystems.com\","
				+ "\n   \"password\": \"test\"\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);
		
		ApiTestCase.globalToken = responseJsonObject.get("access_token").toString();
		logger.info("Global Token is: " + globalToken);
	}
	
	public static void generateTokenWithJsonFileBody() throws IOException, ParseException {
		String filePath = Constants.RESOURCE_FOLDER + Constants.SITE_REQUEST_BODY + "GenerateTokenBody.json";
		logger.info("Request body json: " + (new File(filePath)).getAbsolutePath());
		
		RequestBody body = RequestBody.create(mediaType, new File(filePath));
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);
		
		ApiTestCase.globalToken = responseJsonObject.get("access_token").toString();
		logger.info("Global Token is: " + globalToken);
	}
	
	@Test(priority = 3)
	public static void generateTokenTest() throws IOException, ParseException {
		test = extent.startTest("Generate Token");

		RequestBody body = RequestBody.create(mediaType,
				"{\n   \"grant_type\": \"password\","
				+ "\n   \"email\": \"vetest@volantesystems.com\","
				+ "\n   \"password\": \"test\"\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/auth/auth/token")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Basic dmV0ZXN0QHZvbGFudGVzeXN0ZW1zLmNvbTp0ZXN0")
				.addHeader("cache-control", "no-cache")
				.addHeader("access_token", "f6f82d1e-a5e4-4497-aca8-d7d04f136636")
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJsonObject = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(response.message().equals("OK"));
		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(responseJsonObject.get("access_token") != null);
		Assert.assertTrue(responseJsonObject.get("refresh_token") != null);
		
		Long responseReceivedTime = Long.parseLong(response.headers("OkHttp-Received-Millis").get(0));
		Long requestSentTime = Long.parseLong(response.headers("OkHttp-Sent-Millis").get(0));
		Long responseTime = responseReceivedTime - requestSentTime;

		logger.info("response.isSuccessful(): " + response.isSuccessful());
		logger.info("response.isRedirect(): " + response.isRedirect());
		logger.info("response.protocol(): " + response.protocol());
		logger.info("response.headers(): " + response.headers());
		logger.info("response.message(): " + response.message());
		logger.info("response.code(): " + response.code());
		logger.info("responseBody.contentType(): " + responseBody.contentType());
		logger.info("responseBody.string(): " + responseString);
		logger.info("access_token is: " + responseJsonObject.get("access_token"));
		logger.info("responseReceivedTime is: " + responseReceivedTime);
		logger.info("requestSentTime is: " + requestSentTime);
		logger.info("responseTime is: " + responseTime);
		
		Assert.assertTrue(responseTime < 800);
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws IOException {
		String filePath = Constants.RESOURCE_FOLDER + Constants.SITE_REQUEST_BODY + "GenerateTokenBody.json";
		logger.info("Request body json: " + (new File(filePath)).getAbsolutePath());

		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();

		obj.put("name", "foo");
		obj.put("num", new Integer(1050));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));

		obj2.putAll(obj);
		obj2.put("name2", "foo");
		obj2.put("num2", new Integer(1050));
		obj2.put("balance2", new Double(1000.21));
		obj2.put("is_vip2", new Boolean(true));

		StringWriter out = new StringWriter();
		obj.writeJSONString(out);

		StringWriter out2 = new StringWriter();
		obj2.writeJSONString(out2);

		String jsonText = out.toString();
		System.out.println(jsonText);

		String jsonText2 = out2.toString();
		System.out.println(jsonText2);

		System.out.println(obj2.containsKey("name"));
		System.out.println(obj2.containsKey("num"));
		System.out.println(obj2.containsKey("balance"));
		System.out.println(obj2.containsKey("are_vip"));
		System.out.println(obj2.containsValue("foo"));
		System.out.println(obj2.containsValue(100));
		System.out.println(obj2.containsValue(1000.21));
		System.out.println(obj2.containsValue(true));

		System.out.println(obj2.containsKey("name2"));
		System.out.println(obj2.containsKey("num2"));
		System.out.println(obj2.containsKey("balance2"));
		System.out.println(obj2.containsKey("is_vip2"));
		System.out.println(obj2.containsValue("foo"));
		System.out.println(obj2.containsValue(1050));
		System.out.println(obj2.containsValue(1000.21));
		System.out.println(obj2.containsValue(true));
	}
}
