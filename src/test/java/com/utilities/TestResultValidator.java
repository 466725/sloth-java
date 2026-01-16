package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestResultValidator extends WebPageUtils {

    protected final static Logger logger = LogManager.getLogger(TestResultValidator.class.getName());

    public static boolean isElementEditable(WebElement element) {
        try {
            if (element == null)
                return false;
            if (element.getAttribute("readonly") != null)
                return true;
            element.sendKeys("checkEditable");
            if (element.getText().equals("checkEditable"))
                return true;
            return false;
        } catch (Exception e) {
            logger.error("Exception is: ", e);
            return false;
        }
    }

    public static boolean isStringElementsAscendingOrdered(List<WebElement> elements) {
        List<String> stringList = new ArrayList<>();
        boolean isAscending = true;
        try {
            for (int i = 0; i < elements.size(); i++)
                stringList.add(elements.get(i).getText());
            logger.info("Elements before sorting: " + stringList);
            Collections.sort(stringList);
            logger.info("Elements after sorting: " + stringList);
            for (int i = 0; i < elements.size(); i++)
                isAscending = isAscending && elements.get(i).getText().equals(stringList.get(i));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        logger.info("Is ascending ordered: " + isAscending);
        return isAscending;
    }

    public static boolean isIntegerElementsAscendingOrdered(List<WebElement> elements) {
        List<Integer> intList = new ArrayList<>();
        try {
            for (int i = 0; i < elements.size(); i++)
                intList.add(Integer.parseInt(elements.get(i).getText()));
            logger.info("Elements before: " + intList);
            for (int i = 0; i < intList.size(); i++)
                if ((i + 1) < intList.size())
                    if (intList.get(i) > intList.get(i + 1))
                        return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return true;
        }
        return true;
    }

    public static boolean isDateElementsAscendingOrdered(List<WebElement> elements) {
        List<Long> intList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        try {
            for (int i = 0; i < elements.size(); i++)
                intList.add(format.parse(elements.get(i).getText()).getTime());
            logger.info("Elements before: " + intList);
            for (int i = 0; i < intList.size(); i++)
                if ((i + 1) < intList.size())
                    if (intList.get(i) > intList.get(i + 1))
                        return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return true;
        }
        return true;
    }
}
