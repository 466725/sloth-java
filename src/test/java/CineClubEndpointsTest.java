import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.testng.Assert.*;

//For UAT only, will not work in PROD
public class CineClubEndpointsTest {
	private final String connectURL = "https://apis.cineplex.com/uat/connect/v1";
	private final String subscriptionURL = "https://apis.cineplex.com/uat/connect/v2/subscription";
	private final String chargeBeeURL = "https://cineplex-test.chargebee.com/api/v2/subscriptions";
	private static String sessionToken = "";
	private static String userSessionToken = "";
	private static String userProfileId = "";
	private static String cineClubChargeBeeID = "";
	private static OkHttpClient client;
	MediaType mediaType = MediaType.parse("application/json");
	JSONParser parser = new JSONParser();

	@BeforeClass
	public static void setup() {
		System.out.println("========================Before Class=======================");
		new CookieJar() {
			private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

			@Override
			public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
				cookieStore.put(url.host(), cookies);
			}

			@Override
			public List<Cookie> loadForRequest(HttpUrl url) {
				List<Cookie> cookies = cookieStore.get(url.host());
				return cookies != null ? cookies : new ArrayList<>();
			}
		};
		client = new OkHttpClient.Builder().build();
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("========================After Class========================");
	}
	
	@BeforeTest
	public void startTest() {
		System.out.println("=========================Before Test=======================");
	}

	@AfterTest
	public void endTest() {
		System.out.println("=========================After Test========================");
	}
	
	@Test(priority = 1)
	public void createApplicationSession() throws Exception {
		System.out.println("============================createApplicationSession============================");
		RequestBody body = RequestBody
				.create(mediaType,
						"{\r\n\t\"ApplicationKey\": " +
								"\"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"\r\n}");
		System.out.println(body);
		Request request = new Request.Builder()
				.url(connectURL + "/CreateApplicationSession")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		sessionToken = jsonBody.get("SessionToken").toString();
		System.out.println("sessionToken: " + sessionToken);

		assertEquals(response.code(), 200);
	}

	@Test(priority = 3)
	public void login() throws Exception {
		System.out.println("============================login============================");
		RequestBody body = RequestBody
				.create(mediaType,
						"{\n    \"SessionToken\": " +
								"\"" + sessionToken + "\"," +
								"\n    \"Password\": " +
								"\"Allen@Yonge1300\"," +
								"\n    \"Email\": " +
								"\"weipeng.zheng.ca@gmail.com\"," +
								"\n    \"Source\": " +
								"\"1\"," +
								"\n    \"LanguageType\": " +
								"\"1\"\n}");
		System.out.println(body);
		Request request = new Request.Builder()
				.url(connectURL + "/login")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		userSessionToken = jsonBody.get("UserSessionToken").toString();

		assertEquals(response.code(), 200);
	}

	@Test(priority = 5)
	public void getLoginStatus() throws Exception {
		System.out.println("============================getLoginStatus============================");
		RequestBody body = RequestBody
				.create(mediaType,
						"{\r\n    \"LanguageType\": " +
								"\"1\"," +
								"\r\n    \"SessionToken\": \"" +
								sessionToken + "\"," +
								"\r\n    \"UserSessionToken\": \"" +
								userSessionToken + "\"\r\n}");
		System.out.println(body);
		Request request = new Request.Builder()
				.url(connectURL + "/GetLoginStatus")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		userProfileId = jsonBody.get("UserProfileId").toString();

		assertEquals(response.code(), 200);
	}

	//@Test(priority = 7)
	public void subscriptionEnroll() throws Exception {
		System.out.println("============================subscriptionEnroll============================");
		RequestBody body = RequestBody
				.create(mediaType,
						"{\r\n    \"PaymentCardSortOrder\": " +
								"1," +
								"\r\n    \"PlanId\": " +
								"\"daily1\"\r\n}");
		System.out.println(body);
		Request request = new Request.Builder()
				.url(subscriptionURL + "/enroll")
				.method("POST", body)
				.addHeader("CCToken", userSessionToken)
				.addHeader("Content-Type", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		userProfileId = jsonBody.get("UserProfileId").toString();

		assertEquals(response.code(), 200);
	}

	@Test(priority = 9)
	public void hasSubscription() throws Exception {
		System.out.println("============================hasSubscription============================");
		Request request = new Request.Builder()
				.url(subscriptionURL + "/hasSubscription")
				.method("GET", null)
				.addHeader("CCToken", userSessionToken)
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		assertEquals(response.code(), 200);
	}

	@Test(priority = 11)
	public void activeSubscriptions() throws Exception {
		System.out.println("============================activeSubscriptions============================");
		Request request = new Request.Builder()
				.url(chargeBeeURL + "?customer_id[is]=" + userProfileId + "&status[is]=active")
				.method("GET", null)
				.addHeader("Authorization", "Basic dGVzdF9vNVE4Tk91eEFhaUdTSzJTZ0M4eHQweEpvbW5rU3F3ODo=")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		assertEquals(response.code(), 200);
	}

	@Test(priority = 13)
	public void allSubscriptions() throws Exception {
		System.out.println("============================allSubscriptions============================");
		Request request = new Request.Builder()
				.url(chargeBeeURL + "?customer_id[is]=" + userProfileId)
				.method("GET", null)
				.addHeader("Authorization", "Basic dGVzdF9vNVE4Tk91eEFhaUdTSzJTZ0M4eHQweEpvbW5rU3F3ODo=")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		JSONArray jsonArray = (JSONArray) jsonBody.get("list");
		JSONObject firstArrayObject = (JSONObject) parser.parse(jsonArray.get(0).toString());
		JSONObject subscriptionSection = (JSONObject) parser.parse(firstArrayObject.get("subscription").toString());
		cineClubChargeBeeID = subscriptionSection.get("id").toString();

		assertEquals(response.code(), 200);
	}

	@Test(priority = 15)
	public void cancelSubscriptionRightAway() throws Exception {
		System.out.println("============================cancelSubscriptionRightAway============================");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
				.url(chargeBeeURL + "/" + cineClubChargeBeeID + "/cancel")
				.method("POST", body)
				.addHeader("Authorization", "Basic dGVzdF9vNVE4Tk91eEFhaUdTSzJTZ0M4eHQweEpvbW5rU3F3ODo=")
				.build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body());

		assertEquals(response.code(), 409);
	}
}
