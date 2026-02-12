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

public class TangerineSigninPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(TangerineSigninPage.class.getName());

    @FindBy(id = "menu_signup")
    public WebElement signupButton;

    public TangerineSigninPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TangerineSignupPage gotoSignupPage() {
        logger.info("Navigating to Signup page");
        SeleniumWrapper.explicitWaitClickable(driver, signupButton, PropertiesFileReader.getTimeout());
        signupButton.click();
        return new TangerineSignupPage(driver);
    }
}
