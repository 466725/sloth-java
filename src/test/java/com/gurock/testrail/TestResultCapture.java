package com.gurock.testrail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import com.gurock.testrail.enums.TestResultStatusEnum;
import com.openqa.annotations.TestCaseID;
import com.openqa.testlog.TestLog;

/**
 * This abstract class is used to read through all TestNG test results and create lighter version of test results
 * so these results can be reported to any test management system like QC, TestCaseID.
 */
public abstract class TestResultCapture implements IReporter
{
	
	Map<String, TestResult> testResults = null;
	
	public Map<String, TestResult> getTestResults()
	{
		return testResults;
	}
	
	@Override
	public void generateReport(List<XmlSuite> xml, List<ISuite> suites, String outputDirectory)
	{
		testResults = new HashMap<String, TestResult>();
		for (ISuite suite : suites)
		{
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (String resultKey : suiteResults.keySet())
			{
				ISuiteResult suiteResult = suiteResults.get(resultKey);
				ITestContext context = suiteResult.getTestContext();
				initializeTestResults(context);
				// passed tests
				IResultMap resultMap = context.getPassedTests();
				try
				{
					generateTestResults(resultMap, TestResultStatusEnum.PASSED);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// Failed Tests
				resultMap = context.getFailedTests();
				try
				{
					generateTestResults(resultMap, TestResultStatusEnum.FAILED);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// skipped tests
				resultMap = context.getSkippedTests();
				try
				{
					generateTestResults(resultMap, TestResultStatusEnum.BLOCKED);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} // end for loop
	}
	
	/**
	 * Generates Test Results map from testNG result map
	 * 
	 * @param resultMap
	 *            TestNG result map
	 * @param resultStatus
	 * @throws Exception
	 */
	private void generateTestResults(IResultMap resultMap, TestResultStatusEnum resultStatus) throws Exception
	{
		Set<ITestResult> resultSet = resultMap.getAllResults();
		for (ITestResult result : resultSet)
		{
			ITestNGMethod method = result.getMethod();
			String uniqueTestResultKey = generateTestResultKey(method);
			TestScenarioResult scenarioResult = createScenarioResult(result, resultStatus);
			// Add to TestResult Map
			addScenarioResult(uniqueTestResultKey, scenarioResult);
		}
	}
	
	public static TestScenarioResult createScenarioResult(ITestResult itr, TestResultStatusEnum resultStatus)
	{
		TestScenarioResult scenarioResult = new TestScenarioResult();
		String uniqueLogId = TestRunUtils.generateUniqueLogId(itr.getTestClass().getName(), itr.getMethod().getMethodName(), itr.getParameters());
		scenarioResult.setUniqueLogId(uniqueLogId);
		scenarioResult.setStatus(resultStatus.getStatus());
		scenarioResult.setStatusId(resultStatus.getStatusId());
		scenarioResult.setLogEntries(TestLog.getLogEntries(uniqueLogId));
		scenarioResult.setServer(TestLog.getServer(uniqueLogId));
		scenarioResult.setOrgCode(TestLog.getOrgCode(uniqueLogId));
		scenarioResult.setFacility(TestLog.getFacility(uniqueLogId));
		scenarioResult.setAppVersion(TestLog.getAppVersion(uniqueLogId));
		scenarioResult.setUserName(TestLog.getUser(uniqueLogId));
		// For the case where some exceptions thrown in AfterMethod cause BeforeMethod setupLogger is
		// skipped
		if (TestLog.getScenarioId(uniqueLogId) == null)
		{
			scenarioResult.setScenarioId(TestRunUtils.retriveScenarioId(itr.getParameters(), itr.getMethod().getMethodName()));
		}
		else
		{
			scenarioResult.setScenarioId(TestLog.getScenarioId(uniqueLogId));
		}
		scenarioResult.setError(itr.getThrowable());
		scenarioResult.setParameters(itr.getParameters());
		// Set execution duration (in seconds)
		scenarioResult.setDuration((itr.getEndMillis() - itr.getStartMillis()) / 1000);
		return scenarioResult;
	}
	
	private void addScenarioResult(String key, TestScenarioResult scenarioResult) throws Exception
	{
		if (key == null || this.testResults == null || this.testResults.size() == 0)
		{
			return;
		}
		if (this.testResults.containsKey(key))
		{
			TestResult result = this.testResults.get(key);
			result.addScenarioResult(scenarioResult);
		}
		else
		{
			throw new Exception("Missing associated test result...");
		}
	}
	
	/**
	 * Initialises test results by creating test result map for reporting
	 * 
	 * @param context
	 *            TestNG test context
	 */
	private void initializeTestResults(ITestContext context)
	{
		// Go through all methods and build the testresult map
		ITestNGMethod[] allMethods = context.getAllTestMethods();
		for (ITestNGMethod method : allMethods)
		{
			String uniqueTestResultKey = generateTestResultKey(method);
			if (!testResults.containsKey(uniqueTestResultKey))
			{
				TestResult testResult = createTestResult(method);
				testResults.put(uniqueTestResultKey, testResult);
			}
		}
	}
	
	public static TestResult createTestResult(ITestNGMethod method)
	{
		TestResult testResult = new TestResult();
		testResult.setTestName(method.getTestClass().getTestName());
		testResult.setTestClassName(method.getTestClass().getName());
		testResult.setTestMethodName(method.getMethodName());
		testResult.setTmsTestRunId(getTestRunId(method));
		testResult.setTmsTestCaseId(getTestRailCaseId(method));
		testResult.setTmsTestId(getTestId(method));
		return testResult;
	}
	
	/**
	 * Generates unique test result key
	 * 
	 * @param method
	 * @return Unique test result key
	 */
	public static String generateTestResultKey(ITestNGMethod method)
	{
		return method.getTestClass().getName() + "_" + method.getMethodName();
	}
	
	/**
	 * Returns Test Run ID provided in the XML file via parameter=testRunId
	 * 
	 * @param method
	 *            TestNG Method
	 * @return Test Run ID
	 */
	private static String getTestRunId(ITestNGMethod method)
	{
		if (method.getXmlTest() == null)
			return null;
		String testRunId = method.getXmlTest().getParameter("testRunId");
		return testRunId;
	}
	
	/**
	 * Returns Test ID provided in the XML file via parameter=testId
	 * 
	 * @param method
	 *            TestNG Method
	 * @return Test ID
	 */
	private static String getTestId(ITestNGMethod method)
	{
		if (method.getXmlTest() == null)
			return null;
		String testId = method.getXmlTest().getParameter("testId");
		return testId;
	}
	
	/**
	 * Gets the TestCaseID case ID from method annotation
	 * 
	 * @param method
	 * @return TestCaseID case ID
	 */
	private static String getTestRailCaseId(ITestNGMethod method)
	{
		String testRailCaseId = null;
		TestCaseID methodAn = null;
		try
		{
			methodAn = (TestCaseID) method.getConstructorOrMethod().getMethod().getAnnotation(TestCaseID.class);
			if (methodAn != null)
			{
				testRailCaseId = methodAn.testCaseID();
			}
		}
		catch (Exception e)
		{
			return null;
		}
		return testRailCaseId;
	}
}
