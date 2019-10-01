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
		System.out.println("Cineplex load test started!");
		driver = new FirefoxDriver();
	}

	@Test
	public void testCineplex() throws Exception {
		driver.get("http://uat-www.cineplex.com");
		assertTrue(driver.getTitle().contains("Cineplex"));
		System.out.println(driver.getCurrentUrl());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		System.out.println("Cineplex load test ended!");
	}

	public static void openInBrowser(String string) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(null);
			} catch (Exception ex) {
				System.out.println("Failed to open in browser");
			}
		}
	}
}