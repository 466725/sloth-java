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

public class AmazonHomePage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(AmazonHomePage.class.getName());
    @FindBy(id = "nav-link-accountList")
    public WebElement helloSignIn;
    public AmazonHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BaseWebPage gotoSigninPage() {
        SeleniumWrapper.explicitWaitClickable(driver, helloSignIn, PropertiesFileReader.getTimeout());
        helloSignIn.click();
        return new AmazonSigninPage(driver);
    }
}
