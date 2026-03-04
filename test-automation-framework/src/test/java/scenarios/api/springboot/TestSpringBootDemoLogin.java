package scenarios.api.springboot;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import testcases.ApiTestCase;
import testcases.TestGroups;

import static io.restassured.RestAssured.given;

public class TestSpringBootDemoLogin extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSpringBootDemoLogin.class.getName());
    private static final String baseUrl = System.getProperty("spring.boot.demo.base.url", "http://localhost:8080");

    @Test(priority = 1, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API, TestGroups.INTEGRATION})
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

    @Test(priority = 2, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API, TestGroups.INTEGRATION})
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
