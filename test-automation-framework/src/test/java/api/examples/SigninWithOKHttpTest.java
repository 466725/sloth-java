package api.examples;

import config.PropertiesFileReader;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;
import core.ApiTestCase;
import core.TestGroups;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

// Demo API tests for OkHttp usage.
public class SigninWithOKHttpTest extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninWithOKHttpTest.class.getName());
    private static String sessionToken = "";
    private final String connectURL = PropertiesFileReader.getCONNECT_URL();
    MediaType mediaType = MediaType.parse("application/json");
    JSONParser parser = new JSONParser();

    @Test(priority = 1, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.INTEGRATION})
    public void shouldCreateApplicationSessionWithOkHttp() throws Exception {
        String payload = """
                {
                  "ApplicationKey": "2939bf3b-6c04-4c7b-bcfd-bb590e0016fa"
                }
                """;
        RequestBody body = RequestBody.create(payload, mediaType);
        Request request = new Request.Builder()
                .url(connectURL + "/CreateApplicationSession")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            logger.info("create_Application_Session_OKhttp_Call status: " + response.code());
            logger.info("create_Application_Session_OKhttp_Call body: " + responseBody);
            assertEquals(response.code(), 200);
            assertTrue(responseBody.trim().startsWith("{"), "Expected JSON object response but got: " + responseBody);

            JSONObject jsonBody = (JSONObject) parser.parse(responseBody);
            sessionToken = jsonBody.get("SessionToken").toString();
            logger.info("jsonBody: " + jsonBody);
            logger.info("sessionToken: " + sessionToken);
        }
    }

    // Login call using test credentials.
    @Test(priority = 3, groups = {TestGroups.API, TestGroups.SMOKE, TestGroups.INTEGRATION})
    public void shouldLoginWithOkHttp() throws Exception {
        String payload = """
                {
                  "SessionToken": "%s",
                  "Password": "password",
                  "Email": "user.name.ca@gmail.com",
                  "Source": "1",
                  "LanguageType": "1"
                }
                """.formatted(sessionToken);
        RequestBody body = RequestBody.create(payload, mediaType);
        Request request = new Request.Builder()
                .url(connectURL + "/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            logger.info("login_OKhttp_Call status: " + response.code());
            logger.info("login_OKhttp_Call body: " + responseBody);
            assertEquals(response.code(), 200);
            assertTrue(responseBody.trim().startsWith("{"), "Expected JSON object response but got: " + responseBody);

            JSONObject jsonBody = (JSONObject) parser.parse(responseBody);
            logger.info("jsonBody: " + jsonBody);
        }
    }
}
