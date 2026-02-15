package scenarios.api.cineplex;

import config.PropertiesFileReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import testcases.ApiTestCase;
import testcases.TestGroups;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

// For Demo purpose only
public class TestSigninWithRestAssured extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSigninWithRestAssured.class.getName());
    private final String connectURL = PropertiesFileReader.getCONNECT_URL();
    private static String sessionToken = "";

    @Test(priority = 1, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API, TestGroups.CANADA_ONLY})
    public void create_Application_Session_RestAssured_Call() {
        logger.info("API manager subscription key is: " + apiManagerSubscriptionKey);
        String payload = """
                {
                  "ApplicationKey": "2939bf3b-6c04-4c7b-bcfd-bb590e0016fa"
                }
                """;

        Response response =
                given()
                        .contentType(ContentType.JSON)     // sets Content-Type: application/json
                        .accept(ContentType.JSON)
                        .header("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                        .body(payload)
                        .when()
                        .post(connectURL + "/CreateApplicationSession")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Extract token from JSON response
        sessionToken = response.jsonPath().getString("SessionToken");

        logger.info("jsonBody: " + response.asString());
        logger.info("sessionToken: " + sessionToken);
    }

    @Test(priority = 7, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API, TestGroups.CANADA_ONLY})
    public void login_HashMapBody_RestAssured_Call() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("SessionToken", sessionToken);
        payload.put("Password", "password");
        payload.put("Email", "user.name.ca@gmail.com");
        payload.put("Source", "1");
        payload.put("LanguageType", "1");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                        .body(payload)
                        .when()
                        .post(connectURL + "/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // If you want to log the JSON response (similar to your logger.info("jsonBody: ..."))
        String responseBody = response.asString();
        logger.info("jsonBody: " + responseBody);
    }

    @Test(priority = 9, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API, TestGroups.CANADA_ONLY})
    public void login_RawJsonBody_restAssured_Call() {
        String body = """
                {
                  "SessionToken": "%s",
                  "Password": "password",
                  "Email": "user.name.ca@gmail.com",
                  "Source": "1",
                  "LanguageType": "1"
                }
                """.formatted(sessionToken);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                        .body(body)
                        .when()
                        .post(connectURL + "/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        logger.info("jsonBody: " + response.asString());
        logger.info("Login with raw JSON body");
    }
}
