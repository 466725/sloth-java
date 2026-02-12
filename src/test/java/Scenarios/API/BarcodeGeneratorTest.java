package Scenarios.API;

import config.PropertiesFileReader;
import okhttp3.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

//For UAT only, will not work in PROD
public class BarcodeGeneratorTest {
    private static OkHttpClient client;
    private static String apiManagerSubscriptionKey = null;
    //private static MediaType mediaType = MediaType.parse("application/json");

    @BeforeClass
    public static void setup() {
        System.out.println("========================Before Class=======================");

        // Read API manager subscription key from os environment variable
        // For security purposes, a subscription key not supposed to be hardcoded
        if (System.getenv("API_MANAGER_SUBSCRIPTION_KEY") != null)
            apiManagerSubscriptionKey = System.getenv("API_MANAGER_SUBSCRIPTION_KEY");
        else
            apiManagerSubscriptionKey = "5c8c64aa27dc4384b59bf3ebf5547895"; // For testing purposes only
        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
        client = new OkHttpClient.Builder().build();
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("========================After Class========================");
    }

    @Test(priority = 1)
    public void barcodeGeneratorTest() throws Exception {
        //RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(PropertiesFileReader.getBarcodeBaseURL() + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 200);
    }

    // Redo barcodeGeneratorRestAssuredTest() with Rest Assured
    @Test(priority = 3)
    public void barcodeGeneratorRestAssuredTest() throws Exception {
        given()
                .contentType("application/json")
                .header("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .when()
                .get(PropertiesFileReader.getBarcodeBaseURL() + "cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
                .then()
                .statusCode(200);
    }
}
