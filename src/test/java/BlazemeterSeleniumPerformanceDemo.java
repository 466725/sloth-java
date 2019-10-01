
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BlazemeterSeleniumPerformanceDemo {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void testCineplex() throws Exception {
		driver.get("http://uat-www.cineplex.com");
		assertTrue(driver.getTitle().contains("Cineplex"));
		System.out.println(driver.getTitle());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}