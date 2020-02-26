import static org.testng.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketTransactionLoadTest {
	private String connectBaseURL = "https://uat-connect.cineplex.com/ClientServices/CineplexClientServicesWeb";
	private String cotBaseURL = "https://uat-onlineticketing.cineplex.com";
	private String userName = "cpxapitester@gmail.com";
	private String password = "Cineplex123";
	private String vistaSessionID = "217343";
	private String locationID = "7995";
	private String sessionToken = "";
	private String userProfileGUID = "";
	private String userSessionToken = "";
	private String transactionID = "";
	private OkHttpClient client = new OkHttpClient().newBuilder().build();
	private MediaType mediaType = MediaType.parse("application/json");
	private JSONParser parser = new JSONParser();

	@Before
	public void setup() {
		System.out.println(" ");
		System.out.println("===========================BEGIN===========================");
		System.out.println(" ");
	}

	@After
	public void tearDown() {
		System.out.println(" ");
		System.out.println("============================END============================");
		System.out.println(" ");
	}

	@Test
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

	@Test
	public void login() throws Exception {
		System.out.println("============================222============================");
		RequestBody body = RequestBody
				.create(mediaType, "{\n    \"SessionToken\": " 
						+ sessionToken 
						+ ",\n    \"Password\": " 
						+ password 
						+ ",\n    \"Email\": " 
						+ userName 
						+ ",\n    \"Source\": \"1\",\n    \"LanguageType\": \"1\"\n}");
		Request request = new Request
				.Builder()
				.url(connectBaseURL + "/Login")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		userProfileGUID = jsonBody.get("UserProfileGuid").toString();
		userSessionToken = jsonBody.get("UserSessionToken").toString();
		System.out.println("userProfileGUID: " + userProfileGUID);
		System.out.println("userSessionToken: " + userSessionToken);

		assertTrue(!userProfileGUID.equalsIgnoreCase("00000000-0000-0000-0000-000000000000"));
		assertTrue(!userSessionToken.equalsIgnoreCase("00000000-0000-0000-0000-000000000000"));
		assertTrue(response.code() == 200);
	}

	@Test
	public void ticket_transaction_create() throws Exception {
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

		assertTrue(response.code() == 200);
	}

	@Test
	public void ticket_transaction_get_ticket_cart() throws Exception {
		System.out.println("============================444============================");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/" + transactionID)
				.method("GET", null)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();
		
		Response response = client.newCall(request).execute();
		
		assertTrue(response.code() == 200);
	}

	@Test
	public void ticket_transaction_get_ticket_cart_proceed() throws Exception {
		System.out.println("============================555============================");
		RequestBody body = RequestBody
				.create(mediaType, "[\r\n\t{"
						+ "\r\n        \"ITTicketTypeID\": 2150991,"
						+ "\r\n        \"TicketTypeCode\": \"0946\","
						+ "\r\n        \"TicketCode\": \"VY\","
						+ "\r\n        \"Quantity\": 1,"
						+ "\r\n        \"ITSessionId\": 13215604"
						+ "\r\n    }\r\n]");
		
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/Proceed/916cb00e-c00a-4336-b217-3d62d3d530e7")
				.method("POST", body)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();

		assertTrue(response.code() == 200);
	}

	@Test
	public void ticket_transaction_get_ticket_cart_seats() throws Exception {
		System.out.println("============================666============================");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/Seats/" + transactionID)
				.method("GET", null)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();
		
		Response response = client.newCall(request).execute();
		
		assertTrue(response.code() == 200);
	}

	@Test
	public void ticket_transaction_void() throws Exception {
		System.out.println("============================777============================");
		RequestBody body = RequestBody
				.create(null, new byte[0]);
		
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/CineplexTicketingBase/CreateTicketTransaction/" + transactionID)
				.method("POST", body)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();
		
		Response response = client.newCall(request).execute();
		
		assertTrue(response.code() == 200);
	}
}
