package testng;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Factory;

public class SimpleTestFactoryDemo {
    protected final static Logger logger = LogManager.getLogger(SimpleTestFactoryDemo.class.getName());

    @Factory
    public Object[] factoryMethod() {
        return new Object[]{new tutorial.testng.SimpleTestDemo(1000), new tutorial.testng.SimpleTestDemo(2000)};
    }
}
