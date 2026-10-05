package api.examples;

import com.github.tomakehurst.wiremock.WireMockServer;
import core.ApiTestCase;
import core.TestGroups;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WireMockDemoTest extends ApiTestCase {
    private static final String USERS_ENDPOINT = "/users/1";
    private static final String USER_RESPONSE = "{\"id\":1,\"name\":\"Weipeng\"}";

    private WireMockServer wireMockServer;

    @BeforeClass(alwaysRun = true)
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(0);
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo(USERS_ENDPOINT))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(USER_RESPONSE)));

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = wireMockServer.port();
    }

    @AfterClass(alwaysRun = true)
    public void stopWireMockServer() {
        RestAssured.reset();
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test(priority = 1, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void shouldReturnMockedUser() {
        given()
                .when()
                .get(USERS_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("name", equalTo("Weipeng"));
    }
}
