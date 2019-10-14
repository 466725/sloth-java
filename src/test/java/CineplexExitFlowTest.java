import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CineplexExitFlowTest {
	private static WebDriver driver;
	private static WebDriverWait wait;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 240, 250);
		driver.get("https://uat-www.cineplex.com");
		driver.manage().window().maximize();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
		Assert.assertTrue(driver.getTitle().contains("Cineplex"));
	}

	@AfterTest
	public void tearDown() {
		sleepInSeconds(5);
		if (driver != null) {
			driver.quit();
		}
		sleepInSeconds(15);
	}

	@Test(priority = 3)
	public void displayModal() {
		driver.findElement(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='bootstrapConnectModalDialog']//button[@class='close']")));
		sleepInSeconds(5);
	}

	@Test(priority = 4)
	public void login() {
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		sleepInSeconds(5);
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		sleepInSeconds(10);
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("userInfo")));
		sleepInSeconds(5);
	}

	@Test(priority = 5)
	public void selectTheatre() {
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']"))
				.click();
		sleepInSeconds(5);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("H0" + "\n");
		sleepInSeconds(5);
	}

	@Test(priority = 6)
	public void selectMovie() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input")))
				.click();
		sleepInSeconds(5);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("Mamma" + "\n");
		sleepInSeconds(5);
	}

	@Test(priority = 7)
	public void selectDate() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']")))
				.click();
		driver.findElement(By.xpath(
				"//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[6]"))
				.click();
		sleepInSeconds(5);
	}

	@Test(priority = 8)
	public void selectTime() {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='quick-tickets-theatre-showtimes']/div[1]/div[2]")));
		driver.findElement(By.xpath("//*[@id=\"l_7997_s_417908_c_0000000001\"]")).sendKeys(Keys.ENTER);
		sleepInSeconds(5);
	}

	@Test(priority = 9)
	public void clickAddTicketButton() {
		List<WebElement> allAdd = driver.findElements(By.xpath("//button[contains(@class,'add-qty-btn')]"));
		for (WebElement ele : allAdd) {
			ele.click();
			sleepInSeconds(1);
		}
		List<WebElement> allPlus = driver.findElements(By.xpath("//div[contains(@class,'btn-plus increment')]"));
		for (WebElement ele : allPlus) {
			ele.click();
			sleepInSeconds(1);
		}
		Assert.assertTrue(driver.getTitle().contains("Ticket Cart"));
		sleepInSeconds(10);
	}

	@Test(priority = 10)
	public void clickProceedButtonOnTicketCartPage() {
		Assert.assertTrue(driver.getTitle().contains("Ticket Cart"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		sleepInSeconds(10);
	}

	@Test(priority = 11)
	public void clickProceedButtonOnExtrasPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"addSuperTicketButton\"]")));
		Assert.assertTrue(driver.getTitle().contains("Extras"));
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		sleepInSeconds(10);
	}

	@Test(priority = 99)
	public void exitCOTFlow() {
		// Exit flow by clicking Cineplex Logo
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='col-xs-6 text-left']//img[@alt='Cineplex Logo']")))
				.click();
		// Verify back at Homepage
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
		Assert.assertTrue(driver.getTitle().contains("Cineplex"));
	}

	private static void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
