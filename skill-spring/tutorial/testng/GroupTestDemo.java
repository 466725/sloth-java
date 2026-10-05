package tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import core.ApiTestCase;

public class GroupTestDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(GroupTestDemo.class.getName());

    @Test(enabled = true, timeOut = 500, groups = {"test-group"})
    public void testOne() throws InterruptedException {
        Thread.sleep(300);
        logger.info("Group test method one");
    }

    @Test(enabled = true, timeOut = 500)
    public void testTwo() throws InterruptedException {
        Thread.sleep(300);
        logger.info("Group test method two");
    }

    @Test(enabled = true, timeOut = 500, groups = {"test-group"})
    public void testThree() throws InterruptedException {
        Thread.sleep(300);
        logger.info("Group test method three");
    }
}
