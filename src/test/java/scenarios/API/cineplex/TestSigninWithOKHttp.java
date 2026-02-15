package scenarios.api.cineplex;

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
import testcases.ApiTestCase;
import testcases.TestGroups;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

// For Demo purpose only
public class TestSigninWithOKHttp extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSigninWithOKHttp.class.getName());
    private final String connectURL = PropertiesFileReader.getCONNECT_URL();
    private static String sessionToken = "";
    MediaType mediaType = MediaType.parse("application/json");
    JSONParser parser = new JSONParser();

    @Test(priority = 1, groups = {TestGroups.REGRESSION, TestGroups.API, TestGroups.SMOKE})
    public void create_Application_Session_OKhttp_Call() throws Exception {
        logger.info("API manager subscription key is: " + apiManagerSubscriptionKey);
        RequestBody body = RequestBody
                .create(mediaType,
                        "{\r\n\t\"ApplicationKey\": " +
                                "\"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"\r\n}");
        Request request = new Request.Builder()
                .url(connectURL + "/CreateApplicationSession")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        Response response = client.newCall(request).execute();
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

    // Login with the wrong username and password
    @Test(priority = 3, groups = {TestGroups.REGRESSION, TestGroups.API, TestGroups.SMOKE})
    public void login_OKhttp_Call() throws Exception {
        RequestBody body = RequestBody
                .create(mediaType,
                        "{\n    \"SessionToken\": " +
                                "\"" + sessionToken + "\"," +
                                "\n    \"Password\": " +
                                "\"password\"," +
                                "\n    \"Email\": " +
                                "\"user.name.ca@gmail.com\"," +
                                "\n    \"Source\": " +
                                "\"1\"," +
                                "\n    \"LanguageType\": " +
                                "\"1\"\n}");
        Request request = new Request.Builder()
                .url(connectURL + "/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Ocp-Apim-Subscription-Key", apiManagerSubscriptionKey)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body() != null ? response.body().string() : "";
        logger.info("login_OKhttp_Call status: " + response.code());
        logger.info("login_OKhttp_Call body: " + responseBody);
        assertEquals(response.code(), 200);
        assertTrue(responseBody.trim().startsWith("{"), "Expected JSON object response but got: " + responseBody);

        JSONObject jsonBody = (JSONObject) parser.parse(responseBody);
        logger.info("jsonBody: " + jsonBody);
    }
}
