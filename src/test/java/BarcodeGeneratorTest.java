import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//For UAT only, will not work in PROD
public class BarcodeGeneratorTest {
	private static OkHttpClient client;
	//private static MediaType mediaType = MediaType.parse("application/json");

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
	public void barcodeGeneratorTest() throws Exception {
		System.out.println("============================111============================");
		//RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
		  .url("https://cineplex-apis-nonprod.azure-api.net/cpx-barcode-generator-uat/GenerateBarcode?v=4564564136198789456")
		  .method("GET", null)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Ocp-Apim-Subscription-Key", "5c8c64aa27dc4384b59bf3ebf5547895")
		  .build();
		
		Response response = client.newCall(request).execute();
		
		assertTrue(response.code() == 200);
	}
}
