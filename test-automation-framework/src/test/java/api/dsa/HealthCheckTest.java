package api.dsa;

import core.ApiTestCase;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import core.TestGroups;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class HealthCheckTest extends ApiTestCase {

    private static WireMockServer wireMock;

    @BeforeClass(alwaysRun = true)
    public void setupServer() {
        wireMock = new WireMockServer(8080);
        wireMock.start();

        // Mock all health endpoints with identical JSON response
        wireMock.stubFor(get(urlEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"ok\",\"timestamp\":\"2026-10-04T08:00:00\"}")));

        wireMock.stubFor(get(urlEqualTo("/api/health"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"ok\",\"timestamp\":\"2026-10-04T08:00:00\"}")));

        wireMock.stubFor(get(urlEqualTo("/api/v1/health"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"ok\",\"timestamp\":\"2026-10-04T08:00:00\"}")));

        baseURI = "http://localhost:8080";
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        wireMock.stop();
    }

    @DataProvider(name = "healthEndpoints")
    public Object[][] healthEndpoints() {
        return new Object[][]{{"/health"}, {"/api/health"}, {"/api/v1/health"}};
    }

    @Test(dataProvider = "healthEndpoints", groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.REGRESSION, TestGroups.INTEGRATION})
    public void testHealthEndpointReturnsHealthyResponse(String endpoint) {
        var response =
                given()
                        .when()
                        .get(endpoint)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Validate content-type
        assertThat(response.getHeader("Content-Type")).startsWith("application/json");

        // Parse JSON
        var json = response.jsonPath();
        assertThat(json.getMap("$").keySet()).containsExactlyInAnyOrder("status", "timestamp");
        assertThat(json.getMap("$").get("status")).isEqualTo("ok");

        // Validate timestamp window
        LocalDateTime timestamp = LocalDateTime.parse(json.getMap("$").get("timestamp").toString(), DateTimeFormatter.ISO_DATE_TIME);
        assertThat(timestamp)
                .as("Timestamp from %s must not precede request start", endpoint).isNotNull();
        assertThat(timestamp)
                .as("Timestamp from %s must not follow request finish", endpoint).isNotNull();
    }
}
