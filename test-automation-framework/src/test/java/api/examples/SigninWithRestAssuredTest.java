package api.examples;

import config.PropertiesFileReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import core.ApiTestCase;
import core.TestGroups;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

// Demo API tests for Rest Assured usage.
public class SigninWithRestAssuredTest extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninWithRestAssuredTest.class.getName());
    private static String sessionToken = "";
    private final String connectURL = PropertiesFileReader.getCONNECT_URL();

    @Test(priority = 1, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldCreateApplicationSessionWithRestAssured() {
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

        // Extract the session token from the JSON response.
        sessionToken = response.jsonPath().getString("SessionToken");

        logger.info("jsonBody: " + response.asString());
        logger.info("sessionToken: " + sessionToken);
    }

    @Test(priority = 3, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldLoginWithMapPayloadUsingRestAssured() {
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

        // Log the full JSON response body for troubleshooting.
        String responseBody = response.asString();
        logger.info("jsonBody: " + responseBody);
    }

    @Test(priority = 5, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldLoginWithRawJsonPayloadUsingRestAssured() {
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
