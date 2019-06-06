package com.openqa.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.gurock.testrail.TestRunnerContext;

public class FrameworkUtils
{
	
	protected final static Logger logger = LogManager.getLogger(FrameworkUtils.class.getName());
	private static InheritableThreadLocal<Stack<String>> parentBroswerHandle = new InheritableThreadLocal<Stack<String>>();
	
	/**
	 * after a browser is closed, remove its handle from the stack
	 */
	public static void removeBrowserHandle()
	{
		parentBroswerHandle.get().pop();
	}
	
	/**
	 * get the parent browser (calling page) handle return the handle
	 * 
	 * @return String value of the window handle of the calling page
	 */
	public static String getParentHandle()
	{
		if (parentBroswerHandle.get() == null)
			return null;
		return parentBroswerHandle.get().lastElement();
	}
	
	/**
	 * Closes current window and switches the reference to parent window.
	 * 
	 * @param driver
	 * @param mainWindow
	 * @throws Exception
	 */
	public static void closeCurrentWindowAndSwitchToParent(WebDriver driver) throws Exception
	{
		FrameworkUtils.confirmAlert(driver);
		// Get latest handle from the pool and close it's window
		driver.switchTo().window(getParentHandle()).close();
		switchDriveToParentBrowser(driver);
	}
	
	/**
	 * Closes all windows (including popup windows) and switches the reference to
	 * main window.
	 * 
	 * @param driver
	 * @param mainWindow
	 */
	public static void closeAllWindowsExceptMain(WebDriver driver, String mainWindow)
	{
		FrameworkUtils.confirmAlert(driver);
		for (String winH : driver.getWindowHandles())
		{
			if (!winH.equals(mainWindow))
			{
				try
				{
					driver.switchTo().window(winH).close();
				}
				catch (NoSuchWindowException e)
				{
					// ignore, we're closing them all anyway
				}
			}
		}
		// keep popping window handles from parentBroswerHandle stack until reach the
		// last one
		if (parentBroswerHandle != null && parentBroswerHandle.get() != null)
		{
			int depth = parentBroswerHandle.get().size();
			for (int i = 1; i < depth; i++)
				removeBrowserHandle();
		}
		driver.switchTo().window(mainWindow);
	}
	
	/**
	 * switch from current page to its parent browser by removing it from window
	 * handle stack and wait for it close
	 * 
	 * @param driver
	 * @throws Exception
	 */
	public static void switchDriveToParentBrowser(WebDriver driver) throws Exception
	{
		removeBrowserHandle();
		// most of the time the failure is related to not waiting for a window to
		// close before moving on
		ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					return new HashSet<String>(parentBroswerHandle.get()).equals(driver.getWindowHandles());
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(condition);
		Set<String> currentHandles = driver.getWindowHandles();
		Set<String> parentSet = new HashSet<String>(parentBroswerHandle.get());
		// if after default wait time, the window handle sets are not equal, the debug
		// message will be
		// provided for investigation
		if (!parentSet.equals(currentHandles))
		{
			throw new Exception("WARNING: Window handles out of sync: \nExpected: [" + parentBroswerHandle.get().toString() + "]\n" + "Actual: [" + currentHandles.toString() + "]");
		}
		driver.switchTo().window(getParentHandle());
	}
	
	/**
	 * after open a new browser, get the window handle and push it into the stack
	 * 
	 * @param parentHandle
	 * @throws Exception
	 */
	public static void addBrowserHandle(String parentHandle) throws Exception
	{
		if (parentBroswerHandle.get() == null)
		{
			throw new Exception("Window handle stack must be initiated first");
		}
		// if the window handle is the same as the one on the top of the stack, don't
		// push it in
		if (parentBroswerHandle.get().size() == 0 || !parentHandle.equals(parentBroswerHandle.get().lastElement()))
		{
			parentBroswerHandle.get().push(parentHandle);
		}
	}
	
	/**
	 * Used to re-initiate the browser handles from scratch to prevent a dirty
	 * stack from
	 * a previous test run
	 * 
	 * @param originalWindowHandle
	 *            the main window handle
	 */
	public static void initiateBrowserHandling(String originalWindowHandle)
	{
		Stack<String> stack = new Stack<String>();
		parentBroswerHandle.set(stack);
		parentBroswerHandle.get().push(originalWindowHandle);
	}
	
	/**
	 * Switches to window by it's exact title name after clicking the web element
	 * 
	 * @deprecated Use switchToNewWindowAfterClicking
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @return Name of the window
	 * @throws Exception
	 */
	@Deprecated
	public static String switchToWindowByExplicitTitleAfterClicking(WebDriver driver, WebElement clickMe, String title) throws Exception
	{
		return switchToWindowByExplicitTitleAfterClicking(driver, clickMe, title, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Switches to window by it's exact title name after clicking the web element
	 * 
	 * @deprecated Use switchToNewWindowAfterClicking
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @param numberOfSeconds
	 *            Number of seconds to wait
	 * @return Name of the window
	 * @throws Exception
	 */
	@Deprecated
	public static String switchToWindowByExplicitTitleAfterClicking(WebDriver driver, WebElement clickMe, String title, int numberOfSeconds) throws Exception
	{
		// Record original handle
		String originWinH = driver.getWindowHandle();
		final Integer windowsBefore = driver.getWindowHandles().size();
		clickMe.click();
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore + 1, numberOfSeconds);
		switchToWindowByExplictTitle(driver, title);
		return originWinH;
	}
	
	/**
	 * Switches to window by it's title after clicking the web element
	 * 
	 * @deprecated Use switchToNewWindowAfterClicking
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @return Name of the window
	 * @throws Exception
	 */
	@Deprecated
	public static String switchToWindowByTitleAfterClicking(WebDriver driver, WebElement clickMe, String title) throws Exception
	{
		return switchToWindowByTitleAfterClicking(driver, clickMe, title, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Switches to window by it's title after clicking the web element
	 * 
	 * @deprecated Use switchToNewWindowAfterClicking
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @param numberOfSeconds
	 *            Number of seconds to wait
	 * @return Name of the window
	 * @throws Exception
	 */
	@Deprecated
	public static String switchToWindowByTitleAfterClicking(WebDriver driver, WebElement clickMe, String title, int numberOfSeconds) throws Exception
	{
		// Record original handle
		String originWinH = driver.getWindowHandle();
		final Integer windowsBefore = driver.getWindowHandles().size();
		clickMe.click();
		// Wait for window to load (stupid IE)
		// TODO: Make this iPad compatible
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore + 1, numberOfSeconds);
		switchToWindowByTitle(driver, title);
		return originWinH;
	}
	
	/**
	 * Switches to window by it's title. Should ONLY be used when not switching to
	 * a new
	 * window, otherwise use switchToNewWindowAfterClicking
	 * 
	 * @param driver
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @throws Exception
	 */
	public static void switchToWindowByTitle(WebDriver driver, String title) throws Exception
	{
		// Go through handles to find string
		Set<String> winHandles = driver.getWindowHandles();
		Iterator<String> h = winHandles.iterator();
		ArrayList<String> titles = new ArrayList<String>();
		Boolean found = false;
		// Test current window first, to prevent windows that disappear when they lose
		// focus
		// from disappearing
		try
		{
			if (driver.getTitle().contains(title))
			{
				return;
			}
		}
		catch (Exception e)
		{
			// just continue if there is no current window
		}
		while (h.hasNext())
		{
			driver.switchTo().window(h.next());
			// If the window hasn't loaded the title yet, we need to wait for it
			waitForTitle(driver);
			String windowTitle = driver.getTitle();
			if (windowTitle.contains(title))
			{
				found = true;
				break;
			}
			titles.add(windowTitle);
		}
		if (!found)
		{
			throw new Exception("No window with title found: " + title + " but found: " + titles);
		}
	}
	
	/**
	 * This method simply waits for the page title to appear.
	 * 
	 * @param driver
	 */
	public static void waitForTitle(WebDriver driver)
	{
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				return StringUtils.isNotEmpty(driver.getTitle());
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(windowCondition);
	}
	
	/**
	 * Switches to window by the exact title name. Should only be used when not
	 * switching to a new
	 * window, otherwise use switchToNewWindowAfterClicking
	 * 
	 * @param driver
	 * @param title
	 *            Title of the web page to which driver will switch
	 * @throws Exception
	 */
	public static void switchToWindowByExplictTitle(WebDriver driver, String title) throws Exception
	{
		// Go through handles to find string
		Set<String> winHandles = driver.getWindowHandles();
		Iterator<String> h = winHandles.iterator();
		ArrayList<String> titles = new ArrayList<String>();
		Boolean found = false;
		while (h.hasNext())
		{
			driver.switchTo().window(h.next());
			waitForTitle(driver);
			String currentTitle = driver.getTitle().trim();
			if (currentTitle.equalsIgnoreCase(title.trim()))
			{
				found = true;
				break;
			}
			titles.add(currentTitle);
		}
		if (!found)
		{
			throw new Exception("No window with title found: " + title + " but found: " + titles);
		}
	}
	
	/**
	 * Switches the driver to a new window after clicking a button
	 * 
	 * @param driver
	 *            WebDriver
	 * @param clickMe
	 *            Element which you are expecting will open a window
	 * @throws Exception
	 *             If the new window is not found in
	 *             TestRunnerContext.getDefaultWait() seconds
	 */
	public static void switchToNewWindowAfterClicking(WebDriver driver, WebElement clickMe) throws Exception
	{
		switchToNewWindowAfterClicking(driver, clickMe, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Switches the driver to a new window after clicking a button
	 * 
	 * @param driver
	 *            WebDriver
	 * @param clickMe
	 *            Element which you are expecting will open a window
	 * @param numberOfSeconds
	 *            seconds to wait
	 * @throws Exception
	 *             If the new window is not found in numberOfSeconds seconds
	 */
	public static void switchToNewWindowAfterClicking(WebDriver driver, WebElement clickMe, int numberOfSeconds) throws Exception
	{
		// Record original handle
		Set<String> originWinHandles = driver.getWindowHandles();
		final Integer windowsBefore = originWinHandles.size();
		clickMe.click();
		// clickMe.sendKeys("\n");
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore + 1);
		// Go through handles to find string
		Set<String> winHandles = driver.getWindowHandles();
		Iterator<String> h = winHandles.iterator();
		// Find the window handle that didn't exist before
		while (h.hasNext())
		{
			String next = h.next();
			if (!originWinHandles.contains(next))
			{
				driver.switchTo().window(next);
				return;
			}
		}
		throw new Exception("No new window found after clicking on " + clickMe.getText() + ".");
	}
	
	/**
	 * Switches the driver to a new window based on an old set of handles
	 * 
	 * @param driver
	 *            WebDriver
	 * @param originWinHandles
	 *            array of original window handles
	 * @param newHandles
	 *            array of new window handles
	 * @throws Exception
	 *             If the new window is not found in default time
	 */
	public static void switchToWindowByHandleDiff(WebDriver driver, Set<String> originWinHandles) throws Exception
	{
		FrameworkUtils.switchToWindowByHandleDiff(driver, originWinHandles, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Switches the driver to a new window based on an old set of handles
	 * 
	 * @param driver
	 *            WebDriver
	 * @param originWinHandles
	 *            array of original window handles
	 * @param newHandles
	 *            array of new window handles
	 * @param numberOfSeconds
	 *            seconds to wait
	 * @throws Exception
	 *             If the new window is not found in numberOfSeconds seconds
	 */
	public static void switchToWindowByHandleDiff(WebDriver driver, Set<String> originWinHandles, int numberOfSeconds) throws Exception
	{
		// Go through handles to find string
		Set<String> winHandles = driver.getWindowHandles();
		Iterator<String> h = winHandles.iterator();
		// Find the window handle that didn't exist before
		while (h.hasNext())
		{
			String next = h.next();
			if (!originWinHandles.contains(next))
			{
				driver.switchTo().window(next);
				return;
			}
		}
		throw new Exception("No new window found.");
	}
	
	/**
	 * Switches the driver to a new window after clicking a button, if
	 * ElementNotVisibleException present,
	 * then force to click the element through javascript
	 * 
	 * @param driver
	 *            WebDriver
	 * @param clickMe
	 *            Element which you are expecting will open a window
	 * @param selector
	 *            selector context
	 * @throws Exception
	 *             If the new window is not found in
	 *             TestRunnerContext.getDefaultWait() seconds
	 */
	public static void switchToNewWindowAfterClicking(WebDriver driver, WebElement clickMe, String selector) throws Exception
	{
		switchToNewWindowAfterClicking(driver, clickMe, selector, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Switches the driver to a new window after clicking a button, if
	 * ElementNotVisibleException present,
	 * then force to click the element through javascript
	 * 
	 * @param driver
	 *            WebDriver
	 * @param clickMe
	 *            Element which you are expecting will open a window
	 * @param selector
	 *            selector context
	 * @param numberOfSeconds
	 *            seconds to wait
	 * @throws Exception
	 *             If the new window is not found in numberOfSeconds seconds
	 */
	public static void switchToNewWindowAfterClicking(WebDriver driver, WebElement clickMe, String selector, int numberOfSeconds) throws Exception
	{
		// Record original handle
		Set<String> originWinHandles = driver.getWindowHandles();
		final Integer windowsBefore = driver.getWindowHandles().size();
		try
		{
			clickMe.click();
			// clickMe.sendKeys("\n");
		}
		catch (ElementNotVisibleException e)
		{
			((JavascriptExecutor) driver).executeScript("$(\"" + selector + "\").click();");
		}
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore + 1);
		// Go through handles to find string
		Set<String> winHandles = driver.getWindowHandles();
		Iterator<String> h = winHandles.iterator();
		// Find the window handle that didn't exist before
		while (h.hasNext())
		{
			String next = h.next();
			if (!originWinHandles.contains(next))
			{
				driver.switchTo().window(next);
				return;
			}
		}
		throw new Exception("No new window found after clicking on " + clickMe.getText() + ".");
	}
	
	/**
	 * try to click the element, if ElementNotVisibleException present,
	 * then force to click the element through javascript
	 * 
	 * @param driver
	 * @param clickMe
	 * @param selector
	 */
	public static void click(WebDriver driver, WebElement clickMe, String selector)
	{
		try
		{
			clickMe.click();
			// clickMe.sendKeys("\n");
		}
		catch (ElementNotVisibleException e)
		{
			((JavascriptExecutor) driver).executeScript("$(\"" + selector + "\").click();");
		}
	}
	
	/**
	 * try to click the element, if ElementNotVisibleException present, then
	 * force to click the element through javascript
	 * 
	 * @param driver
	 * @param clickMe
	 * @param selector
	 * @throws Exception
	 */
	public static void clickJS(WebDriver driver, WebElement clickMe, String selector) throws Exception
	{
		try
		{
			clickAjaxButton(driver, clickMe);
		}
		catch (WebDriverException e)
		{
			if (e.getMessage().contains("Element is not clickable"))
			{
				((JavascriptExecutor) driver).executeScript("$(\"" + selector + "\").click();");
			}
		}
	}
	
	/**
	 * Confirms the message on the Alert window if the Alert window opened.
	 * 
	 * @param driver
	 */
	public static void confirmAlert(WebDriver driver)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
		catch (Exception e)
		{
			// ignore
		}
	}
	
	/**
	 * If an alert exists, return its text. Otherwise return null
	 * 
	 * @param driver
	 * @return String of an alert or null
	 */
	public static String getAlertText(WebDriver driver)
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		}
		catch (NoAlertPresentException e)
		{
		}
		return null;
	}
	
	/**
	 * Checks if the window closed after the web element was clicked and waits for
	 * TestRunnerContext.getDefaultWait() milliseconds and if
	 * window does not close in specified time, throws the error
	 * 
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public static ArrayList<String> windowClosesWhenClicking(WebDriver driver, WebElement clickMe) throws Exception
	{
		// TOOD: the time will be handled by template based on server performance
		return windowClosesWhenClicking(driver, clickMe, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Checks if the window closed after the web element was clicked and waits for
	 * seconds and if
	 * window does not close in specified time, throws the error
	 * 
	 * @param driver
	 * @param clickMe
	 *            Web element which needs to be clicked
	 * @param seconds
	 * @return list of alerts handled
	 * @throws Exception
	 */
	public static ArrayList<String> windowClosesWhenClicking(WebDriver driver, WebElement clickMe, int seconds) throws Exception
	{
		final Integer windowsBefore = driver.getWindowHandles().size();
		ArrayList<String> alertsList = new ArrayList<String>();
		clickMe.click();
		try
		{
			alertsList = handleSubmissionAlerts(driver, true);
		}
		catch (Exception e)
		{
			logger.error("Failed on handling submission alerts: [" + e + "].");
			throw new Exception("Error at handling submission alerts: [" + e + "].");
		}
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore - 1, seconds);
		return alertsList;
	}
	
	/**
	 * Waits for a given number of windows to exist before proceeding
	 * 
	 * @param driver
	 * @param windowNum
	 *            The number of windows you're waiting for
	 */
	public static void waitForNumberOfWindows(WebDriver driver, final Integer windowNum, int seconds)
	{
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					return driver.getWindowHandles().size() == windowNum;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, seconds);
		waitForWindow.until(windowCondition);
	}
	
	/**
	 * Waits for a given number of windows to exist before proceeding
	 * 
	 * @param driver
	 * @param windowNum
	 *            The number of windows you're waiting for
	 */
	public static void waitForNumberOfWindows(WebDriver driver, final Integer windowNum)
	{
		FrameworkUtils.waitForNumberOfWindows(driver, windowNum, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * Waits for every window to close except for the original one
	 * 
	 * @param driver
	 *            WebDriver
	 * @param numberOfSeconds
	 *            Number of seconds to wait
	 */
	public static void waitForAllWindowsToClose(WebDriver driver, int numberOfSeconds)
	{
		FrameworkUtils.waitForNumberOfWindows(driver, 1);
	}
	
	public static void clickAjaxButton(WebDriver driver, final WebElement el)
	{
		ExpectedCondition<Boolean> button = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					if (el != null && el.isDisplayed() && el.isEnabled())
						return true;
					return false;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(button);
		el.click();
		if (!getBrowser(driver).equals("chrome"))
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	public static void waitForElement(WebDriver driver, final WebElement el)
	{
		waitForElement(driver, el, TestRunnerContext.getDefaultWait());
	}
	
	public static void waitForElement(WebDriver driver, final WebElement el, int numberOfSeconds)
	{
		ExpectedCondition<Boolean> enabled = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					if (el != null && el.isDisplayed() && el.isEnabled())
						return true;
					return false;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, numberOfSeconds);
		waitForWindow.until(enabled);
	}
	
	/**
	 * Given an element, wait for its attribute to become a certain value
	 * 
	 * @param driver
	 *            Web Driver
	 * @param el
	 *            Element to be validated
	 * @param attr
	 *            Attribute to check, such as "value"
	 * @param value
	 *            Value to check, such as "23"
	 * @deprecated This method may not work yet
	 */
	@Deprecated
	public static void waitForAttribute(WebDriver driver, final WebElement el, final String attr, final String value)
	{
		ExpectedCondition<Boolean> enabled = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					if (el != null && el.getAttribute(attr) == value)
						return true;
					return false;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(enabled);
	}
	
	public static void waitForElementById(WebDriver driver, String id)
	{
		waitForElement(driver, By.id(id), TestRunnerContext.getDefaultWait());
	}
	
	public static void waitForElementById(WebDriver driver, String id, int numberOfSeconds)
	{
		waitForElement(driver, By.id(id), numberOfSeconds);
	}
	
	public static void waitForElement(WebDriver driver, final By by)
	{
		waitForElement(driver, by, TestRunnerContext.getDefaultWait());
	}
	
	public static void waitForElement(WebDriver driver, final By by, int numberOfSeconds)
	{
		ExpectedCondition<Boolean> found = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					WebElement el = driver.findElement(by);
					if (!el.isDisplayed() || !el.isEnabled())
					{
						throw new Exception("");
					}
				}
				catch (Exception e)
				{
					return false;
				}
				return true;
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, numberOfSeconds);
		waitForWindow.until(found);
	}
	
	public static void waitForNoSuchElement(WebDriver driver, final By by)
	{
		waitForNoSuchElement(driver, by, TestRunnerContext.getDefaultWait());
	}
	
	public static void waitForNoSuchElement(WebDriver driver, final By by, int numberOfSeconds)
	{
		ExpectedCondition<Boolean> notFound = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					WebElement el = driver.findElement(by);
					if (el.isDisplayed())
					{
						return false;
					}
					return true;
				}
				catch (Exception e)
				{
					return true;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, numberOfSeconds);
		waitForWindow.until(notFound);
	}
	
	/**
	 * If jquery animations cause a problem with clicking links, this script will
	 * wait until
	 * those animations are complete
	 * 
	 * @param driver
	 */
	public static void waitForJavascript(WebDriver driver, final String script, int second)
	{
		ExpectedCondition<Boolean> done = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					JavascriptExecutor js = (JavascriptExecutor) driver;
					return (Boolean) js.executeScript(script);
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForJs = null;
		if (second >= 0)
			waitForJs = new WebDriverWait(driver, second);
		else
			waitForJs = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		try
		{
			waitForJs.until(done);
		}
		catch (TimeoutException e)
		{
			logger.error("Waiting for javascript failed, continuing");
		}
	}
	
	/**
	 * Wait all Jquery script complete
	 * 
	 * @param driver
	 */
	public static void waitForJavascriptDone(WebDriver driver)
	{
		try
		{
			waitForJavascript(driver, "return (jQuery.active == 0 && $(\":animated\").length == 0)", TestRunnerContext.getDefaultWait());
		}
		catch (TimeoutException e)
		{
			logger.error("Javascript Execution did not finish, continuing test");
		}
	}
	
	/**
	 * Wait all Jquery script complete
	 * 
	 * @param driver
	 */
	public static void waitForJavascriptDone(WebDriver driver, int second)
	{
		waitForJavascript(driver, "return jQuery.active == 0", second);
	}
	
	/**
	 * Given an element which is animated using jquery, will wait for that
	 * element to stop animating before continuing.
	 * 
	 * @param driver
	 * @param id
	 *            ID of the element you're waiting to complete animating
	 */
	public static void waitForAnimation(WebDriver driver, final String id)
	{
		ExpectedCondition<Boolean> done = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					JavascriptExecutor js = (JavascriptExecutor) driver;
					return !(Boolean) js.executeScript("return $('#" + id + "').is(':animated')");
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForAnimation = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		try
		{
			waitForAnimation.until(done);
		}
		catch (TimeoutException e)
		{
			logger.error("waitForAnimation timed out, continuing on...");
		}
	}
	
	/**
	 * To wait certain number of seconds and check if a possible alert pops up .
	 * Return true if the alert pops up, return false if the alert does not pop up
	 * after number of seconds.
	 * 
	 * @param driver
	 * @param msgOnAlert
	 * @param numberOfSeconds
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean hasAlertPopup(WebDriver driver, final String msgOnAlert, int numberOfSeconds) throws InterruptedException
	{
		ExpectedCondition<Boolean> pops = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					Alert alert = driver.switchTo().alert();
					if (alert.getText().contains(msgOnAlert))
					{
						return true;
					}
					return false;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		try
		{
			// Prevent firefox from hanging
			Thread.sleep(10000);
			WebDriverWait waitForWindow = new WebDriverWait(driver, numberOfSeconds);
			return waitForWindow.until(pops);
		}
		catch (TimeoutException e)
		{
			return false;
		}
	}
	
	/**
	 * To handle popped-up alert per alert message by waiting for specified wait
	 * time
	 * 
	 * @param driver
	 * @param msgOnAlert
	 * @param numberOfSeconds
	 * @returns true if a popup alert is consumed (by confirming it), false if no
	 *          alert is consumed (because none is generated or because can't
	 *          consume)
	 */
	public static boolean handlePoppedAlertByMessage(WebDriver driver, final String msgOnAlert, int numberOfSeconds, final boolean accept)
	{
		try
		{
			WebDriverWait waitForAlert = new WebDriverWait(driver, numberOfSeconds);
			waitForAlert.until(ExpectedConditions.alertIsPresent());
		}
		catch (TimeoutException e)
		{
			return false;
		}
		Alert alert = driver.switchTo().alert();
		// If the alert is found, deal with it
		if (alert.getText().contains(msgOnAlert))
		{
			if (accept)
			{
				alert.accept();
			}
			else
			{
				alert.dismiss();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * To handle popped-up alert per alert message by waiting for default wait time
	 * 
	 * @param driver
	 * @param msgOnAlert
	 * @throws InterruptedException
	 */
	public static boolean handlePoppedAlertByMessage(WebDriver driver, final String msgOnAlert, boolean acept)
	{
		return handlePoppedAlertByMessage(driver, msgOnAlert, TestRunnerContext.getDefaultWait(), acept);
	}
	
	/**
	 * Click a web element on a window, handle 1 expected alert
	 * and wait for the window to close.
	 * 
	 * @param driver
	 * @param clickMe
	 * @param accept
	 * @throws Exception
	 */
	public static void windowClosesOnClickAndHandleSingleExpectedAlert(WebDriver driver, WebElement clickMe, boolean accept) throws Exception
	{
		final Integer windowsBefore = driver.getWindowHandles().size();
		clickMe.click();
		try
		{
			handleSingleExpectedAlert(driver, accept);
		}
		catch (Exception e)
		{
			logger.error("Failed on handling single expected alert: [" + e.getMessage() + "].");
			throw new Exception("Error at handling single expected alert: [" + e.getMessage() + "].");
		}
		FrameworkUtils.waitForNumberOfWindows(driver, windowsBefore - 1, TestRunnerContext.getDefaultWait());
	}
	
	/**
	 * This method handle 1 and only 1 expected alert
	 * 
	 * @param driver
	 * @param accept
	 */
	public static void handleSingleExpectedAlert(WebDriver driver, boolean accept)
	{
		ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					return ExpectedConditions.alertIsPresent() != null;
				}
				catch (Exception e)
				{
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(condition);
		try
		{
			Alert alt = driver.switchTo().alert();
			if (alt != null)
			{
				if (accept)
				{
					alt.accept();
				}
				else
				{
					alt.dismiss();
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Value of parameter accept is " + accept + ", Exception message is \n" + e.getMessage());
		}
	}
	
	/**
	 * Handles alerts based on the browser being used
	 * 
	 * @param driver
	 * @param accept
	 * @return
	 */
	public static ArrayList<String> handleSubmissionAlerts(WebDriver driver, boolean accept)
	{
		if ("firefox".equals(TestRunnerContext.getBrowser()))
		{
			return FrameworkUtils.handleFirefoxSubmissionAlerts(driver, accept);
		}
		else
		{
			return FrameworkUtils.handleChromeSubmissionAlerts(driver, accept);
		}
	}
	
	/**
	 * Handles any submission alert popups that come up
	 * and accept or dismiss per boolean parameter
	 */
	public static ArrayList<String> handleFirefoxSubmissionAlerts(WebDriver driver, boolean accept)
	{
		ArrayList<String> alertsList = new ArrayList<String>();
		// Accept alert if it pops up
		for (int i = 0; i < 10; i++)
		{
			// TestLog.debug("HandlerSubmissionAlerts [" + i + "]");
			try
			{
				// This sleep is to prevent hanging in firefox
				// TODO find a better way of preventing hanging in firefox
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				// do nothing here, this is temp change until we have better solution
			}
			try
			{
				Alert alert = driver.switchTo().alert();
				if (alert != null)
				{
					alertsList.add(alert.getText());
					if (accept)
					{
						alert.accept();
					}
					else
					{
						alert.dismiss();
					}
				}
			}
			catch (NoAlertPresentException ae)
			{
				break;
			}
			// occurs when the order form is closed and still looping through to check
			// the alerts, the
			// driver focus is lost
			catch (NoSuchWindowException nswe)
			{
				break;
			}
			catch (Exception we)
			{
				// ignore exception
				break;
			}
		} // for
		return alertsList;
	}
	
	/**
	 * Handles any submission alert popups that come up
	 * and accept or dismiss per boolean parameter
	 */
	public static ArrayList<String> handleChromeSubmissionAlerts(WebDriver driver, boolean accept)
	{
		ArrayList<String> alertsList = new ArrayList<String>();
		// Accept alert if it pops up
		for (int i = 0; i < 10; i++)
		{
			try
			{
				// if the current window handle not active, there won't be any alerts to
				// worry about
				if (driver.getWindowHandles().contains(driver.getWindowHandle()))
				{
					Alert alert = driver.switchTo().alert();
					if (alert != null)
					{
						alertsList.add(alert.getText());
						if (accept)
						{
							alert.accept();
						}
						else
						{
							alert.dismiss();
						}
					}
				}
			}
			catch (NoAlertPresentException ae)
			{
				// ignore exception
			}
			// occurs when the order form is closed and still looping through to check
			// the alerts, the
			// driver focus is lost
			catch (NoSuchWindowException nswe)
			{
				break;
			}
			catch (Exception we)
			{
				// ignore exception
				break;
			}
		} // for
		return alertsList;
	}
	
	/**
	 * Wait for a window with title that contains certain string to appear
	 * Switch to the window/browser/tab, and return the window handle
	 * 
	 * @param driver
	 * @param title
	 * @return
	 */
	public static String waitForWindowWithTitle(WebDriver driver, final String title)
	{
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				Set<String> winHandles = driver.getWindowHandles();
				Iterator<String> h = winHandles.iterator();
				while (h.hasNext())
				{
					driver.switchTo().window(h.next());
					if (driver.getTitle().contains(title))
						return true;
				}
				return false;
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(windowCondition);
		return driver.getWindowHandle();
	}
	
	/**
	 * Wait for a textbox's value become the given text value
	 * 
	 * @param driver
	 * @param textBox:
	 *            the element
	 * @param text:
	 *            the expected text value. if pass InputDataConstants.ANY ("%ANY%"),
	 *            then will wait textbox's value become not empty
	 * @param attribute:
	 *            if null or empty, then the text will be textBox.getText(),
	 *            otherwise, will be textBox.getAttribute(attribute)
	 * @throws Exception
	 */
	public static void waitForText(WebDriver driver, final WebElement textBox, final String text, final String attribute) throws Exception
	{
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					if (text.equals("%ANY%"))
					{
						if (attribute != null && !attribute.isEmpty())
							return !textBox.getAttribute(attribute).isEmpty();
						else
							return !textBox.getText().isEmpty();
					}
					else
					{
						if (attribute != null && !attribute.isEmpty())
							return textBox.getAttribute(attribute).equals(text);
						else
							return textBox.getText().equals(text);
					}
				}
				catch (NoSuchWindowException e)
				{
					// try again
					return false;
				}
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		waitForWindow.until(windowCondition);
	}
	
	/**
	 * To wait for a web element to be click-able
	 * The web element locator has to be an xpath
	 * 
	 * @param driver
	 * @param xpath
	 */
	public static void waitElementClickable(WebDriver driver, String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	/**
	 * To get xpath out from a webelement
	 * 
	 * @param driver
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public static String getElementXPath(WebDriver driver, WebElement element) throws Exception
	{
		if (element == null)
		{
			throw new Exception("The web element is null, cannot retrieve xpath from a null value.");
		}
		String javaScript = "function getElementXPath(elt){" + "var path = \"\";" + "for (; elt && elt.nodeType == 1; elt = elt.parentNode){" + "idx = getElementIdx(elt);" + "xname = elt.tagName;"
						+ "if (idx > 1){" + "xname += \"[\" + idx + \"]\";" + "}" + "path = \"/\" + xname + path;" + "}" + "return path;" + "}" + "function getElementIdx(elt){" + "var count = 1;"
						+ "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){" + "if(sib.nodeType == 1 && sib.tagName == elt.tagName){" + "count++;" + "}" + "}" + "return count;"
						+ "}" + "return getElementXPath(arguments[0]).toLowerCase();";
		return (String) ((JavascriptExecutor) driver).executeScript(javaScript, element);
	}
	
	/**
	 * Clicks the WebElement and switches the driver focus to the given iframe
	 * 
	 * @param driver
	 * @param clickMe
	 * @param iframe
	 * @throws Exception
	 */
	public static void clickAndSwitchToIframe(WebDriver driver, WebElement clickMe, WebElement iframe) throws Exception
	{
		FrameworkUtils.addBrowserHandle(driver.getWindowHandle());
		clickMe.click();
		driver.switchTo().frame(iframe);
	}
	
	/**
	 * Switches to a parent window from an iframe
	 * 
	 * @param driver
	 */
	public static void switchToParentFromIframe(WebDriver driver)
	{
		driver.switchTo().window(FrameworkUtils.getParentHandle());
	}
	
	/**
	 * To wait for a new browser with explicit title open and switch the driver
	 * over to it.
	 * 
	 * @param driver
	 * @param title
	 * @throws Exception
	 */
	public static void waitAndSwitchToWindowWithTitle(WebDriver driver, final String title) throws Exception
	{
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				Set<String> winHandles = driver.getWindowHandles();
				Iterator<String> h = winHandles.iterator();
				while (h.hasNext())
				{
					driver.switchTo().window(h.next());
					waitForTitle(driver);
					String currentTitle = driver.getTitle().trim();
					if (currentTitle.contains(title.trim()))
					{
						return true;
					}
				}
				return false;
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait());
		try
		{
			waitForWindow.until(windowCondition);
		}
		catch (TimeoutException e)
		{
			throw new Exception("Window with title, " + title + " not appear.");
		}
	}
	
	/**
	 * Dismiss an alert. If no alert is found, we just catch the
	 * {@link NoAlertPresentException} and ignore it.
	 * 
	 * @param driver
	 *            web driver instnace
	 */
	public static void dismissAlert(WebDriver driver)
	{
		// dismiss first alert
		try
		{
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		}
		catch (NoAlertPresentException nae)
		{
			// No alert is expected
		}
	}
	
	/**
	 * return browser for a given webdriver
	 * 
	 * @param driver
	 * @return
	 */
	public static String getBrowser(WebDriver driver)
	{
		if (driver instanceof RemoteWebDriver)
		{
			return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
		}
		return "htmlunit";
	}
	
	/**
	 * Polls for max 2 mins, and checks if any alerts are present; if no alerts are
	 * found within 2 mins, Timeout exception thrown.
	 * Calls handleAlerts to accept or dismiss the alert.
	 * Polls every 2 seconds, so that new alerts have time to load.
	 * If NoAlertPresentException or NoSuchWindowException is thrown the function
	 * stops looking for alerts and returns a list of alerts that were handled.
	 * (Needs to be reviewed)
	 * 
	 * @param driver
	 * @param accept
	 * @param currentWindowHandle
	 * @return List<String>
	 * @throws TimeoutException
	 */
	public static List<String> handleSubmissionAlertsMODIFIED(WebDriver driver, final boolean accept)
	{
		final List<String> alertsList = new ArrayList<String>();
		ExpectedCondition<Boolean> windowCondition = new ExpectedCondition<Boolean>()
		{
			
			public Boolean apply(WebDriver driver)
			{
				try
				{
					try
					{
						// Firefox clears the alert if an exception is thrown.
						// driver.getWindowHandle() consistently throws UnhandledAlertException in
						// Firefox which causes failures.
						if ((TestRunnerContext.getBrowser()).equals("firefox"))
						{
							handleAlert(driver, accept, alertsList);
						}
						else
						{
							if (driver.getWindowHandles().contains(driver.getWindowHandle()))
							{
								handleAlert(driver, accept, alertsList);
							}
						}
					}
					catch (UnhandledAlertException e)
					{
					}
				}
				catch (NoAlertPresentException e)
				{
					// No alerts are present after waiting for 2 seconds.
					return true;
				}
				catch (NoSuchWindowException e)
				{
					return true;
				}
				catch (Exception e)
				{
					// Accounts for something unexpected
					e.printStackTrace();
				}
				return false;
			}
		};
		WebDriverWait waitForWindow = new WebDriverWait(driver, TestRunnerContext.getDefaultWait() * 4, 10000);
		try
		{
			waitForWindow.until(windowCondition);
		}
		catch (TimeoutException e)
		{
			e.printStackTrace();
		}
		return alertsList;
	}
	
	/**
	 * Check if alert is present. If present, accept or dismiss the alert.
	 * 
	 * @param driver
	 * @param accept
	 * @param alertsList
	 */
	private static void handleAlert(WebDriver driver, final boolean accept, final List<String> alertsList)
	{
		if (ExpectedConditions.alertIsPresent() != null)
		{
			Alert alert = driver.switchTo().alert();
			if (alert != null)
			{
				alertsList.add(alert.getText());
				if (accept)
					alert.accept();
				else
					alert.dismiss();
			}
		}
	}
	
	public static void enterInfoToNumberTypeTextField(WebDriver driver, String element_id, String value)
	{
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String script = "document.getElementById('" + element_id + "').value='" + value + "';";
		if (jse instanceof WebDriver)
		{
			jse.executeScript(script);
		}
	}
}
