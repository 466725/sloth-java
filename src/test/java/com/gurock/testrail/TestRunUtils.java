package com.gurock.testrail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.testng.ITestContext;
import com.openqa.annotations.DataSource;
import com.openqa.testlog.TestLog;
import com.openqa.testlog.TestLog.Entry;

/**
 * This utility class is used to provide methods to deal with test execution
 * process.
 * <P>
 * For e.g.
 * <UL>
 * <LI>CSV (Comma Separated Values) files for automation tests.</LI>
 * <LI>Preparing screenshot images for file transfer to FTP and result reporting
 * </UL>
 */
public class TestRunUtils
{
	
	private static HashMap<String, HashMap<String, String>> testReportParameters = null;
	private static final String CSV_File_Parameter = "csvFile";
	
	/**
	 * Reads the file path from the TestNG test context and returns back
	 * 
	 * @param context
	 *            ITestContext
	 * @return The file path of CSV file loaded from the test context
	 * @throws Exception
	 */
	public static String getCsvFromContext(ITestContext context) throws Exception
	{
		String csvFileName = context.getCurrentXmlTest().getParameter(CSV_File_Parameter);
		if ((csvFileName == null) || csvFileName.isEmpty())
			throw new Exception("<csvFile> parameter expected in the TestNg XML file for this test");
		return csvFileName;
	}
	
	/**
	 * Reads the file path from the TestNG test context and returns back
	 * 
	 * @param context
	 *            ITestContext
	 * @return The file path of CSV file loaded from the test context
	 * @throws Exception
	 */
	public static String getCsvFromMethodOrContext(ITestContext context, Method method) throws Exception
	{
		String csvFileName = context.getCurrentXmlTest().getParameter(CSV_File_Parameter);
		if ((csvFileName != null) && !csvFileName.isEmpty())
		{
			return csvFileName;
		}
		if (method != null)
		{
			DataSource ds = (DataSource) method.getAnnotation(DataSource.class);
			if (ds == null)
			{
				ds = (DataSource) method.getDeclaringClass().getAnnotation(DataSource.class);
			}
			if (ds != null)
			{
				csvFileName = ds.source();
				if ((csvFileName != null) && !csvFileName.isEmpty())
				{
					return csvFileName;
				}
			}
		}
		throw new Exception("<csvFile> parameter expected in the TestNg XML file or method annotation for this test");
	}
	
	/**
	 * Reads the data group from the TestNG test context and returns back
	 * 
	 * @param context
	 *            ITestContext
	 * @return The dataGroup from the test context
	 */
	public static String getDataGroupFromContext(ITestContext context)
	{
		String dataGroup = context.getCurrentXmlTest().getParameter("dataGroup");
		return dataGroup;
	}
	
	/**
	 * Reads the scenario from the TestNG test context and returns back
	 * 
	 * @param context
	 * @return
	 */
	public static String getScenarioFromContext(ITestContext context)
	{
		String scenario = context.getCurrentXmlTest().getParameter("scenario");
		return scenario;
	}
	
	/**
	 * Reads the baseline output file path from the TestNG test context and
	 * returns back
	 * 
	 * @param context
	 *            ITestContext
	 * @return The file path of baseline output CSV file loaded from the test
	 *         context
	 * @throws Exception
	 */
	public static String getBaselineCsvFromContext(ITestContext context) throws Exception
	{
		String csvFileName = context.getCurrentXmlTest().getParameter("csvBaselineOutputFile");
		if ((csvFileName == null) || csvFileName.isEmpty())
			throw new Exception("<csvBaselineOutputFile> parameter expected in the TestNg XML file for this test");
		return csvFileName;
	}
	
	/**
	 * Generates unique log ID based on class name and the object array of test
	 * method parameters.
	 * 
	 * @param className
	 *            Name of java class
	 * @param objArray
	 *            object array generated from test method parameters
	 * @return unique class run id
	 */
	public static String generateUniqueLogId(String className, String methodName, Object[] objArray)
	{
		return className + "." + methodName + "[" + Arrays.hashCode(objArray) + "]";
	}
	
	public static TestResult modifyTestResultForReporting(TestResult testResult)
	{
		ArrayList<TestScenarioResult> updatedScenarioResults = new ArrayList<TestScenarioResult>();
		List<TestScenarioResult> scenarioResults = testResult.getScenarioResults();
		if (scenarioResults != null)
		{
			for (TestScenarioResult scenarioResult : scenarioResults)
			{
				scenarioResult.setLogString(TestRunUtils.buildTestLogText(scenarioResult.getLogEntries()));
				updatedScenarioResults.add(scenarioResult);
			}
		}
		testResult.setScenarioResults(updatedScenarioResults);
		return testResult;
	}
	
	private static String buildTestLogText(ArrayList<Entry> entries)
	{
		String logInText = "";
		if (entries != null && entries.size() > 0)
		{
			int i = 0;
			for (Entry entry : entries)
			{
				i += (entry.getLogType() == TestLog.STEP) ? 1 : 0;
				String str = entry.toString(i);
				logInText += str + "\n";
			}
		}
		return logInText;
	}
	
	/**
	 * Given a stack trace, remove all non-essential debugging information,
	 * leaving only:
	 * - The exception message (first line) - All lines referring to
	 * com.company.* lines of code
	 * 
	 * @param stackTrace
	 * @return
	 */
	public static String simplifyStackTrace(String stackTrace)
	{
		String newTrace = "";
		boolean pastError = false;
		String[] traceLines = stackTrace.split("\n");
		for (int i = 0; i < traceLines.length; i++)
		{
			// If we're not past the error, make sure to include it all. If
			// we've reached the end, check
			// other conditions
			if (!pastError)
			{
				if (traceLines[i].startsWith("  at") || traceLines[i].startsWith("Build info"))
				{
					pastError = true;
				}
				else
				{
					newTrace += traceLines[i] + "\n";
					continue;
				}
			}
			// Always include lines which contain "company" which will be
			// part of the class path
			if (i == 0 || traceLines[i].contains("company"))
			{
				newTrace += traceLines[i] + "\n";
				continue;
			}
		}
		return newTrace;
	}
	
	/**
	 * Retrieve scenario id from test parameters.
	 * 
	 * @param objArray
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String retriveScenarioId(Object[] objArray, String method)
	{
		String scenarioId = null;
		for (Object o : objArray)
		{
			if (o != null)
			{
				if (o instanceof HashMap && ((HashMap<String, String>) o).containsKey("scenario"))
				{
					scenarioId = (((HashMap<String, String>) o).get("scenario"));
					break;
				}
				if (o instanceof ScenarioData && ((ScenarioData) o).getScenarioId() != null)
				{
					scenarioId = ((ScenarioData) o).getScenarioId();
					break;
				}
			}
		}
		return scenarioId == null ? method : scenarioId;
	}
	
	/**
	 * set up Test Report Parameter for a class
	 * 
	 * @param clazz
	 * @param parameters
	 */
	public static synchronized void setTestReportParameter(String clazz, HashMap<String, String> parameters)
	{
		if (testReportParameters == null)
		{
			testReportParameters = new HashMap<String, HashMap<String, String>>();
		}
		if (!testReportParameters.containsKey(clazz))
		{
			testReportParameters.put(clazz, parameters);
		}
	}
	
	/**
	 * retrieve Test Report parameters for a class
	 * 
	 * @param clazz
	 * @return
	 */
	public static HashMap<String, String> getTestReportParameter(String clazz)
	{
		if (testReportParameters == null)
			return null;
		return testReportParameters.get(clazz);
	}
	
	/**
	 * get annotation from a given class
	 * 
	 * @param clazz
	 * @param source
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static <T extends Annotation> T getAnnotation(Class<T> clazz, String source) throws ClassNotFoundException
	{
		Class<?> sourceClazz = Class.forName(source);
		T annotation = sourceClazz.getAnnotation(clazz);
		if (annotation == null)
		{
			Method[] methods = sourceClazz.getDeclaredMethods();
			for (Method method : methods)
			{
				annotation = method.getAnnotation(clazz);
				if (annotation != null)
					break;
			}
		}
		return annotation;
	}
}
