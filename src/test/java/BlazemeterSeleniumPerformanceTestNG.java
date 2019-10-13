import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BlazemeterSeleniumPerformanceTestNG {
	private static WebDriver driver;
	private static WebDriverWait wait;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 240, 250);
		driver.get("https://uat-www.cineplex.com");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
	}

	@AfterTest
	public void tearDown() {
		sleepInSeconds(5);
		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 1)
	public void startTest() {
		driver.get("http://uat-www.cineplex.com");
		driver.manage().window().maximize();
		String expectedTitle = "Cineplex.com | Movies, Showtimes, Tickets, Trailers";
		Assert.assertEquals(driver.getTitle(), expectedTitle);
		sleepInSeconds(5);
	}

	@Test(priority = 3)
	public void login() {
		driver.findElement(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")).click();
		sleepInSeconds(5);
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		sleepInSeconds(5);
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		sleepInSeconds(10);
	}

	@Test(priority = 5)
	public void selectTheatre() {
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']"))
				.click();
		sleepInSeconds(10);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("H0" + "\n");
		sleepInSeconds(10);
	}

	@Test(priority = 7)
	public void signOut() {
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		sleepInSeconds(10);
	}

	private static void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
