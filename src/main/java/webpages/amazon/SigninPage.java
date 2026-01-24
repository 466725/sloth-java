package webpages.amazon;

import config.PropertiesFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumWrapper;
import webpages.BaseWebPage;

public class SigninPage extends BaseWebPage {
    @FindBy(id = "ab-registration-ingress-link")
    public WebElement createAccount;
    public SigninPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BaseWebPage gotoRegisterPage() {
        SeleniumWrapper.explicitWaitClickable(driver, createAccount, PropertiesFileReader.getTimeout());
        createAccount.click();
        return new RegisterPage(driver);
    }
}
