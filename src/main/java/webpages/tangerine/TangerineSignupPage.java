package webpages.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import webpages.BaseWebPage;

public class TangerineSignupPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(TangerineSignupPage.class.getName());

    public TangerineSignupPage(WebDriver driver) {
        super(driver);
    }
}
