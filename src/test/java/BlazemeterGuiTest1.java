import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BlazemeterGuiTest1 {
	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 240, 250);
		driver.get("https://uat-www.cineplex.com");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
		sleepInSeconds(10);
	}

	@AfterTest
	public void tearDown() throws Exception {
		sleepInSeconds(10);
		if (driver != null) {
			driver.quit();
		}
		throw new Exception("11111111111111111111111111111111111111111111111");
	}

	@Test(priority = 1)
	public void startTest() throws Exception {
		Assert.assertTrue(true);
		throw new Exception("11111111111111111111111111111111111111111111111");
	}
	
	@Test(priority = 3)
	public void startTest1() throws Exception {
		Assert.assertTrue(false);
	}

	private void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
