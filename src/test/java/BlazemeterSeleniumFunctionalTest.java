import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BlazemeterSeleniumFunctionalTest {
	private final static String API_KEY = "4761df1b4dc8ecb8e0a50337";
	private final static String API_SECRET = "d1a41bb1f0c1235fb4215fbaf47566f38bd3fb08ef5fb6d0ff004e09c3744ed4777948eb";
	private final static String BASE = "a.blazemeter.com";
	private final static String curl = String.format("https://%s/api/v4/grid/wd/hub", BASE);
	private static RemoteWebDriver driver;

	@BeforeTest
	public static void setUp() throws MalformedURLException {
		URL url = new URL(curl);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("blazemeter.apiKey", API_KEY);
		capabilities.setCapability("blazemeter.apiSecret", API_SECRET);
		capabilities.setCapability("blazemeter.reportName", "Cineplex Functional Test Scenario 4B");
		capabilities.setCapability("blazemeter.sessionName", "Cineplex 4B Test on Chrome");
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("browserVersion", "69");
		driver = new RemoteWebDriver(url, capabilities);
		String reportURL = String.format("https://%s/api/v4/grid/sessions/%s/redirect/to/report", BASE, driver.getSessionId());
		System.out.println("Report url: " + reportURL);
		openInBrowser(reportURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public static void openInBrowser(String string) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new URI(string));
			} catch (Exception ex) {
				System.out.println("Failed to open in browser");
			}
		}
	}

	@Test(priority = 1)
	public void startTest() throws InterruptedException {
		driver.get("http://uat-www.cineplex.com");
		driver.manage().window().maximize();
		String expectedTitle = "Cineplex.com | Movies, Showtimes, Tickets, Trailers";
		Thread.sleep(5000);
		Assert.assertEquals(driver.getTitle(), expectedTitle);
	}

	@Test(priority = 3)
	public void login() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")).click();
		Thread.sleep(2000);
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		Thread.sleep(500);
	}

	@Test(priority = 5) 
	public void selectTheatre() throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input")).sendKeys("H0" + "\n");
		Thread.sleep(1000);
	}

	@Test(priority = 7)
	public void selectShow() throws InterruptedException {
		driver.findElement(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input")).sendKeys("Deadpool" + "\n");
		Thread.sleep(1000);
	}

	@Test(priority = 9)
	public void selectDate() throws InterruptedException {
		driver.findElement(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[7]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 11)
	public void selectTime() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"l_7997_s_417111_c_0000000001\"]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 13)
	public void clickAdd() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"collapse0000000001\"]/div/div[2]/section/section[1]/div/div/div[2]/div[1]/button")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 15)
	public void clickProceed() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 17)
	public void testCaseTranctionWithoutSeatSelection() throws InterruptedException {
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 19)
	public void gotoHomePage() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/section/div[1]/header/div[2]/div/div[1]/a/img")).click();
		Thread.sleep(10000);
	}

	@Test(priority = 21)
	public void clickProfile() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(@class,'cineplex_connect inline-block')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 23)
	public void signOut() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		Thread.sleep(5000);
	}
}