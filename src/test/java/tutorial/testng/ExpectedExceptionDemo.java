package tutorial.testng;

import com.framework.testcases.ApiTestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;

public class ExpectedExceptionDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(ExpectedExceptionDemo.class.getName());

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = "Pass Message test")
    public void testFive() throws Exception {
        throw new IOException("Fail Message test");
    }

    @Test(expectedExceptions = {IOException.class, NullPointerException.class})
    public void testTwo() throws Exception {
        throw new Exception();
    }

    @Test(expectedExceptions = {IOException.class})
    public void testOne() throws Exception {
        throw new IOException();
    }

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = "Pass Message test")
    public void testThree() throws Exception {
        throw new IOException("Pass Message test");
    }

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = ".* Message .*")
    public void testFour() throws Exception {
        throw new IOException("Pass Message test");
    }
}
