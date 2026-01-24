package webpages.amazon;

import config.PropertiesFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

public class HomePage extends BaseWebPage {
    @FindBy(id = "nav-link-accountList")
    public WebElement helloSignIn;
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BaseWebPage gotoSigninPage() {
        SeleniumWrapper.explicitWaitClickable(driver, helloSignIn, PropertiesFileReader.getTimeout());
        helloSignIn.click();
        return new SigninPage(driver);
    }
}
