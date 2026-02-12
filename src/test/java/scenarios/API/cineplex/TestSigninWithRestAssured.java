package scenarios.API.cineplex;

import config.PropertiesFileReader;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
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
    private final String barcodeBaseURL = PropertiesFileReader.getBarcodeBaseURL();
    private static String sessionToken = "";

    @Test(priority = 5, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API})
    public void create_Application_Session_RestAssured_Call() throws Exception {
        Response response =
                (Response) given()
                        .contentType("application/json")
                        .body("{\"ApplicationKey\": \"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"}")
                        .when()
                        .post(barcodeBaseURL + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                        .then()
                        .statusCode(200);
        // Retrieve response headers
        Headers headers = response.headers();
        logger.info("Response Headers: " + headers);
        // Retrieve response body
        String responseBody = response.getBody().asString();
        logger.info("Response Body: " + responseBody);
    }

    @Test(priority = 7, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API})
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
                        .body(payload)
                        .when()
                        .post(connectURL + "/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // If you want to log the JSON response (similar to your logger.info("jsonBody: ..."))
        String responseBody = response.asString();
        System.out.println("jsonBody: " + responseBody);
    }

    @Test(priority = 9, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API})
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

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(connectURL + "/login")
                .then()
                .statusCode(200);
    }
}
