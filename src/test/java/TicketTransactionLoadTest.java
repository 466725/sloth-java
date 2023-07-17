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

//For UAT only, will not work in PROD
public class TicketTransactionLoadTest {
	private String connectBaseURL = "https://apis.cineplex.com/uat/connect/v1";
	private static String sessionToken = "";
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
		RequestBody body = RequestBody.create(mediaType, "{\n\t\"ApplicationKey\": \"2939bf3b-6c04-4c7b-bcfd-bb590e0016fa\"\n}");
		Request request = new Request.Builder()
				  .url("https://apis.cineplex.com/uat/connect/v1/CreateApplicationSession")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Cookie", "Cineplex MVC Sandbox_Language=en-us; incap_ses_1292_2293381=2N2sSpMCeHZjQ8Tn4RvuERuStGQAAAAAC/Z4mJWYBk9u56PXMyqT+A==; nlbi_2293381=I4Wja4543xqh5fHj1FeBbgAAAAB/+r9DI3IiCiEuNOSNosr7; visid_incap_2293202=Vk2g+Ip2TviRNPtO4x9mSBRr+mIAAAAAQUIPAAAAAADZPWuoaVLr1o34OrUm1v/H; visid_incap_2293254=VO8KkKtpQBihZtakfmdclvCJRmQAAAAAQUIPAAAAAAD9USJTuk/zYxTyQ8bIq2BU; visid_incap_2293381=DLktviNvRseaJ8yqJjMKOOyJRmQAAAAAQUIPAAAAAAD/VRvrMPaJxpBgOmGq82Pj; visid_incap_2293405=Y++4xRPvRL6LJh7GZ1LY4u0TUWQAAAAAQUIPAAAAAACB2/Tf3z0PmOqxwsgOOhDp; visid_incap_2306869=fuZe7W/eTEObqFBNH4C4Fo8+/WMAAAAAQUIPAAAAAADESQL9ulm2pURALKg3iCpD; visid_incap_2350392=EWPxKU+jQ0KGsLGANbh7jq7ogWQAAAAAQUIPAAAAAADJx7tDcP4vCJwxVkW8Djxl")
				  .build();
		
		Response response = client.newCall(request).execute();

		JSONObject jsonBody = (JSONObject) parser.parse(response.body().string());
		sessionToken = jsonBody.get("SessionToken").toString();
		System.out.println("sessionToken: " + sessionToken);
		
		assertTrue(response.code() == 200);
	}
}
