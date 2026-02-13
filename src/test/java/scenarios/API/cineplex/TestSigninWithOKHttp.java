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

// For Demo purpose only
public class TestSigninWithOKHttp extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(TestSigninWithOKHttp.class.getName());
    private final String connectURL = PropertiesFileReader.getCONNECT_URL();
    private static String sessionToken = "";
    MediaType mediaType = MediaType.parse("application/json");
    JSONParser parser = new JSONParser();

    @Test(priority = 1, groups = {TestGroups.REGRESSION, TestGroups.API, TestGroups.SMOKE})
    public void create_Application_Session_OKhttp_Call() throws Exception {
        RequestBody body = RequestBody
                .create(mediaType,
                        "{\r\n\t\"ApplicationKey\": " +
                                "\"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"\r\n}");
        Request request = new Request.Builder()
                .url(connectURL + "/CreateApplicationSession")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
        sessionToken = jsonBody.get("SessionToken").toString();
        logger.info("jsonBody: " + jsonBody);
        logger.info("sessionToken: " + sessionToken);

        assertEquals(response.code(), 200);
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
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
        logger.info("jsonBody: " + jsonBody);

        assertEquals(response.code(), 200);
    }
}
