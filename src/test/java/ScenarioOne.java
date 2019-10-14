import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScenarioOne {
	@BeforeTest
	public void setUp() {
		System.out.println("==========================111111-Setup==========================");
	}

	@AfterTest
	public void tearDown() {
		System.out.println("==========================111111-TearDown==========================");
	}

	@Test(priority = 6)
	public void testTrueOne() {
		System.out.println("==========================111111-True==========================");
		Assert.assertTrue(true);
	}

	@Test(priority = 8)
	public void testFalseOne() throws Exception {
		System.out.println("==========================111111-False==========================");
		throw new Exception("==========================111111-False==========================");
	}
}