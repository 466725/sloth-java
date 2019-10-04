import static org.junit.Assert.assertTrue;

//import org.apache.log4j.xml.DOMConfigurator;
//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CineplexPerfTestScenario4BUAT {
	public WebDriver driver = new FirefoxDriver();
	WebDriverWait wait = new WebDriverWait(driver, 20, 100);
//	static Logger log = Logger.getLogger(CineplexPerfTest1junit.class.getName());

	@Before
	public void setUp() throws Exception {
		// Launch the Home Page in Max window
		driver.manage().window().maximize();
		driver.get("https://uat-www.cineplex.com");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
//		DOMConfigurator.configure("log4j.xml");
	}

	@Test
	public void startTest() throws Exception {
		// Start the main test
		assertTrue(driver.getTitle().contains("Cineplex"));

		// Click LOG IN button
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
		driver.findElement(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")).click();

		// Switch to Login Modal frame and Log in
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtEmailAddress']")));
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		Thread.sleep(2000); // Find something else

		// Switch back to main frame
		driver.switchTo().defaultContent();

		// Select Theater
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']"))).click();
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("H0" + "\n");

		// Select Movie
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']"))).click();
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("Mamma" + "\n");

		// Select Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']"))).click();
		driver.findElement(By.xpath("//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[2]")).click();

		// Select Time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"quicktickets-showtime-selection-wrapper\"]")));
		driver.findElement(By.xpath("//*[@id=\"l_7997_s_417071_c_0000000001\"]")).sendKeys(Keys.ENTER);// .click();

		/*
		 * Tickets Page Click Add Ticket Button and click Proceed
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"collapse0000000001\"]/div/div[2]/section/section[1]/div/div/div[2]/div[1]/button"))).click();
		assertTrue(driver.getTitle().contains("Ticket Cart"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@data-bind,'Proceed')]"))).click();

		// Seat Map
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"seatmap\"]/h3")));
//		assertTrue(driver.getTitle().contains("Select Seats"));
//		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

		// Donation Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addDonationButton\"]")));
		assertTrue(driver.getTitle().contains("Extras"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

		// Payment Methods Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='ADD OTHER PAYMENT METHOD']")));
		assertTrue(driver.getTitle().contains("Payment Options"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		
		// Payment Page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-xs-6 text-left']//img[@alt='Cineplex Logo']")));
		assertTrue(driver.getTitle().contains("Payment"));

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}