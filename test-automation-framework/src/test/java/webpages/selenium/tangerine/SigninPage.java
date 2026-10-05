package webpages.selenium.tangerine;

import config.Constants;
import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.SeleniumWrapper;
import webpages.selenium.SeleniumBasePage;

public class SigninPage extends SeleniumBasePage {
    protected final static Logger logger = LogManager.getLogger(SigninPage.class.getName());

    @FindBy(id = "menu_signup")
    public WebElement signupButton;

    public SigninPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public SignupPage gotoSignupPage() {
        logger.info("Navigating to Signup page");

        SeleniumWrapper.scrollToElement(driver, signupButton);
        SeleniumWrapper.explicitWaitClickable(driver, signupButton, PropertiesFileReader.getTimeout());

        try {
            signupButton.click();
        } catch (Exception e) {
            logger.warn("Normal click failed; falling back to JS click.", e);
            SeleniumWrapper.clickElement(driver, signupButton, Constants.CLICK_METHOD.RUN_JS);
        }

        return new SignupPage(driver);
    }
}
