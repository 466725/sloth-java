package webpages.selenium.google;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webpages.selenium.SeleniumBasePage;

public class GoogleSearchHomePage extends SeleniumBasePage {
    protected final static Logger logger = LogManager.getLogger(GoogleSearchHomePage.class.getName());

    @FindBy(id = "ti6dpd")
    public WebElement searchBox;

    public GoogleSearchHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchBox() {
        return searchBox;
    }
}
