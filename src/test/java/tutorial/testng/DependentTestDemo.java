package tutorial.testng;

import com.framework.testcases.ApiTestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;

public class DependentTestDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(DependentTestDemo.class.getName());

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = "Pass Message test")
    public void testThree() throws Exception {
        long id = Thread.currentThread().getId();
        logger.info("Simple test-method Three. Thread id is: " + id);
        System.out.println("Simple test-method Three. Thread id is: " + id);
        throw new IOException("Pass Message test");
    }

    //must be Independent, to guarantee thread safe
    @Test(threadPoolSize = 3
            , invocationCount = 6
            , timeOut = 1000
            , dependsOnMethods = {"testThree"}
            , expectedExceptions = {IOException.class}
            , expectedExceptionsMessageRegExp = ".* Message .*")
    public void testFour() throws Exception {
        long id = Thread.currentThread().getId();
        logger.info("Simple test-method Four. Thread id is: " + id);
        System.out.println("Simple test-method Four. Thread id is: " + id);
        throw new IOException("Pass Message test");
    }

    @Test(dependsOnGroups = {"test-group"})
    public void testOne() {
        logger.info("Group Test method one");
    }

    @Test(groups = {"test-group"})
    public void testTwo() {
        logger.info("Group test method two");
    }
}
