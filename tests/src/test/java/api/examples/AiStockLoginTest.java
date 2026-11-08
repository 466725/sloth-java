package api.examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.ApiTestCase;
import core.TestGroups;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static io.restassured.RestAssured.given;

public class AiStockLoginTest extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(AiStockLoginTest.class.getName());
    private static final String baseUrl = System.getProperty("ai.stock.base.url", "http://localhost:8080");

    @BeforeClass(alwaysRun = true)
    public void verifyAiStockIsAvailable() {
        super.beforeClass();
        if (!isAiStockUp()) {
            throw new SkipException(
                    "Skipping AiStockLoginTest because ai-stock is not reachable at " + baseUrl);
        }
    }

    private boolean isAiStockUp() {
        HttpURLConnection connection = null;
        try {
            URL url = URI.create(baseUrl + "/actuator/health").toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            int status = connection.getResponseCode();
            logger.info("ai-stock health endpoint status: " + status);
            return status >= 200 && status < 300;
        } catch (Exception ex) {
            logger.warn("ai-stock health check failed: " + ex.getMessage());
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Test(priority = 1, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldLoginSuccessfullyWithValidCredentials() {
        Response response =
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("username", "username")
                        .formParam("password", "password")
                        .when()
                        .post(baseUrl + "/api/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        response.then().body("message", org.hamcrest.Matchers.equalTo("login successful"));
        logger.info("Valid login response: " + response.asString());
    }

    @Test(priority = 2, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldReturnWrongCredentialsWithInvalidCredentials() {
        Response response =
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("username", "wrong-username")
                        .formParam("password", "wrong-password")
                        .when()
                        .post(baseUrl + "/api/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        response.then().body("message", org.hamcrest.Matchers.equalTo("wrong credentials"));
        logger.info("Invalid login response: " + response.asString());
    }
}
