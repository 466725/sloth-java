import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MaxConcurrentcyOnly {
	private static WebDriver driver;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		driver.get("https://uat-www.cineplex.com");
	}

	@AfterTest
	public void tearDown() {
		// Do nothing
	}

	@Test(priority = 1)
	public void showLoginPopup() {
		// Do nothing
		Assert.assertTrue(true);
	}

	@Test(priority = 2)
	public void login() {
		// Do nothing
		Assert.assertTrue(false);
	}
}
