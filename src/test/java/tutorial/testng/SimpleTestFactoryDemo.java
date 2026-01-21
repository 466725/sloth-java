package tutorial.testng;

import com.framework.testcases.ApiTestCase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Factory;

public class SimpleTestFactoryDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SimpleTestFactoryDemo.class.getName());

    @Factory
    public Object[] factoryMethod() {
        return new Object[]{new SimpleTestDemo(1000), new SimpleTestDemo(2000)};
    }
}
