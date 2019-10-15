import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NorthEdmonton4B {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static int extraWait = 3;
	public static boolean exitFlow = false;
	public static boolean onUAT = false;

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
		String loginButton = "//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]";
		waitClick(loginButton);
	}

	@Test(priority = 4)
	public void login() {

		String username;
		String password;

		if (onUAT)
			username = "glory.leung@cineplex.com";
		else
			username = "glory.leung@cineplex.com";

		if (onUAT)
			password = "Cineplex@2019";
		else
			password = "Password1";

		for (int i = 0; i < 3; i++) {
			letsSleep();
		}

		driver.switchTo().activeElement();
		driver.switchTo().frame("bootstrapModalIframe");
		letsSleep();
		driver.findElement(By.xpath("//*[@id='txtEmailAddress']")).sendKeys(username);
		letsSleep();
		driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys(password);
		letsSleep();
		driver.findElement(By.xpath("//*[@id='btnLogin']")).click();
		letsSleep();
		driver.switchTo().defaultContent();
	}
	
	@Test(priority = 6)
	public void selectTheatre() {
		String theatreDropDown = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']";
		String theatreDropDownTextBox = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-theatre-selection-area']/div/selectize-input/div/div[1]/input";
		String theatreName;

		if (onUAT)
			theatreName = "H0";
		else
			theatreName = "Cineplex Cinemas North Edmonton and VIP";

		waitClick(theatreDropDown);
		waitSendKeys(theatreDropDownTextBox, theatreName + Keys.ENTER);
	}

	@Test(priority = 7)
	public void selectMovie() {
		String movieName;

		if (onUAT)
			movieName = "Ant-Man";
		else
			movieName = "Joker";

		String movieDropDown = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input";
		waitClick(movieDropDown);
		String movieDropDownTextBox = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-movie-selection-area']/div/selectize-input/div/div[1]/input";
		waitSendKeys(movieDropDownTextBox, movieName + Keys.ENTER);
	}

	@Test(priority = 8)
	public void selectDate() {
		String selectDateDropDown = "//div[contains(@class,'tabs-container visible')]//div[@id='search-by-theatre-time-selection-area']";
		waitClick(selectDateDropDown);

		String selectDateAndClick = "//div[contains(@class,'optgroup')]//div[contains(text(), 'Thu, Oct 17 2019')]";
		waitClick(selectDateAndClick);
	}

	@Test(priority = 9)
	public void selectTime() {
		String time;
		if (onUAT)
			time = ".//a[contains(text(), '9:30 am')]";
		else
			time = ".//a[contains(text(), '10:00 pm')]";
		waitSendKeys(time, Keys.ENTER);
	}

	@Test(priority = 10)
	public void clickAddTicketButton() {
		String addbutton = "//button[contains(@class,'add-qty-btn')]";
		for (int i = 0; i < 5; i++) {
			letsSleep();
		}

		List<WebElement> allAdd = driver.findElements(By.xpath(addbutton));
		for (WebElement ele : allAdd) {
			ele.click();
			letsSleep();
		}

		String plusButton = "//div[contains(@class,'btn-plus increment')]";
		letsSleep();
		List<WebElement> allPlus = driver.findElements(By.xpath(plusButton));
		for (WebElement ele : allPlus) {
			try {
				for (int i = 0; i < 2; i++) {
					ele.click();
					letsSleep();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 11)
	public void clickProceedButtonOnTicketCartPage() {
		letsSleep();
		String proceedButton = "//button[contains(@data-bind,'Proceed')]";
		waitClick(proceedButton);
	}

	@Test(priority = 30)
	public void clickProceedButtonOnExtrasPage() {
		letsSleep();
		String proceedButton = "//button[contains(@data-bind,'Proceed')]";
		waitClick(proceedButton);
	}

	@Test(priority = 99)
	public void exitCOTFlow() {
		if (exitFlow) {
			String logo = "//div[@class='col-xs-6 text-left']//img[@alt='Cineplex Logo']";
			waitClick(logo);
			for (int i = 0; i < 10; i++) {
				letsSleep();
			}

			String homePage = "//*[@id='site-navbar-wrap']/nav/div[4]/nav/ul/li[2]/a[1]";
			waitClick(homePage);
			for (int i = 0; i < 10; i++) {
				letsSleep();
			}
		} else {
			for (int i = 0; i < 200; i++) {
				letsSleep();
			}
			// Nothing else
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

	private static void letsSleep() {
		try {
			Thread.sleep(extraWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	List<String> getAllOptions(By by) {
		List<String> options = new ArrayList<String>();
		for (WebElement option : new Select(driver.findElement(by)).getOptions()) {
			if (option.getAttribute("value") != "")
				options.add(option.getText());
		}
		return options;
	}

}
