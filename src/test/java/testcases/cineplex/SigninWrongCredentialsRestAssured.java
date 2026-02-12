package testcases.cineplex;

import config.PropertiesFileReader;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;
import testcases.ApiTestCase;

import static io.restassured.RestAssured.given;

// For Demo purpose only
public class SigninWrongCredentialsRestAssured extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninWrongCredentialsRestAssured.class.getName());
    private final String barcodeBaseURL = PropertiesFileReader.getBarcodeBaseURL();
    private static String sessionToken = "";
    JSONParser parser = new JSONParser();

    // Redo createApplicationSession() with Rest Assured
    // @Test(priority = 5)
    public void createApplicationSessionRestAssuredTest() throws Exception {
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

    // Redo login() with Rest Assured
    // @Test(priority = 7)
    public void loginRestAssuredTest() throws Exception {
        Response response =
                (Response) given()
                        .contentType("application/json")
                        .body("{\"ApplicationKey\": \"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"}")
                        .when()
                        .get(barcodeBaseURL + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                        .then()
                        .statusCode(200);
    }
}
