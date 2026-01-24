package webpages.amazon;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webpages.BaseWebPage;

public class RegisterPage extends BaseWebPage {
    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
