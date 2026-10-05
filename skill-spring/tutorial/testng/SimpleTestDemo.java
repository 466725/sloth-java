package tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import core.ApiTestCase;

public class SimpleTestDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SimpleTestDemo.class.getName());

    private int param = 50;

    public SimpleTestDemo(int param) {
        this.param = param;
    }

    @Test(enabled = true, timeOut = 500)
    public void testOne() {
        int opValue = param + 1;
        logger.info("Test method one output: " + opValue);
    }

    @Test(enabled = true, timeOut = 500)
    public void testTwo() {
        int opValue = param + 2;
        logger.info("Test method two output: " + opValue);
    }
}
