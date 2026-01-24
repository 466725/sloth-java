package tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import testcases.ApiTestCase;

import java.io.IOException;

public class ParallelMethodDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(ParallelMethodDemo.class.getName());

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
            , expectedExceptions = {IOException.class}
            , expectedExceptionsMessageRegExp = ".* Message .*")
    public void testFour() throws Exception {
        long id = Thread.currentThread().getId();
        logger.info("Simple test-method Four. Thread id is: " + id);
        System.out.println("Simple test-method Four. Thread id is: " + id);
        throw new IOException("Pass Message test");
    }
}
