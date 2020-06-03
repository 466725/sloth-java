import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//For PROD only, will not work in UAT
public class TicketTransactionLoadTestCSV {
	private static String locationID = null;
	private static String vistaSessionID = null;
	private static String itSessionID = null;
	private static String itTicketTypeID = null;
	private static String ticketTypeCode = null;
	private static String ticketCode = null;
	private static String connectBaseURL = "https://connect.cineplex.com/ClientServices/CineplexClientServicesWeb";
	private static String cotBaseURL = "https://onlineticketing.cineplex.com";
	private static String sessionToken = "";
	private static String userSessionToken = "";
	private static String transactionID = "";
	private static String aspCookie = "";
	private static OkHttpClient client;
	private static MediaType mediaType = MediaType.parse("application/json");
	private static JSONParser parser = new JSONParser();

	@BeforeClass
	public static void setup() {
		System.out.println("========================Before Class=======================");
		client = new OkHttpClient.Builder().build();
	}
	
	@AfterClass
	public static void tearDown() {
		System.out.println("========================After Class========================");
	}
	
	@BeforeTest
	public static void startTest() {
		System.out.println("=========================Before Test=======================");
	}

	@AfterTest
	public static void endTest() {
		System.out.println("=========================After Test========================");
	}
	
	@Test(priority = 1)
	public static void create_session_token() throws Exception {
		System.out.println("============================111============================");
		RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"ApplicationKey\": \"9fbcb70c-8bcd-43eb-930f-d99968b4561e\"\r\n}");
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
		
		response.body().close();
	}

	@Test(priority = 3)
	public static void login() throws Exception {
		System.out.println("============================222============================");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"SessionToken\": \"" + sessionToken + "\",\n    \"Password\": \"Cineplex123\",\n    \"Email\": \"cpxapitester@gmail.com\",\n    \"Source\": \"1\",\n    \"LanguageType\": \"1\"\n}");
		Request request = new Request
				.Builder()
				.url(connectBaseURL + "/Login")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();
		
		response.body().close();
	}

	@Test(priority = 5)
	public static void create_ticket_transaction() throws Exception {
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
		
		response.body().close();
	}
	
	@Test(priority = 9)
	public static void ticket_cart() throws Exception {
		System.out.println("============================444============================");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/" + transactionID)
				.method("GET", null)
				.addHeader("Cookie", "CCTOKEN=" + userSessionToken)
				.build();

		Response response = client.newCall(request).execute();
		
		for(int i = 0; i < response.headers().size(); i++) {
			if(response.headers().value(i).contains("ASP.NET_SessionId="))
				aspCookie = response.headers().value(i);
		}
		
		response.body().close();
	}

	@Test(priority = 11)
	public static void ticket_cart_proceed() throws Exception {
		System.out.println("============================555============================");
		String jsonBody = "[\r\n    {"
				+ "\r\n        \"ITTicketTypeID\": " 
				+ itTicketTypeID
				+ ",\r\n        \"TicketTypeCode\": " 
				+ ticketTypeCode
				+ ",\r\n        \"TicketCode\": " 
				+ ticketCode
				+ ",\r\n        \"Quantity\": 1"
				+ ",\r\n        \"ITSessionId\": " 
				+ itSessionID
				+ "\r\n    }\r\n]";
		RequestBody body = RequestBody.create(mediaType, jsonBody);
		
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/TicketCart/Proceed/" + transactionID)
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Cookie", aspCookie)
				.build();
		
		Response response = client.newCall(request).execute();
		
		response.body().close();
	}

	@Test(priority = 13)
	public static void seats() throws Exception {
		System.out.println("============================666============================");
		Request request = new Request
				.Builder()
				.url(cotBaseURL + "/Seats/" + transactionID)
				.method("GET", null)
				.addHeader("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();
		
		response.body().close();
	}

	@Test(priority = 15)
	public static void ticket_cart_cancel() throws Exception {
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
		
		response.body().close();
	}
	
	public static void main(String[] args) {
		System.out.println("Let's get csv file handled! ");
		
		File file = new File("ticketing_flow_data_prod.csv");
		String filePath = file.getAbsolutePath();
		Iterable<CSVRecord> records;
		try {
			Reader in = new FileReader(filePath);
			records = CSVFormat.EXCEL.parse(in);
			int index = 0;
			setup();
			startTest();
			for (CSVRecord record : records) {
				locationID = record.get(1);
				vistaSessionID = record.get(2);
				itSessionID = record.get(3);
				itTicketTypeID = record.get(4);
				ticketTypeCode = record.get(5);
				ticketCode = record.get(6);
				index = index + 1;
				System.out.println("++++++++++++++++++++++");
				System.out.println("++++++++++++++++++++++");
				System.out.println("Running number: " + index);
				System.out.println("locationID: " + locationID);
				System.out.println("vistaSessionID: " + vistaSessionID);
				System.out.println("itSessionID: " + itSessionID);
				System.out.println("itTicketTypeID: " + itTicketTypeID);
				System.out.println("ticketTypeCode: " + ticketTypeCode);
				System.out.println("ticketCode: " + ticketCode);
				System.out.println("++++++++++++++++++++++");
				System.out.println("++++++++++++++++++++++");
				try {
					create_session_token();
					login();
					create_ticket_transaction();
					ticket_cart();
					ticket_cart_proceed();
					seats();
					//ticket_cart_cancel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			endTest();
			tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
