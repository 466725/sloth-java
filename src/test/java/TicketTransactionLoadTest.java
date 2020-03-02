import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class TicketTransactionLoadTest {
	private String connectBaseURL = "https://uat-connect.cineplex.com/ClientServices/CineplexClientServicesWeb";
	private String cotBaseURL = "https://uat-onlineticketing.cineplex.com";
	private String vistaSessionID = "217343";
	private String locationID = "7995";
	private static String sessionToken = "";
	private static String userProfileGUID = "";
	private static String userSessionToken = "";
	private static String transactionID = "";
	private static String aspCookie = "";
	private static OkHttpClient client;
	private static MediaType mediaType = MediaType.parse("application/json");
	private static JSONParser parser = new JSONParser();

	@BeforeClass
	public static void setup() {
		System.out.println("========================Before Class=======================");

		@SuppressWarnings("unused")
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
		/*
		client = new OkHttpClient.Builder().cookieJar(cookieJar).build();
		client = new OkHttpClient.Builder().cookieJar(new TicketTransactionCookie()).build();
		*/
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
	public void create_session_token() throws Exception {
		System.out.println("============================111============================");
		RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"ApplicationKey\": \"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"\r\n}");
		Request request = new Request
				.Builder()
				.url(connectBaseURL + "/CreateApplicationSession")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		sessionToken = jsonBody.get("SessionToken").toString();
		System.out.println("sessionToken: " + sessionToken);
		
		assertTrue(response.code() == 200);
	}

	@Test(priority = 3)
	public void login() throws Exception {
		System.out.println("============================222============================");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"SessionToken\": \"" + sessionToken + "\",\n    \"Password\": \"Cineplex123\",\n    \"Email\": \"cpxapitester@gmail.com\",\n    \"Source\": \"1\",\n    \"LanguageType\": \"1\"\n}");
		System.out.println(body.toString());
		System.out.println("{\n    \"SessionToken\": \"" + sessionToken + "\",\n    \"Password\": \"Cineplex123\",\n    \"Email\": \"cpxapitester@gmail.com\",\n    \"Source\": \"1\",\n    \"LanguageType\": \"1\"\n}");
		Request request = new Request
				.Builder()
				.url(connectBaseURL + "/Login")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		System.out.println(request.toString());
		
		Response response = client.newCall(request).execute();

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		System.out.println(jsonBody);
		userProfileGUID = jsonBody.get("UserProfileGuid").toString();
		userSessionToken = jsonBody.get("UserSessionToken").toString();
		System.out.println("userProfileGUID: " + userProfileGUID);
		System.out.println("userSessionToken: " + userSessionToken);

		assertTrue(!userProfileGUID.equalsIgnoreCase("00000000-0000-0000-0000-000000000000"));
		assertTrue(!userSessionToken.equalsIgnoreCase("00000000-0000-0000-0000-000000000000"));
		assertTrue(response.code() == 200);
	}

	@Test(priority = 5)
	public void create_ticket_transaction() throws Exception {
		System.out.println("============================333============================");
		RequestBody body = RequestBody
				.create(mediaType, "{\n    \"VISTASessionId\": \"" 
						+ vistaSessionID 
						+ "\",\n    \"LocationId\": \"" 
						+ locationID 
						+ "\",\n    \"ClientGuid\": null\n}");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/CineplexTicketingBase/CreateTicketTransaction")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		transactionID = jsonBody.get("TransactionUid").toString();
		System.out.println("transactionID: " + transactionID);
		System.out.println("Status: " + jsonBody.get("Status"));

		assertTrue(!transactionID.equalsIgnoreCase("00000000-0000-0000-0000-000000000000"));
		assertTrue(jsonBody.get("Status").toString().equals("1"));
		assertTrue(response.code() == 200);
	}
	
	@Test(priority = 9)
	public void ticket_cart() throws Exception {
		System.out.println("============================444============================");
		System.out.println("transactionID: " + transactionID);
		System.out.println("userSessionToken: " + userSessionToken);
		
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/" + transactionID)
				.method("GET", null)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();
		System.out.println(request.url());
		System.out.println(request.headers().toString());

		Response response = client.newCall(request).execute();
		
		System.out.println(response.headers().toString());
		System.out.println(response.body().string());
		
		for(int i = 0; i < response.headers().size(); i++) {
			System.out.println(response.headers().value(i));
			if(response.headers().value(i).contains("ASP.NET_SessionId=")) {
				System.out.println(response.headers().value(i));
				aspCookie = response.headers().value(i);
			}
		}
		
		assertTrue(response.code() == 200);
	}

	@Test(priority = 11)
	public void ticket_cart_proceed() throws Exception {
		System.out.println("============================555============================");
		String jsonBody = "[\r\n    {"
				+ "\r\n        \"ITTicketTypeID\": 2150991,"
				+ "\r\n        \"TicketTypeCode\": \"0946\","
				+ "\r\n        \"TicketCode\": \"VY\","
				+ "\r\n        \"Quantity\": 1,"
				+ "\r\n        \"ITSessionId\": 13215604"
				+ "\r\n    }\r\n]";
		System.out.println(jsonBody);
		RequestBody body = RequestBody.create(mediaType, jsonBody);
		
		System.out.println(aspCookie);
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/Proceed/" + transactionID)
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie", aspCookie)
				.build();
		
		System.out.println(request.url());
		System.out.println(request.headers().toString());
		System.out.println(request.body().toString());
		
		Response response = client.newCall(request).execute();
		
		System.out.println(response.body().string());
		System.out.println(response.headers().toString());
		System.out.println(response.code());
		
		assertTrue(response.code() == 200);
	}

	@Test(priority = 13)
	public void seats() throws Exception {
		System.out.println("============================666============================");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/Seats/" + transactionID)
				.method("GET", null)
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie", aspCookie)
				.build();
		
		System.out.println(request.url());
		System.out.println(request.headers().toString());
		
		Response response = client.newCall(request).execute();
		
		System.out.println(response.body().string());
		System.out.println(response.headers().toString());
		System.out.println(response.code());
		
		assertTrue(response.code() == 200);
	}

	@Test(priority = 15)
	public void ticket_cart_cancel() throws Exception {
		System.out.println("============================777============================");
		RequestBody body = RequestBody
				.create(null, new byte[0]);
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/CineplexTicketingBase/CreateTicketTransaction/" + transactionID)
				.method("POST", body)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();
		
		System.out.println(request.url());
		System.out.println(request.headers().toString());
		System.out.println(request.body().toString());
		
		Response response = client.newCall(request).execute();
		
		System.out.println(response.body().string());
		System.out.println(response.headers().toString());
		System.out.println(response.code());
		
		assertTrue(response.code() == 200);
	}
}
