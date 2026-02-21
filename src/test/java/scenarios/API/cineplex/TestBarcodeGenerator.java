package scenarios.api.cineplex;

import config.PropertiesFileReader;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.Test;
import testcases.ApiTestCase;
import testcases.TestGroups;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

// UAT-only endpoint; this test is not expected to work in PROD.
public class TestBarcodeGenerator extends ApiTestCase {

    @Test(priority = 3, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API})
    public void generate_Barcode_OKhttp_Call() throws Exception {
        logger.info("API manager subscription key is: " + apiManagerSubscriptionKey);
        Request request = new Request.Builder()
                .url(PropertiesFileReader.getBarcodeBaseURL() + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 200);
    }

    // Equivalent barcode generation call implemented with Rest Assured.
    @Test(priority = 5, groups = {TestGroups.REGRESSION, TestGroups.SMOKE, TestGroups.API})
    public void generate_Barcode_RestAssured_Call() throws Exception {
        given()
                .contentType("application/json")
                .header("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .when()
                .get(PropertiesFileReader.getBarcodeBaseURL() + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                .then()
                .statusCode(200);
    }
}
