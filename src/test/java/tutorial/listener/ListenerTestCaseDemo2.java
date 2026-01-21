package tutorial.listener;

import com.framework.testcases.ApiTestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ListenerTestCaseDemo2 extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(ListenerTestCaseDemo2.class.getName());

    @Test(enabled = true, timeOut = 500)
    public void testOne() {
        Assert.assertTrue(true);
    }

    @Test(enabled = true, timeOut = 500)
    public void testTwo() {
        Assert.assertTrue(true);
    }

    @Test(enabled = true, timeOut = 500)
    public void testThree() throws Exception {
        throw new Exception("For testing purpose, no worries! ");
    }
}