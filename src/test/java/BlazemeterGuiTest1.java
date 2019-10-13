import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BlazemeterGuiTest1 {
	private WebDriver driver;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		driver.get("https://uat-www.cineplex.com");
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

	private void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void startTest() throws Exception {
		Assert.assertTrue(true);
	}

	@Test(priority = 3)
	public void startTest1() throws Exception {
		Assert.assertTrue(true);
	}

	@Test(priority = 5)
	public void startTest2() throws Exception {
		Assert.assertTrue(false);
		throw new Exception("11111111111111111111+++++++++++++++++++++++++++");
	}
}
