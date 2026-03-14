package webpages.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import webpages.BaseWebPage;

public class SignupPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(SignupPage.class.getName());

    public SignupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
