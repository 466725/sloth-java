package webpages.amazon;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webpages.BaseWebPage;

public class RegisterPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(RegisterPage.class.getName());

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
