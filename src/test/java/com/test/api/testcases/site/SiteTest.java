package com.test.api.testcases.site;

import java.io.IOException;

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
import com.test.api.testcases.TokenManagementTest;

/**
 * Site API test cases are all here, Add + Update + Delete...
 * 
 * @author Weipeng Zheng
 *
 */
public class SiteTest extends ApiTestCase {
	protected final static Logger logger = LogManager.getLogger(SiteTest.class.getName());
	private static String firstCompanyName = faker.company().name();
	private static String secondCompanyNameOne = faker.company().name();
	private static String secondCompanyNameTwo = faker.company().name();
	private static String secondCompanyAddressOne = faker.address().streetAddress();
	private static String secondCompanyAddressTwo = faker.address().streetAddress();
	private static String secondCompanyPhoneOne = faker.phoneNumber().cellPhone();
	private static String secondCompanyPhoneTwo = faker.phoneNumber().cellPhone();
	private static String firstSiteID = "";
	private static String secondSiteID = "";
	
	@Test(priority = 1)
	public static void generateToken() throws IOException, ParseException {
		test = extent.startTest("Generate Token");
		
		//TokenManagementTest.generateToken();
		TokenManagementTest.generateTokenWithJsonFileBody();
	}
	
	@Test(priority = 3)
	public static void addFirstSite() throws IOException, ParseException {
		test = extent.startTest("Add the first site");
		
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"" + faker.address().streetAddress() + "\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + firstCompanyName + "\","
				+ "\r\n  \"phoneNumber\": \"" + faker.phoneNumber().cellPhone() + "\","
				+ "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);
		firstSiteID = responseJson.get("siteId").toString();

		Assert.assertTrue(response.code() == 201);
		Assert.assertTrue(responseJson.containsKey("createTime"));
		Assert.assertTrue(responseJson.containsKey("address"));
		Assert.assertTrue(responseJson.containsKey("postalCode"));
	}

	@Test(priority = 5)
	public static void verifyFirstSiteCreated() throws IOException, ParseException {
		test = extent.startTest("Verify the first site is created");

		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + firstSiteID)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);
		
		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.get("siteId").equals(firstSiteID));
		Assert.assertTrue(responseJson.containsValue(firstSiteID));
	}
	
	@Test(priority = 6)
	public static void verifyFirstSiteCreatedEasyWay() throws IOException, ParseException {
		test = extent.startTest("Verify the first site is created");
		
		Assert.assertTrue(ApiTestCase.isGetSuccessful(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + firstSiteID));
	}
	
	@Test(priority = 7)
	public static void addSiteAgainWithSameName() throws IOException, ParseException {
		test = extent.startTest("Add another site with first site's name");

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"300 Titus Avenue\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + firstCompanyName + "\","
				+ "\r\n  \"phoneNumber\": \"416-221-1132\","
				+ "\r\n  \"postalCode\": \"L111X4\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		Assert.assertTrue(response.code() == 400);
	}
	
	@Test(priority = 9)
	public static void addSiteAgainWithEmptyName() throws IOException, ParseException {
		test = extent.startTest("Add another site with empty name");

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"300 Titus Avenue\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": ,"
				+ "\r\n  \"phoneNumber\": \"416-221-1132\","
				+ "\r\n  \"postalCode\": \"L111X4\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		Assert.assertTrue(response.code() == 400);
	}
	
	@Test(priority = 13)
	public static void addSecondSite() throws IOException, ParseException {
		test = extent.startTest("Add the second site");
		
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"" + secondCompanyAddressOne + "\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + secondCompanyNameOne + "\","
				+ "\r\n  \"phoneNumber\": \"" + secondCompanyPhoneOne + "\","
				+ "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);
		secondSiteID = responseJson.get("siteId").toString();

		Assert.assertTrue(response.code() == 201);
		Assert.assertTrue(responseJson.containsKey("createTime"));
		Assert.assertTrue(responseJson.containsKey("address"));
		Assert.assertTrue(responseJson.containsKey("postalCode"));
		Assert.assertTrue(responseJson.containsKey("phoneNumber"));
	}
	
	@Test(priority = 15)
	public static void getAllSites() throws IOException, ParseException {
		test = extent.startTest("Get all sites");

		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/?size=50")
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.size() > 0);
	}
	
	@Test(priority = 17)
	public static void updateSeondSiteName() throws IOException, ParseException {
		test = extent.startTest("Update name of the second site");

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"" + secondCompanyAddressOne + "\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + secondCompanyNameTwo + "\","
				+ "\r\n  \"phoneNumber\": \"" + secondCompanyPhoneOne + "\","
				+ "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID)
				.patch(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.size() > 0);
	}
	
	@Test(priority = 19)
	public static void updateSeondSiteAddress() throws IOException, ParseException {
		test = extent.startTest("Update address of the second site");

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"" + secondCompanyAddressTwo + "\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + secondCompanyNameTwo + "\","
				+ "\r\n  \"phoneNumber\": \"" + secondCompanyPhoneOne + "\","
				+ "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID)
				.patch(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.size() > 0);
	}
	
	@Test(priority = 21)
	public static void updateSeondSitePhoneNumber() throws IOException, ParseException {
		test = extent.startTest("Update phone number of the second site");

		RequestBody body = RequestBody.create(mediaType,
				"{\r\n  \"address\": \"" + secondCompanyAddressTwo + "\","
				+ "\r\n  \"cityId\": 4952206,"
				+ "\r\n  \"name\": \"" + secondCompanyNameTwo + "\","
				+ "\r\n  \"phoneNumber\": \"" + secondCompanyPhoneTwo + "\","
				+ "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
				+ "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID)
				.patch(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.size() > 0);
	}
	
	@Test(priority = 23)
	public static void verifySiteInfoUpdatedAccordingly() throws IOException, ParseException {
		test = extent.startTest("Verify updated info of the second site");

		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();
		JSONObject responseJson = (JSONObject) parser.parse(responseString);

		Assert.assertTrue(response.code() == 200);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(responseJson.size() > 0);
		Assert.assertTrue(responseJson.get("name").equals(secondCompanyNameTwo));
		Assert.assertTrue(responseJson.get("address").equals(secondCompanyAddressTwo));
		Assert.assertTrue(responseJson.get("phoneNumber").equals(secondCompanyPhoneTwo));
	}
	
	@Test(priority = 11)
	public static void deleteFirstSite() throws IOException, ParseException {
		test = extent.startTest("Delete the first site with ID");

		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + firstSiteID)
				.delete(null)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		Assert.assertTrue(response.code() == 200);
	}
	
	@Test(priority = 25)
	public static void deleteSecondSite() throws IOException, ParseException {
		test = extent.startTest("Delete the second site");

		Assert.assertTrue(ApiTestCase.isDeleteSuccessful(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID));
	}
	
	@Test(priority = 27)
	public static void getDeletedSecondSite() throws IOException, ParseException {
		test = extent.startTest("Verify the second site could be deleted");

		Request request = new Request.Builder()
				.url(TestCase.API_TEST_BASE_URL + "/api/store/v1/sites/" + secondSiteID)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
				.build();

		Response response = client.newCall(request).execute();
		responseBody = response.body();
		responseString = responseBody.string();

		Assert.assertTrue(response.code() == 200);
	}
}
