package com.gurock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.IResultMap;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlTest;

import com.framework.helpers.TestLogHelper;
import com.gurock.testrail.TestResult;
import com.gurock.testrail.TestRunUtils;
import com.gurock.testrail.TestScenarioResult;
import com.gurock.testrail.enums.TestResultStatusEnum;
import com.gurock.testrail.TestResultCapture;

import org.openqa.selenium.remote.UnreachableBrowserException;

public class TestRailListener extends TestListenerAdapter
{
	
	private TestRailConnector testRailConnector = null;
	
	public void onTestFailure(ITestResult testResult)
	{
		if (isFailureSkipped(testResult))
			onTestSkipped(testResult);
		manageTestFailure(testResult);
	}
	
	public void manageTestFailure(ITestResult testResult)
	{
		try
		{
			String clazzName = testResult.getMethod().getClass().getClass().getName();
			if (TestRunUtils.getTestReportParameter(clazzName) != null)
			{
				String customizedStatus = TestRunUtils.getTestReportParameter(clazzName).get("failedStatus");
				if (customizedStatus != null)
				{
					onTestResult(TestResultStatusEnum.getTestResultStatusEnum(customizedStatus), testResult);
					return;
				}
			}
			onTestResult(TestResultStatusEnum.FAILED, testResult);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean isFailureSkipped(ITestResult testResult)
	{
		Throwable t = testResult.getThrowable();
		if ((t.getClass() == NullPointerException.class) || (t.getClass() == org.openqa.selenium.WebDriverException.class) || (t.getClass() == java.net.SocketException.class)
						|| (t.getClass() == UnreachableBrowserException.class) || (t.getClass() == org.openqa.selenium.UnsupportedCommandException.class))
		{
			testResult.setStatus(ITestResult.SKIP);
			return true;
		}
		return false;
	}
	
	public void onTestSkipped(ITestResult testResult)
	{
		try
		{
			String uniqueLogId = TestRunUtils.generateUniqueLogId(testResult.getTestClass().getName(), testResult.getMethod().getMethodName(), testResult.getParameters());
			if (TestLogHelper.getLogEntries(uniqueLogId).isEmpty())
				TestLogHelper.initialize(uniqueLogId);
			if (testResult.getThrowable() == null)
			{
				IResultMap failedConfigurations = testResult.getTestContext().getFailedConfigurations();
				if (failedConfigurations.size() > 0)
				{
					Iterator<ITestResult> results = failedConfigurations.getAllResults().iterator();
					testResult.setThrowable(results.next().getThrowable());
				}
			}
			String clazzName = testResult.getMethod().getClass().getClass().getName();
			if (TestRunUtils.getTestReportParameter(clazzName) != null)
			{
				String customizedStatus = TestRunUtils.getTestReportParameter(clazzName).get("skippedStatus");
				if (customizedStatus != null)
				{
					onTestResult(TestResultStatusEnum.getTestResultStatusEnum(customizedStatus), testResult);
					return;
				}
			}
			onTestResult(TestResultStatusEnum.BLOCKED, testResult);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void onTestSuccess(ITestResult testResult)
	{
		try
		{
			String clazzName = testResult.getTestClass().getName();
			if (TestRunUtils.getTestReportParameter(clazzName) != null)
			{
				String customizedStatus = TestRunUtils.getTestReportParameter(clazzName).get("passedStatus");
				if (customizedStatus != null)
				{
					onTestResult(TestResultStatusEnum.getTestResultStatusEnum(customizedStatus), testResult);
					return;
				}
			}
			onTestResult(TestResultStatusEnum.PASSED, testResult);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void onConfigurationFailure(ITestResult testResult)
	{
		String uniqueLogId = null;
		try
		{
			ITestNGMethod method = testResult.getMethod();
			if ((testResult.getStatus() == ITestResult.FAILURE) && (method.isAfterMethodConfiguration() || method.isAfterClassConfiguration() || method.isAfterTestConfiguration()
							|| method.isAfterSuiteConfiguration() || method.isAfterGroupsConfiguration()))
			{
				uniqueLogId = TestRunUtils.generateUniqueLogId(testResult.getTestClass().getName(), testResult.getMethod().getMethodName(), testResult.getParameters());
				if (TestLogHelper.getLogEntries(uniqueLogId).isEmpty())
					TestLogHelper.initialize(uniqueLogId);
				onTestResult(TestResultStatusEnum.POSTTESTFAILED, testResult);
			}
		}
		catch (Exception e)
		{
			TestLogHelper.debug(e.getMessage());
		}
		finally
		{
			if (uniqueLogId != null)
			{
				TestLogHelper.done(uniqueLogId);
				TestLogHelper.removeLogEntries(uniqueLogId, Thread.currentThread().getId());
			}
		}
	}
	
	private String getCaseId(String className, String scenarioNumber, String currentTestRunid) throws Exception
	{
		if (currentTestRunid == null)
			return null;
		String str = "get_tests/" + currentTestRunid;
		JSONArray jSONArray = (JSONArray) TestRailConnector.getAPIClient().sendGet(str);
		for (int i = 0; i < jSONArray.size(); i++)
		{
			String currentClassName = (String) ((JSONObject) jSONArray.get(i)).get("custom_testclass");
			if (currentClassName.compareTo(className) == 0)
			{
				String currentScenarioNumber = (String) ((JSONObject) jSONArray.get(i)).get("custom_scenario").toString();
				if (currentScenarioNumber.compareTo(scenarioNumber) == 0)
					return ((JSONObject) jSONArray.get(i)).get("case_id").toString();
			}
		}
		return "-1";
	}
	
	private void onTestResult(TestResultStatusEnum resultStatus, ITestResult myTestResult) throws Exception
	{
		this.testRailConnector = TestRailConnector.getConnector();
		TestResult testResult = TestResultCapture.createTestResult(myTestResult.getMethod());
		if (testResult.getTmsTestId() == null && testResult.getTmsTestRunId() == null)
		{
			XmlTest xmlTest = myTestResult.getTestContext().getCurrentXmlTest();
			testResult.setTmsTestRunId(xmlTest.getParameter("testRunId"));
			testResult.setTmsTestId(xmlTest.getParameter("testId"));
		}
		XmlTest xmlTest = myTestResult.getTestContext().getCurrentXmlTest();
		String str = xmlTest.getParameter("ignorePassed");
		boolean state = Boolean.parseBoolean(str);
		if (resultStatus.getStatus().compareTo("Passed") == 0)
			testResult.setIgnoreLogForPassedTestsState(state);
		if (testResult.getTmsTestId() == null)
		{
			String scenario = new String();
			Object[] obj = myTestResult.getParameters();
			if (obj.length != 0)
			{
				Object objEntry = obj[0];
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) objEntry;
				scenario = map.get("scenario");
			}
			else
				scenario = "1";
			String caseId = getCaseId(myTestResult.getInstanceName(), scenario, testResult.getTmsTestRunId());
			testResult.setTmsTestId(caseId);
		}
		TestScenarioResult testScenarioResult = TestResultCapture.createScenarioResult(myTestResult, resultStatus);
		if (testResult.getIgnoreLogForPassedTestsState())
			testScenarioResult.setLogEntries(null);
		ArrayList<TestScenarioResult> scenarioResults = new ArrayList<TestScenarioResult>();
		scenarioResults.add(testScenarioResult);
		testResult.setScenarioResults(scenarioResults);
		TestResult testResultModified = TestRunUtils.modifyTestResultForReporting(testResult);
		for (TestScenarioResult scenarioResult : testResultModified.getScenarioResults())
		{
			if (!testScenarioResult.getStatus().equalsIgnoreCase(TestResultStatusEnum.PASSED.getStatus()))
			{
			}
			this.testRailConnector.registerResponse(testResultModified, scenarioResult);
		}
	}
}
