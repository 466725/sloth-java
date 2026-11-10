package webpages.selenium.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webpages.selenium.SeleniumBasePage;

public class SignupPage extends SeleniumBasePage {
    protected final static Logger logger = LogManager.getLogger(SignupPage.class.getName());

    public SignupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
