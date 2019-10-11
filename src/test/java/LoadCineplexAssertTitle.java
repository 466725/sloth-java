import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoadCineplexAssertTitle {
	private static WebDriver driver;
	WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 120, 250);
		driver.get("https://uat-www.cineplex.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
	}

	@Test
	public void startTest() throws Exception {
		assertTrue(driver.getTitle().contains("Cineplex"));
		// Click LOG IN button
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")))
				.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bootstrapConnectModalDialog']//button[@class='close']")));

		// Switch to Login Modal frame and Log in
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtEmailAddress']")));
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("userInfo")));

		// ********************************************** NEW CODE
		// ***********************************************

		// Select Theater
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']")))
				.click();
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("H0" + "\n");
		Thread.sleep(1000);
		// Select Movie
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input")))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("Ant-Man" + "\n");

		// ********************************************** NEW CODE
		// ***********************************************

		// Select Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']")))
				.click();
		driver.findElement(By.xpath(
				"//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[2]"))
				.click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='quick-tickets-theatre-showtimes']/div[1]/div[2]")));
		driver.findElement(By.xpath("//*[@id=\"l_7997_s_417887_c_0000000001\"]")).sendKeys(Keys.ENTER);// .click();

		/*
		 * Tickets Page Click Add Ticket Button and click Proceed
		 */
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"collapse0000000001\"]/div/div[2]/section/section/div/div/div[2]/div[1]/button"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'add-qty-btn')]")))
				.click();
		assertTrue(driver.getTitle().contains("Ticket Cart"));
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@data-bind,'Proceed')]")));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

		// Seat Map
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"seatmap\"]/h3")));
		assertTrue(driver.getTitle().contains("Select Seats"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

		// Donation Page
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addDonationButton\"]")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addSuperTicketButton\"]")));

		assertTrue(driver.getTitle().contains("Extras"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

		// Payment Methods Page
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement giftcard = driver.findElement(By.xpath("//*[@id=\"giftCardAddButton\"]"));
		jse.executeScript("arguments[0].scrollIntoView()", giftcard);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"giftCardAddButton\"]")));
		assertTrue(driver.getTitle().contains("Payment Options"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();

	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}