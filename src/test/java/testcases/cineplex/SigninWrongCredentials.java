package testcases.cineplex;

import okhttp3.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;
import testcases.ApiTestCase;

import static org.testng.Assert.assertEquals;

// For Demo purpose only
public class SigninWrongCredentials extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SigninWrongCredentials.class.getName());
    private final String connectURL = "https://apis.cineplex.com/uat/connect/v1";
    private static String sessionToken = "";
    MediaType mediaType = MediaType.parse("application/json");
    JSONParser parser = new JSONParser();

    @Test(priority = 1)
    public void createApplicationSession() throws Exception {
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
    @Test(priority = 3)
    public void login() throws Exception {
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
