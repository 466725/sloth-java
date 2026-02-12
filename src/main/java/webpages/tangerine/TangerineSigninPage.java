package webpages.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import webpages.BaseWebPage;

public class TangerineSigninPage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(TangerineSigninPage.class.getName());

    public TangerineSigninPage(WebDriver driver) {
        super(driver);
    }
}
