import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BlazemeterGuiTest {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static int extraWait = 2;
	public static boolean exitFlow = true;
	public static boolean onUAT = true;

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, extraWait * 10, 250);
		if (onUAT)
			driver.get("https://uat-www.cineplex.com");
		else
			driver.get("https://www.cineplex.com/");
		driver.manage().window().maximize();
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test(priority = 3)
	public void showLoginPopup() {
		String loginButton;
		if (onUAT) {
			loginButton = "//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]";
		} else {
			loginButton = "";
		}
		waitClick(loginButton);
	}

	@Test(priority = 4)
	public void login() {
		for (int i = 0; i < 3; i++) {
			stangeSleep();
		}

		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		stangeSleep();

		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys("weipeng.zheng@cineplex.com");
		stangeSleep();

		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Cineplex@1303");
		stangeSleep();

		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		stangeSleep();

		driver.switchTo().defaultContent();
	}

	@Test(priority = 5)
	public void selectTheatre() {
		String locator = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']";
		waitClick(locator);

		String locator1 = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input";
		waitSendKeys(locator1, "H0" + "\n");
	}

	@Test(priority = 6)
	public void selectMovie() {
		String locator = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input";
		waitClick(locator);

		String locator1 = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input";
		waitSendKeys(locator1, "Mamma" + "\n");
	}

	@Test(priority = 7)
	public void selectDate() {
		String locator = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']";
		waitClick(locator);

		String locator1 = "//*[@id=\"search-by-theatre-time-selection-area\"]/div/selectize-input/div/div[2]/div/div/div[6]";
		waitClick(locator1);
	}

	@Test(priority = 8)
	public void selectTime() {
		String time = ".//a[contains(text(), '8:00 am')]";
		waitSendKeys(time, Keys.ENTER);
	}

	@Test(priority = 9)
	public void clickAddTicketButton() {
		String addbutton = "//button[contains(@class,'add-qty-btn')]";
		for (int i = 0; i < 5; i++) {
			stangeSleep();
		}

		List<WebElement> allAdd = driver.findElements(By.xpath(addbutton));
		for (WebElement ele : allAdd) {
			ele.click();
			stangeSleep();
		}

		String plusButton = "//div[contains(@class,'btn-plus increment')]";
		stangeSleep();
		List<WebElement> allPlus = driver.findElements(By.xpath(plusButton));
		for (WebElement ele : allPlus) {
			try {
				for (int i = 0; i < 2; i++) {
					ele.click();
					stangeSleep();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 10)
	public void clickProceedButtonOnTicketCartPage() {
		String proceedButton = "//button[contains(@data-bind,'Proceed')]";
		waitClick(proceedButton);
	}

	@Test(priority = 11)
	public void clickProceedButtonOnExtrasPage() {
		String proceedButton = "//button[contains(@data-bind,'Proceed')]";
		waitClick(proceedButton);
	}

	@Test(priority = 99)
	public void exitCOTFlow() {
		if (exitFlow) {
			String logo = "//div[@class='col-xs-6 text-left']//img[@alt='Cineplex Logo']";
			waitClick(logo);
			for (int i = 0; i < 10; i++) {
				stangeSleep();
			}

			String homePage = "//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]";
			waitClick(homePage);
			for (int i = 0; i < 10; i++) {
				stangeSleep();
			}
		} else {
			// Do nothing
		}
	}

	private static void waitClick(String locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).click();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(extraWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void waitSendKeys(String locator, String keysToSend) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).sendKeys(keysToSend);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(extraWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void waitSendKeys(String locator, Keys key) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).sendKeys(key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(extraWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void stangeSleep() {
		try {
			Thread.sleep(extraWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
