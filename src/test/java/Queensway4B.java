import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Queensway4B {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private boolean onUAT = true;
	private boolean exitFlow = false;
	private String username = "glory.leung@cineplex.com";
	private String password = "Cineplex@2019";
	private String baseURL_UAT = "https://uat-www.cineplex.com";
	private String baseURL_PROD = "https://www.cineplex.com/";

	@BeforeTest
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30, 250);
		if (onUAT)
			driver.get(baseURL_UAT);
		else
			driver.get(baseURL_PROD);
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
		waitClick("//*[@class='loginSignUp'][1]");
	}

	@Test(priority = 4)
	public void login() {
		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		waitSendString("//*[@id='txtEmailAddress']", username);
		waitSendString("//*[@id='txtPassword']", password);
		waitClick("//*[@id='btnLogin']");
		driver.switchTo().defaultContent();
	}

	@Test(priority = 6)
	public void selectTheatre() {
		String theatreName = "Cineplex Cinemas Queensway and VIP";
		if (onUAT)
			theatreName = "H0";
		waitClick("//div[contains(@class,'visible')]//div[contains(@id,'theatre-selection')]");
		waitSendString("//input[@title='Search by Theatre or City' and @tabindex='0']", theatreName + Keys.ENTER);
	}

	@Test(priority = 7)
	public void selectMovie() {
		waitClick("//div[contains(@class,'visible')]//div[contains(@id,'movie-selection')]");
		waitClick("//div[@data-group='mov']//div[@class='option'][3]");
	}

	@Test(priority = 8)
	public void selectDate() {
		waitClick("//div[contains(@class,'visible')]//div[contains(@id,'time-selection')]");
		waitClick("//div[@data-group='date']//div[@class='option'][1]");
	}

	@Test(priority = 9)
	public void selectTime() {
		String time = "(//div[@id='quick-tickets-theatre-showtimes']//a[@id])[3]";
		if (onUAT)
			time = "(//div[@id='quick-tickets-theatre-showtimes']//a[@id])[3]";
		waitSendKeys(time, Keys.ENTER);
	}

	@Test(priority = 10)
	public void clickAddTicketButton() {
		waitClick("(//*[@id=\"collapse0000000001\"]//button)[1]");
	}

	@Test(priority = 11)
	public void clickProceedButtonOnTicketCartPage() {
		waitClick("//button[contains(@data-bind,'Proceed')]");
	}

	@Test(priority = 12)
	public void clickProceedButtonOnSeatMapPage() {
		try {
			driver.findElement(By.xpath("//*[@id='seatmap']/h3"));
			waitClick("//button[contains(@data-bind,'Proceed')]");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 30)
	public void clickProceedButtonOnExtrasPage() {
		Assert.assertEquals(driver.getTitle(), "Extras - Cineplex Ticketing Mvc");
		waitClick("//button[contains(@data-bind,'Proceed')]");
	}

	@Test(priority = 40)
	public void clickProceedButtonOnPaymentOptionsPage() {
		Assert.assertEquals(driver.getTitle(), "Payment Options - Cineplex Ticketing Mvc");
		waitClick("//button[contains(@data-bind,'Proceed')]");
	}

	@Test(priority = 99)
	public void exitTicketingFlow() {
		if (exitFlow) {
			waitClick("//div[@class='col-xs-6 text-left']//img[@alt='Cineplex Logo']");
			waitClick("//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]");
		}
	}

	private static void waitClick(String locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void waitSendString(String locator, String input) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).sendKeys(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void waitSendKeys(String locator, Keys key) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).sendKeys(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
