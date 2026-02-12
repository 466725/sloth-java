package webpages.tangerine;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import webpages.BaseWebPage;

public class TangerineHomePage extends BaseWebPage {
    protected final static Logger logger = LogManager.getLogger(TangerineHomePage.class.getName());

    public TangerineHomePage(WebDriver driver) {
        super(driver);
    }
}
