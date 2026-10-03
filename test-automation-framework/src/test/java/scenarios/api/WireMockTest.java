package scenarios.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
        import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

public class WireMockTest {

    @BeforeClass
    public static void setup() {
        WireMock.configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"id\":1,\"name\":\"Weipeng\"}")));
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testMockedApi() {
        RestAssured
                .given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200);
    }
}
