package webpages.selenium.tangerine;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.SeleniumWrapper;
import webpages.selenium.SeleniumBasePage;

public class HomePage extends SeleniumBasePage {
    protected final static Logger logger = LogManager.getLogger(HomePage.class.getName());

    @FindBy(id = "login")
    public WebElement signinButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public SigninPage gotoSigninPage() {
        SeleniumWrapper.explicitWaitClickable(driver, signinButton, PropertiesFileReader.getTimeout());
        logger.info("Navigating to Signin page");
        signinButton.click();
        return new SigninPage(driver);
    }
}
