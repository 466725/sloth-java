package tutorial.testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Factory;
import core.ApiTestCase;

public class SimpleTestFactoryDemo extends ApiTestCase {
    protected final static Logger logger = LogManager.getLogger(SimpleTestFactoryDemo.class.getName());

    @Factory
    public Object[] factoryMethod() {
        return new Object[]{new SimpleTestDemo(1000), new SimpleTestDemo(2000)};
    }
}
