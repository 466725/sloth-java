package webpages.tangerine;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

public class TangerineHomePage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(TangerineHomePage.class.getName());

    @FindBy(id = "login")
    public WebElement signinButton;

    public TangerineHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TangerineSigninPage gotoSigninPage() {
        SeleniumWrapper.explicitWaitClickable(driver, signinButton, PropertiesFileReader.getTimeout());
        logger.info("Navigating to Signin page");
        signinButton.click();
        return new TangerineSigninPage(driver);
    }
}
