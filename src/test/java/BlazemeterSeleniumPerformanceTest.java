import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BlazemeterSeleniumPerformanceTest {
	private static WebDriver driver;
	private static WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 240, 250);
		driver.get("https://uat-www.cineplex.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")));
	}

	@Test
	public void startTest() throws Exception {
		Thread.sleep(10000);
		assertTrue(driver.getTitle().contains("Cineplex"));
	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(10000);
		driver.quit();
	}
}