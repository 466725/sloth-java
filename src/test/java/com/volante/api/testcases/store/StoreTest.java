package com.volante.api.testcases.store;

import com.framework.templates.ApiTestCase;
import com.framework.templates.TestCase;
import com.volante.api.testcases.TokenManagementTest;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Store API test cases are all here, Add + Update + Delete...
 *
 * @author Weipeng Zheng
 *
 */
public class StoreTest extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(StoreTest.class.getName());
    private static String companyOneName = faker.company().name();
    private static String companyTwoName = faker.company().name();
    private static String companyTwoOldAddress = faker.address().streetAddress();
    private static String companyTwoNewAddress = faker.address().streetAddress();
    private static String companyTwoOldPhone = faker.phoneNumber().cellPhone();
    private static String companyTwoNewPhone = faker.phoneNumber().cellPhone();
    private static String stationOneName = faker.name().firstName();
    private static String stationTwoName = faker.name().firstName();
    private static String terminalGroupsName = faker.name().firstName();
    private static String storeOneID = "";
    private static String storeTwoID = "";
    private static String stationOneID = "";
    private static String stationTwoID = "";
    private static String terminalGroupsID = "";

    @Test(priority = 1)
    public static void generateToken() throws IOException, ParseException {
        TokenManagementTest.generateTokenWithJsonFileBody();
    }

    @Test(priority = 3)
    public static void addFirstStore() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + faker.address().streetAddress() + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyOneName + "\","
                        + "\r\n  \"phoneNumber\": \"" + faker.phoneNumber().cellPhone() + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();
        responseBody = response.body();
        responseString = responseBody.string();
        JSONObject responseJson = (JSONObject) parser.parse(responseString);
        storeOneID = responseJson.get("storeId").toString();

        Assert.assertTrue(response.code() == 201);
        Assert.assertTrue(responseJson.containsKey("createTime"));
        Assert.assertTrue(responseJson.containsKey("address"));
        Assert.assertTrue(responseJson.containsKey("postalCode"));
    }

    @Test(priority = 5)
    public static void verifyFirstStoreCreated() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeOneID;
        Assert.assertTrue(ApiTestCase.isGetSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyCreatedSuccessful(url));
    }

    @Test(priority = 7)
    public static void addStoreAgainWithSameName() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + faker.address().streetAddress() + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyOneName + "\","
                        + "\r\n  \"phoneNumber\": \"" + faker.phoneNumber().cellPhone() + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 400);
    }

    @Test(priority = 9)
    public static void addStoreAgainWithEmptyName() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + faker.address().streetAddress() + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + "" + "\","
                        + "\r\n  \"phoneNumber\": \"" + faker.phoneNumber().cellPhone() + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 400);
    }

    @Test(priority = 11)
    public static void deleteFirstStore() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeOneID;
        Assert.assertTrue(ApiTestCase.isDeleteSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyDeleteSuccessful(url));
    }

    @Test(priority = 13)
    public static void addSecondStore() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + companyTwoOldAddress + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyTwoName + "\","
                        + "\r\n  \"phoneNumber\": \"" + companyTwoOldPhone + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\","
                        + "\r\n  \"stations\": [\r\n    {\r\n      \"name\": \""
                        + stationOneName + "\"\r\n    },\r\n    {\r\n      \"name\": \""
                        + stationTwoName + "\"\r\n    }\r\n  ]\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();
        responseBody = response.body();
        responseString = responseBody.string();
        JSONObject responseJson = (JSONObject) parser.parse(responseString);
        storeTwoID = responseJson.get("storeId").toString();

        logger.debug(responseJson.get("createTime"));
        logger.debug(responseJson.get("storeId"));
        logger.debug(responseJson.get("address"));
        logger.debug(responseJson.get("stations"));
        logger.debug((JSONArray) responseJson.get("stations"));
        logger.debug(((JSONObject) ((JSONArray) responseJson.get("stations")).get(0)).toString());
        logger.debug(((JSONObject) ((JSONArray) responseJson.get("stations")).get(1)).toString());
        stationOneID = ((JSONObject) ((JSONArray) responseJson.get("stations")).get(0)).get("stationId").toString();
        stationTwoID = ((JSONObject) ((JSONArray) responseJson.get("stations")).get(1)).get("stationId").toString();
        logger.debug("First station ID is: " + stationOneID);
        logger.debug("Second station ID is: " + stationTwoID);

        Assert.assertTrue(response.code() == 201);
        Assert.assertTrue(responseJson.containsKey("createTime"));
        Assert.assertTrue(responseJson.containsKey("address"));
        Assert.assertTrue(responseJson.containsKey("postalCode"));
        Assert.assertTrue(responseJson.containsKey("phoneNumber"));
        Assert.assertTrue(responseJson.containsKey("vertical"));
        Assert.assertTrue(!stationOneID.isEmpty());
        Assert.assertTrue(!stationTwoID.isEmpty());
    }

    @Test(priority = 15)
    public static void verifySecondStoreCreatedEasyWay() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID;
        Assert.assertTrue(ApiTestCase.isGetSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyCreatedSuccessful(url));
    }

    @Test(priority = 17)
    public static void getAllStores() throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/?size=200")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 19)
    public static void updateSeondStoreName() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + companyTwoOldAddress + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyTwoName + companyTwoName + "\","
                        + "\r\n  \"phoneNumber\": \"" + companyTwoOldPhone + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID)
                .patch(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 21)
    public static void updateSeondStoreAddress() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + companyTwoNewAddress + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyTwoName + companyTwoName + "\","
                        + "\r\n  \"phoneNumber\": \"" + companyTwoOldPhone + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID)
                .patch(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 23)
    public static void updateSeondStorePhoneNumber() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"address\": \"" + companyTwoNewAddress + "\","
                        + "\r\n  \"cityId\": 4952206,"
                        + "\r\n  \"name\": \"" + companyTwoName + companyTwoName + "\","
                        + "\r\n  \"phoneNumber\": \"" + companyTwoNewPhone + "\","
                        + "\r\n  \"postalCode\": \"" + faker.address().zipCode() + "\","
                        + "\r\n  \"vertical\": \"ENTERTAINMENT\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID)
                .patch(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 25)
    public static void updateSeondStoreStationName() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"stations\": [\r\n    {\r\n      \"name\": \"" + stationOneName + stationOneName + "\","
                        + "\r\n      \"stationId\": \"" + stationOneID + "\"\r\n    },"
                        + "\r\n    {\r\n\t  \"name\": \"" + stationTwoName + stationTwoName + "\","
                        + "\r\n\t  \"stationId\": \"" + stationTwoID + "\"\r\n    }\r\n  ]\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID)
                .patch(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 35)
    public static void verifyStoreInfoUpdatedAccordingly() throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();
        responseBody = response.body();
        responseString = responseBody.string();
        JSONObject responseJson = (JSONObject) parser.parse(responseString);

        logger.debug(responseString);
        logger.debug(responseJson.get("createTime"));
        logger.debug(responseJson.get("storeId"));
        logger.debug(responseJson.get("address"));
        logger.debug(responseJson.get("stations"));
        logger.debug((JSONArray) responseJson.get("stations"));
        logger.debug(((JSONObject) ((JSONArray) responseJson.get("stations")).get(0)).toString());
        logger.debug(((JSONObject) ((JSONArray) responseJson.get("stations")).get(1)).toString());
        stationOneID = ((JSONObject) ((JSONArray) responseJson.get("stations")).get(0)).get("stationId").toString();
        stationTwoID = ((JSONObject) ((JSONArray) responseJson.get("stations")).get(1)).get("stationId").toString();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(responseJson.size() > 0);
        Assert.assertTrue(responseJson.get("name").equals(companyTwoName + companyTwoName));
        Assert.assertTrue(responseJson.get("address").equals(companyTwoNewAddress));
        Assert.assertTrue(responseJson.get("phoneNumber").equals(companyTwoNewPhone));
        Assert.assertTrue(!stationOneID.isEmpty());
        Assert.assertTrue(!stationTwoID.isEmpty());
    }

    @Test(priority = 37)
    public static void addSecondStoreToTerminalGroup() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"name\": \"" + terminalGroupsName + "\","
                        + "\r\n  \"storeId\": \"" + storeTwoID + "\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();
        responseBody = response.body();
        responseString = responseBody.string();
        JSONObject responseJson = (JSONObject) parser.parse(responseString);
        terminalGroupsID = responseJson.get("terminalGroupId").toString();

        Assert.assertTrue(response.code() == 201);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(!terminalGroupsID.isEmpty());
    }

    @Test(priority = 39)
    public static void verifyTerminalGroupCreated() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups/" + terminalGroupsID;
        Assert.assertTrue(ApiTestCase.isGetSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyCreatedSuccessful(url));
    }

    @Test(priority = 41)
    public static void updateTerminalGroupName() throws IOException, ParseException {
        RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"name\": \"" + terminalGroupsName + terminalGroupsName + "\"\r\n}");
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups/" + terminalGroupsID)
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
        Assert.assertTrue(responseJson.get("name").equals(terminalGroupsName + terminalGroupsName));
    }

    @Test(priority = 43)
    public static void getAllTerminalGroups() throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups/?size=200")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer" + ApiTestCase.globalToken)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.code() == 200);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(priority = 45)
    public static void deleteTerminalGroup() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups/" + terminalGroupsID;
        Assert.assertTrue(ApiTestCase.isDeleteSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyDeleteSuccessful(url));
    }

    @Test(priority = 47)
    public static void deleteSecondStore() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID;
        Assert.assertTrue(ApiTestCase.isDeleteSuccessful(url));
        Assert.assertTrue(ApiTestCase.verifyDeleteSuccessful(url));
    }

    @Test(priority = 49)
    public static void getDeletedTerminalGroup() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/terminal-groups/" + terminalGroupsID;
        Assert.assertTrue(ApiTestCase.verifyDeleteSuccessful(url));
    }

    @Test(priority = 51)
    public static void getDeletedSecondStore() throws IOException, ParseException {
        String url = TestCase.API_TEST_BASE_URL + "/api/store/v1/stores/" + storeTwoID;
        Assert.assertTrue(ApiTestCase.verifyDeleteSuccessful(url));
    }
}
