import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CineplexPerfTest1junit {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
	}

	@Test
	public void startTest() throws Exception {
		driver.get("https://uat-www.cineplex.com");
		assertTrue(driver.getTitle().contains("Cineplex"));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]")).click();
		Thread.sleep(2000);
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("glory.leung@cineplex.com");
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@2019");
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("H0" + "\n");
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input"))
				.sendKeys("Ant-Man And The Wasp" + "\n");
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[6]"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"l_7997_s_417359_c_0000000001\"]")).click();
		Thread.sleep(10000);
		driver.findElement(
				By.xpath("//*[@id=\"collapse0000000001\"]/div/div[2]/section/section/div/div/div[2]/div[1]/button"))
				.click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@data-bind,'Proceed')]")).click();
		Thread.sleep(5000);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}