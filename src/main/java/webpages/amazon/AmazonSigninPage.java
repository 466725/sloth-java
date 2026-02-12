package webpages.amazon;

import config.PropertiesFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

public class AmazonSigninPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(AmazonSigninPage.class.getName());

    @FindBy(id = "ab-registration-ingress-link")
    public WebElement createAccount;
    public AmazonSigninPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BaseWebPage gotoRegisterPage() {
        SeleniumWrapper.explicitWaitClickable(driver, createAccount, PropertiesFileReader.getTimeout());
        createAccount.click();
        return new AmazonRegisterPage(driver);
    }
}
