package com.gurock;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.internal.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.annotations.DataSource;
import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;
import com.gurock.testrail.TestResult;
import com.gurock.testrail.TestRunUtils;
import com.gurock.testrail.TestScenarioResult;
import com.gurock.testrail.enums.HTTPRequestEnum;
import com.gurock.testrail.enums.TestResultStatusEnum;
import com.gurock.testrail.models.HTTPRequestProcessor;
import com.gurock.testrail.models.HTTPResponse;
import com.gurock.testrail.models.TRConfig;
import com.gurock.testrail.models.TRMilestone;
import com.gurock.testrail.models.TRRelatedCase;
import com.gurock.testrail.models.TRResult;
import com.gurock.testrail.models.TRTest;
import com.gurock.testrail.models.TRTestCase;
import com.gurock.testrail.models.TRTestCaseRun;
import com.gurock.testrail.models.TRTestChange;
import com.gurock.testrail.models.TRTestRun;
import com.utilities.CsvFileReader;
import com.utilities.PropertiesFileReader;
import com.utilities.StringUtils;

import config.Constants;

/**
 * This singleton class interacts with TestCaseID
 */
public class TestRailConnector {
	protected final static Logger logger = LogManager.getLogger(TestRailConnector.class.getName());
	private static APIClient apiClient = null;
	private static TestRailConnector testRailConnector = null;
	private static String baseUrl;
	private static String key;
	private static HashMap<String, String> headerHashMap = new HashMap<String, String>();
	private ObjectMapper mapper = null;
	private HashMap<String, String> propertyHashMap = null;
	private int total = 0;

	/**
	 * Singleton testRailConnector retriever
	 */
	public synchronized static APIClient getAPIClient() throws Exception {
		if (testRailConnector == null)
			testRailConnector = new TestRailConnector();
		return apiClient;
	}

	/**
	 * Singleton testRailConnector retriever
	 */
	public synchronized static TestRailConnector getConnector() throws Exception {
		if (testRailConnector == null)
			testRailConnector = new TestRailConnector();
		return testRailConnector;
	}

	private TestRailConnector() throws Exception {
		apiClient = new APIClient(Constants.TEST_RAIL_URL);
		apiClient.setUser(Constants.TEST_RAIL_USERNAME);
		apiClient.setPassword(Constants.TEST_RAIL_PASSWORD);
		baseUrl = PropertiesFileReader.getProperty("testrail.baseurl");
		key = "&key=" + PropertiesFileReader.getProperty("testrail.key");
		headerHashMap.put(HTTPRequestEnum.HTTPRequestHeader.ACCEPT.getProperty(), "application/json");
		propertyHashMap = new HashMap<String, String>();
		propertyHashMap.put("smoke", "5");
		propertyHashMap.put("postupload", "4");
		propertyHashMap.put("nt", "3");
		propertyHashMap.put("regression", "2");
		propertyHashMap.put("broken", "1");
		propertyHashMap.put("bug", "1");
	}

	/**
	 * Return the first parameter if the parameter is the dummy parameters created
	 * when failed at XMLDataProvider
	 */
	private String retriveErrorMsgFromParameters(TestScenarioResult tsr) {
		Object[] params = tsr.getParameters();
		if (params != null && params.length > 0 && params[0] instanceof HashMap<?, ?>) {
			if (((HashMap<?, ?>) params[0]).containsKey("Failed parsing file")) {
				return ((HashMap<?, ?>) params[0]).toString();
			}
		}
		return null;
	}

	/**
	 * Retrieve orgCode. If orgCode is null from the given TestScenarioResult, then
	 * get from System Properties. IF orgCode is null in System Properties, then get
	 * from local properties.
	 * 
	 * @param tir
	 * @return
	 * @throws Exception
	 */
	private String getOrgCode(TestScenarioResult tir) throws Exception {
		String orgCode = tir.getOrgCode();
		if (orgCode == null) {
			orgCode = System.getProperty("orgCode");
			if (orgCode == null || orgCode.equals("${orgCode}"))
				orgCode = PropertiesFileReader.getProperty("app.orgcode");
		}
		return orgCode;
	}

	/**
	 * Retrieve server. If server is null from the given TestScenarioResult, then
	 * get from System Properties. IF server is null in System Properties, then get
	 * from local properties.
	 * 
	 * @param tir
	 * @return
	 * @throws Exception
	 */
	private String getServer(TestScenarioResult tir) throws Exception {
		String server = tir.getServer();
		if (server == null) {
			server = System.getProperty("server");
			if (server == null || server.equals("${server}"))
				server = PropertiesFileReader.getProperty("app.server");
		}
		return server;
	}

	/**
	 * Send a request to TestCaseID REST Server
	 * 
	 * @param url
	 * @param data
	 * @param api
	 * @return
	 */
	private String sendRequest(String url, String data, TestRailAPIs api) throws Exception {
		HTTPResponse response = null;
		HTTPRequestProcessor processor;
		try {
			processor = new HTTPRequestProcessor(url);
			if (api.getMethod() == HTTPRequestEnum.HTTPRequestMethod.POST)
				response = processor.sendHttpRequest(headerHashMap, data);
			else if (api.getMethod() == HTTPRequestEnum.HTTPRequestMethod.GET)
				response = processor.sendHttpRequest(headerHashMap);
			if (response == null || response.getStatus() != HttpURLConnection.HTTP_OK)
				throw new Exception("Failed to access " + api.getUrl() + " [" + response.getStatus() + ":"
						+ response.getMessage() + "]");
		} catch (Exception e) {
			throw new Exception(e);
		}
		return response.getMessage();
	}

	/**
	 * @param testResult         The overall test result
	 * @param TestScenarioResult The testRailConnector test result
	 * @throws Exception if response cannot be registered
	 */
	public void registerResponse(TestResult testResult, TestScenarioResult TestScenarioResult) throws Exception {
		String clazz = testResult.getTestClassName();
		String scenario = TestScenarioResult.getScenarioId();
		String testRunId = testResult.getTmsTestRunId();
		String testCaseId = testResult.getTmsTestCaseId();
		String testId = testResult.getTmsTestId();
		String result = TestScenarioResult.getStatusId() == 0
				? String.valueOf(TestResultStatusEnum.RETEST.getStatusId())
				: String.valueOf(TestScenarioResult.getStatusId());
		String stackTrace = TestRunUtils.simplifyStackTrace(
				(TestScenarioResult.getError() != null) ? Utils.stackTrace(TestScenarioResult.getError(), false)[0]
						: "");
		String errorParam = retriveErrorMsgFromParameters(TestScenarioResult);
		String executionComments = ((errorParam != null) ? (errorParam + "\n\n") : "") + stackTrace + "\n\n"
				+ ((TestScenarioResult.getLogString() == null) ? "" : TestScenarioResult.getLogString());
		String version = TestScenarioResult.getAppVersion();
		String server = getServer(TestScenarioResult);
		String orgCode = getOrgCode(TestScenarioResult);
		String duration = "" + TestScenarioResult.getDuration();
		if (testRunId == null && testId == null) {
			throw new Exception(
					"Both TestRunID and TestId are null.  Cannot report result without a run id or test id");
		}
		String resultPoint = null;
		if (testId != null) {
			resultPoint = baseUrl + TestRailAPIs.AddTestResult.getUrl() + "/" + testId + key;
		} else {
			if (testCaseId == null) {
				try {
					testCaseId = this.getTestCaseId(clazz, scenario);
					if (testCaseId == null) {
						logger.info("[TestRailConnector]: TestCaseID not found for scenario (" + scenario
								+ ") and class (" + clazz + ").");
					}
				} catch (Exception e) {
					logger.info("[TestRailConnector]: " + e.getMessage() + "scenario (" + scenario + ") and class ("
							+ clazz + ").");
				}
			}
			resultPoint = baseUrl + TestRailAPIs.AddTestResultForCase.getUrl() + "/" + testRunId + "/" + testCaseId
					+ key;
		}
		String data = URLEncoder.encode("status_id", "UTF-8") + "=" + URLEncoder.encode(result, "UTF-8");
		logger.info(data);
		// Add optional parameters if they're available
		if (version != null)
			data += "&" + URLEncoder.encode("version", "UTF-8") + "=" + URLEncoder.encode(version, "UTF-8");
		if (server != null)
			data += "&" + URLEncoder.encode("custom_server_url", "UTF-8") + "="
					+ URLEncoder.encode("http://" + server, "UTF-8");
		if (orgCode != null)
			data += "&" + URLEncoder.encode("custom_org_code", "UTF-8") + "=" + URLEncoder.encode(orgCode, "UTF-8");
		data += "&" + URLEncoder.encode("custom_execution_datetime", "UTF-8") + "="
				+ URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "UTF-8");
		if (TestScenarioResult.getStatusId() != TestResultStatusEnum.PASSED.getStatusId())
			data += "&" + URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(executionComments, "UTF-8");
		data += "&" + URLEncoder.encode("custom_execution_duration", "UTF-8") + "="
				+ URLEncoder.encode(duration, "UTF-8");
		updateTestRailTestStatus(resultPoint, testRunId, testId, result, executionComments);
	}

	/**
	 * Update TestCaseID Test Status
	 * 
	 * @param url
	 * @param testRunId
	 * @param testId
	 * @param testStatus
	 * @param testComment
	 * @return
	 * @throws TestRailException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws APIException
	 */
	private String updateTestRailTestStatus(String url, String testRunId, String testId, String testStatus,
			String testComment) throws Exception, MalformedURLException, IOException, APIException {
		if (testRunId == null) {
			url = "add_result/" + testId;
		} else {
			url = "add_result_for_case/" + testRunId + "/" + testId;
		}
		Map<String, Comparable<?>> data = new HashMap<String, Comparable<?>>();
		data.put("status_id", new Integer(testStatus));
		data.put("comment", testComment);
		apiClient.sendPost(url, data);
		return null;
	}

	/**
	 * get case id from TestCaseID for a given test class and scenario id
	 * 
	 * @param clazz
	 * @param scenario
	 * @return
	 * @throws Exception
	 */
	public String getTestCaseId(String clazz, String scenario) throws Exception {
		String url = null;
		if (scenario == null) {
			url = baseUrl + TestRailAPIs.GetTestCaseId.getUrl() + "&class=" + clazz + key;
		} else {
			url = baseUrl + TestRailAPIs.GetTestCaseId.getUrl() + "/" + scenario + "&class=" + clazz + key;
		}
		String response = sendRequest(url, null, TestRailAPIs.GetTestCaseId);
		if (response.contains("The database result has more than one row")) {
			throw new Exception("There is more than one test case with the given parameters.");
		}
		Pattern p = Pattern.compile("id\":(\\d+)");
		Matcher m = p.matcher(response);
		if (!m.find()) {
			return null;
		}
		// Return the first match providing the id
		return m.group(1);
	}

	/**
	 * returns a TRTestCase object for a given test case id
	 * 
	 * @param caseid
	 * @return
	 */
	public TRTestCase getTestCase(String caseid) {
		TRTestCase testcase = null;
		String url = baseUrl + TestRailAPIs.GetTestCase.getUrl() + "/" + caseid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetTestCase);
			TRResult result = mapper.readValue(response, TRResult.class);
			testcase = result.getTestcase();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return testcase;
	}

	/**
	 * returns a TRTest object for a given test id
	 * 
	 * @param testid
	 * @return
	 */
	public TRTest getTest(String testid) {
		TRTest test = null;
		String url = baseUrl + TestRailAPIs.GetTest.getUrl() + "/" + testid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetTest);
			TRResult result = mapper.readValue(response, TRResult.class);
			test = result.getTest();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return test;
	}

	/**
	 * return a TRTestRun object for a given runid
	 * 
	 * @param runid
	 * @return
	 */
	public TRTestRun getTestRun(String runid) {
		TRTestRun testrun = null;
		String url = baseUrl + TestRailAPIs.GetTestRun.getUrl() + "/" + runid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetTestRun);
			TRResult result = mapper.readValue(response, TRResult.class);
			testrun = result.getRun();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return testrun;
	}

	/**
	 * return a TRMileStone object for a given milestone id
	 * 
	 * @param milestoneid
	 * @return
	 */
	public TRMilestone getMilestone(String milestoneid) {
		TRMilestone milestone = null;
		String url = baseUrl + TestRailAPIs.GetMilestone.getUrl() + "/" + milestoneid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetMilestone);
			TRResult result = mapper.readValue(response, TRResult.class);
			milestone = result.getMilestone();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return milestone;
	}

	/**
	 * returns all automated test cases belong to the given runid
	 * 
	 * @param runid
	 * @return
	 */
	public List<TRTestCaseRun> retrieveTestCaseRuns(String runid) {
		List<TRTestCaseRun> runs = null;
		String url = baseUrl + TestRailAPIs.GetTestCaseRuns.getUrl() + "/" + runid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetTestCaseRuns);
			;
			TRResult result = mapper.readValue(response, TRResult.class);
			runs = result.getTestcaseruns();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return runs;
	}

	private String constructArgument(Object obj, String id) throws UnsupportedEncodingException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String data = "";
		boolean isStart = true;
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String methodName = field.getName();
			if (methodName.equals(id))
				continue;
			methodName = "get"
					+ methodName.replaceFirst(methodName.substring(0, 1), methodName.substring(0, 1).toUpperCase());
			Method m = obj.getClass().getDeclaredMethod(methodName);
			Object value = m.invoke(obj);
			if (value != null) {
				if (!isStart)
					data += "&";
				String dataValue = value.toString();
				if (value.getClass().isArray()) {
					String[] values = (String[]) value;
					dataValue = StringUtils.arrayToString(values);
				}
				data += URLEncoder.encode(field.getName(), "UTF-8") + "=" + URLEncoder.encode(dataValue, "UTF-8");
				isStart = false;
			}
		}
		return data;
	}

	/**
	 * returns all test cases where the attributes matchs(equals) the given
	 * TRTestCaseRun object's properties
	 * 
	 * @param filter
	 * @return
	 */
	public List<TRTestCaseRun> retrieveTestCaseRuns(TRTestCaseRun filter) throws Exception {
		List<TRTestCaseRun> runs = null;
		if (filter == null) {
			return runs;
		}
		if (filter.getRunid() == null) {
			throw new Exception("Run ID cannot be empty, please set a valid run id!");
		}
		String url = baseUrl + TestRailAPIs.RetrieveTestCaseRuns.getUrl() + "/" + filter.getRunid().trim() + key;
		String data = constructArgument(filter, "runid");
		try {
			String response = sendRequest(url, data, TestRailAPIs.RetrieveTestCaseRuns);
			TRResult result = mapper.readValue(response, TRResult.class);
			runs = result.getTestcaseruns();
			// If we were supplied with a list of tests, filter them out
			runs = this.applyTestIdFilter(runs, filter);
		} catch (Exception e) {
			// errorHandler(e);
		}
		return runs;
	}

	/**
	 * Given a list of test runs with ids, and a filter with ids, return only those
	 * test runs that match the filter
	 * 
	 * @param runs   a list of test case runs to be executed
	 * @param filter a filter with testids set that will restrict which will execute
	 * @return
	 */
	public List<TRTestCaseRun> applyTestIdFilter(List<TRTestCaseRun> runs, TRTestCaseRun filter) {
		String[] testids = filter.getTestids();
		if (testids == null) {
			return runs;
		}
		List<TRTestCaseRun> out = new ArrayList<TRTestCaseRun>();
		Iterator<TRTestCaseRun> i = runs.iterator();
		while (i.hasNext()) {
			TRTestCaseRun t = i.next();
			if (ArrayUtils.contains(testids, t.getTestid())) {
				out.add(t);
			}
		}
		return out;
	}

	/**
	 * return the milestone name for a given testid
	 * 
	 * @param testid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getMilestoneName(String testid) {
		String milestone = null;
		String url = baseUrl + TestRailAPIs.GetMilestoneName.getUrl() + "/" + testid + key;
		try {
			String response = sendRequest(url, null, TestRailAPIs.GetMilestoneName);
			Map json = mapper.readValue(response, LinkedHashMap.class);
			milestone = (String) ((LinkedHashMap) (json.get("milestone"))).get("milestone");
		} catch (Exception e) {
			// errorHandler(e);
		}
		return milestone;
	}

	/**
	 * Update a test case in TestCaseID with new values
	 * 
	 * @param testcase
	 */
	public boolean updateTestCase(TRTestCase testcase) {
		boolean success = false;
		String url = baseUrl + TestRailAPIs.UpdateTestCase.getUrl() + "/" + testcase.getId() + key;
		try {
			String data = constructArgument(testcase, "id");
			String response = sendRequest(url, data, TestRailAPIs.UpdateTestCase);
			TRResult result = mapper.readValue(response, TRResult.class);
			success = result.isResult();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return success;
	}

	/**
	 * Update test case priority/groups for a given test class and scenario
	 * 
	 * @param clazz
	 * @param scenario
	 * @param priority
	 * @param groups
	 * @return
	 * @throws Exception
	 */
	public String updateTestCasePriority(String clazz, String scenario, String priority, String[] groups) {
		String log = "";
		// logger.info("++++ " + clazz + "[" + scenario + "]");
		try {
			TRTestCase filter = new TRTestCase();
			filter.setCustom_automation_script(clazz);
			filter.setCustom_scenario_id(scenario);
			List<TRTestCase> testcases = retrieveTestCases(filter);
			if (testcases == null || (testcases != null && testcases.size() == 0)) {
				log = "Failed to find test case in TestCaseID for " + clazz + "[" + scenario + "]\n";
				return log;
			}
			for (TRTestCase testcase : testcases) {
				String previousPriority = testcase.getPriority_id();
				String previousGroup = StringUtils.arrayToString(testcase.getCustom_automation_groups());
				String newGroup = StringUtils.arrayToString(groups);
				if (priority.equals("-1"))
					break;
				else if (!(previousPriority.equals(priority)
						&& StringUtils.equalsIgnoreCaseAndOrderForNonDuplicateArrays(
								testcase.getCustom_automation_groups(), groups))) {
					testcase.setPriority_id(priority);
					testcase.setCustom_automation_groups(groups);
					if (updateTestCase(testcase)) {// successful updating
						log = "Successfully updated " + clazz + "[" + scenario + "--" + testcase.getId() + "] to "
								+ priority + "[" + newGroup + "] from " + previousPriority + "[" + previousGroup
								+ "]\n";
					} else {
						log = "Failed to update " + clazz + "[" + scenario + "] to " + priority + "[" + newGroup
								+ "] from " + previousPriority + "[" + previousGroup + "]\n";
					}
				}
			}
		} catch (Exception e) {
			log = "Failed to update test case in TestCaseID for " + clazz + "[" + scenario + "]\n";
			// logger.info(log);
			return log;
		}
		// logger.info(log);
		return log;
	}

	/**
	 * get Total number of test cases
	 * 
	 * @return
	 */
	public int getTotal() {
		return this.total;
	}

	/**
	 * Update priority of test case in Test Rail based on CSV and test group in
	 * CompanyQuality
	 * 
	 * @param packageFolder
	 * @return
	 * @throws Exception
	 */
	public String updateTestCases(String packageFolder) throws Exception {
		if (packageFolder == null || (packageFolder != null) && packageFolder.trim().isEmpty())
			return null;
		StringBuilder summary = new StringBuilder();
		File root = new File(Thread.currentThread().getContextClassLoader().getResource(packageFolder).toURI());
		if (root.isDirectory()) {
			String[] files = root.list();
			for (String file : files) {
				// ignore datasource and xml files, also sandbox
				if ((file.contains(".") && !file.endsWith(".class")) || (!file.contains(".") && file.equals("sandbox")))
					continue;
				summary.append(updateTestCases(packageFolder + "/" + file));
			}
		} else if (root.isFile()) {
			String clazzName = packageFolder.replaceAll("/", ".").replace(".class", "");
			Test test = TestRunUtils.getAnnotation(Test.class, clazzName);
			if (test != null) { // only check test class
								// class level
				int classPriority = getPriorityByGroup(test.groups());
				ArrayList<String> classAutoGroups = setCustomAutomationGroups(null, test.groups());
				DataSource ds = TestRunUtils.getAnnotation(DataSource.class, clazzName);
				if (ds != null) {
					HashMap<String, HashMap<String, String>> metaData = CsvFileReader.retrieveMetaData(ds.source());
					Iterator<String> scenarios = metaData.keySet().iterator();
					while (scenarios.hasNext()) {
						// scenario level
						String scenario = scenarios.next();
						String groups = metaData.get(scenario).get("dataGroup");
						int priority = classPriority;
						ArrayList<String> autoGroups = new ArrayList<String>();
						autoGroups.addAll(classAutoGroups);
						if (groups != null) {
							priority = getPriorityByGroup(groups.split(" "));
							priority = priority > classPriority ? priority : classPriority;
							autoGroups = setCustomAutomationGroups(autoGroups, groups.split(" "));
						}
						countTotal();
						summary.append(getTotal() + ":");
						summary.append(clazzName + " [" + scenario + "] -- ");
						summary.append(StringUtils.arrayToString(test.groups()) + "[" + groups + "] -- ");
						summary.append(priority + " -- ");
						summary.append(autoGroups.size() > 0 ? autoGroups.get(0) : "null");
						summary.append("\n");
						summary.append(updateTestCasePriority(clazzName, scenario, String.valueOf(priority),
								autoGroups.toArray(new String[autoGroups.size()])));
					}
				} else {
					countTotal();
					summary.append(getTotal() + ":");
					summary.append(clazzName + " -- ");
					summary.append(StringUtils.arrayToString(test.groups()) + " -- ");
					summary.append(classPriority + " -- ");
					summary.append(classAutoGroups.size() > 0 ? classAutoGroups.get(0) : "null");
					summary.append("\n");
					summary.append(updateTestCasePriority(clazzName, null, String.valueOf(classPriority),
							classAutoGroups.toArray(new String[classAutoGroups.size()])));
				}
			}
		}
		return summary.toString();
	}

	private int getPriorityByGroup(String[] groups) {
		int priority = -1;
		if (groups == null)
			return priority;
		for (String group : groups) {
			String p = propertyHashMap.get(group.trim());
			if (p == null) {
				if (!group.equals("db"))
					priority = 1;
			} else if (priority < Integer.parseInt(p)) {
				priority = Integer.parseInt(p);
			}
		}
		return priority;
	}

	private ArrayList<String> setCustomAutomationGroups(ArrayList<String> autoGroups, String[] groups) {
		if (autoGroups == null)
			autoGroups = new ArrayList<String>();
		for (String group : groups)
			if (group.equals("db") && !autoGroups.contains("1")) {
				autoGroups.add("1");
				continue;
			}
		return autoGroups;
	}

	private void countTotal() {
		this.total += 1;
	}

	public List<TRTestCase> retrieveTestCases(TRTestCase filter) throws Exception {
		List<TRTestCase> testcases = null;
		if (filter == null) {
			return testcases;
		}
		String url = baseUrl + TestRailAPIs.RetrieveTestCases.getUrl() + "/" + key;
		String data = constructArgument(filter, "");
		try {
			String response = sendRequest(url, data, TestRailAPIs.RetrieveTestCases);
			TRResult result = mapper.readValue(response, TRResult.class);
			testcases = result.getTestcases();
		} catch (Exception e) {
			// errorHandler(e);
		}
		return testcases;
	}

	/**
	 * Retrieve all configuration for a given configuration group. e.g. given "Org
	 * Code" will all org codes configured in TestCaseID
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public List<TRConfig> retrieveConfigs(String group) throws Exception {
		List<TRConfig> configs = null;
		if (StringUtils.isEmpty(group)) {
			throw new Exception("Cannot be null/empty -- group!");
		}
		String url = baseUrl + TestRailAPIs.RetrieveConfigs.getUrl() + "/" + URLEncoder.encode(group, "UTF-8") + key;
		String response = sendRequest(url, null, TestRailAPIs.RetrieveConfigs);
		TRResult result = mapper.readValue(response, TRResult.class);
		configs = result.getConfigs();
		return configs;
	}

	/**
	 * Return all configuration value configured in TestCaseID
	 * 
	 * @return
	 * @throws Exception
	 */
	public Set<String> retrieveConfigsSet(String group) throws Exception {
		Set<String> configSet = new HashSet<String>();
		List<TRConfig> configs = retrieveConfigs(group);
		for (TRConfig config : configs)
			configSet.add(config.getName());
		return configSet;
	}

	/**
	 * Retrieve all related cases for a given JIRA defect number.
	 * 
	 * @param defect
	 * @return
	 * @throws Exception
	 */
	public List<TRRelatedCase> retrieveRelatedCases(String defect) throws Exception {
		List<TRRelatedCase> cases = null;
		if (StringUtils.isEmpty(defect)) {
			return cases;
		}
		String url = baseUrl + TestRailAPIs.RetrieveRelatedCases.getUrl() + "/" + URLEncoder.encode(defect, "UTF-8")
				+ key;
		String response = sendRequest(url, null, TestRailAPIs.RetrieveRelatedCases);
		TRResult result = mapper.readValue(response, TRResult.class);
		cases = result.getCases();
		return cases;
	}

	/**
	 * Return all automated test cases from TestCaseID
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TRTestCase> retrieveCases(String suites) throws Exception {
		List<TRTestCase> cases = null;
		String url = baseUrl + TestRailAPIs.RetrieveCases.url + key;
		String data = StringUtils.isEmpty(suites) ? null
				: URLEncoder.encode("suites", "UTF-8") + "=" + URLEncoder.encode(suites, "UTF-8");
		String response = sendRequest(url, data, TestRailAPIs.RetrieveCases);
		TRResult result = mapper.readValue(response, TRResult.class);
		cases = result.getTestcases();
		return cases;
	}

	/**
	 * Return list of <id, test_id, defects, created_on, run_id> with distinct
	 * <test_id, defects> NOTE: -'id' is test_change table key, it has no direct
	 * relationship with any test or any defect -'defects' has plural name because
	 * it has to match the column name in fact, each record contains only 1 defect
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TRTestChange> retrieveAllDefects() throws Exception {
		List<TRTestChange> testChanges = null;
		String url = baseUrl + TestRailAPIs.RetrieveAllDefects.url + key;
		String response = sendRequest(url, null, TestRailAPIs.RetrieveAllDefects);
		TRResult result = mapper.readValue(response, TRResult.class);
		testChanges = result.getTestchanges();
		// remove duplicated <test_id, defects>
		List<TRTestChange> defects = new ArrayList<TRTestChange>();
		HashMap<String, HashSet<String>> hmap = new HashMap<String, HashSet<String>>();
		for (TRTestChange tc : testChanges) {
			if (!hmap.containsKey(tc.getTest_id())) {
				HashSet<String> hs = new HashSet<String>();
				hs.add(tc.getDefects());
				hmap.put(tc.getTest_id(), hs);
				defects.add(tc);
			} else {
				HashSet<String> hs = hmap.get(tc.getTest_id());
				if (!hs.contains(tc.getDefects())) {
					hs.add(tc.getDefects());
					defects.add(tc);
				}
			}
		}
		return defects;
	}

	/**
	 * Return list of defects (TRTestChange objects) associated to given testId
	 * 
	 * @param testId
	 * @return list of TRTestChange objects
	 * @throws Exception
	 */
	public List<TRTestChange> retrieveTestDefects(String testId) throws Exception {
		String url = baseUrl + TestRailAPIs.RetrieveTestDefects.getUrl() + "/" + testId + key;
		String response = sendRequest(url, null, TestRailAPIs.RetrieveTestDefects);
		TRResult result = mapper.readValue(response, TRResult.class);
		List<TRTestChange> testChanges = result.getTestchanges();
		List<TRTestChange> testDefects = new ArrayList<TRTestChange>();
		HashSet<String> hs = new HashSet<String>();
		// Loop through retrieved testChanges and ignore duplicate defects
		for (TRTestChange tc : testChanges) {
			if (!hs.contains(tc.getDefects())) {
				hs.add(tc.getDefects());
				testDefects.add(tc);
			}
		}
		return testDefects;
	}

	private enum TestRailAPIs {
		GetTestCase("miniapi/get_case", HTTPRequestEnum.HTTPRequestMethod.GET),
		GetTest("miniapi/get_test", HTTPRequestEnum.HTTPRequestMethod.GET),
		GetTestRun("miniapi/get_run", HTTPRequestEnum.HTTPRequestMethod.GET),
		GetMilestone("miniapi/get_milestone", HTTPRequestEnum.HTTPRequestMethod.GET),
		GetMilestoneName("company/get_milestone_by_test", HTTPRequestEnum.HTTPRequestMethod.GET),
		GetTestCaseRuns("company/get_testcase_runs", HTTPRequestEnum.HTTPRequestMethod.GET),
		RetrieveTestCaseRuns("company/retrieve_testcase_runs", HTTPRequestEnum.HTTPRequestMethod.POST),
		RetrieveTestCases("company/retrieve_testcases", HTTPRequestEnum.HTTPRequestMethod.POST),
		GetTestCaseId("company/get_case_by_class", HTTPRequestEnum.HTTPRequestMethod.GET),
		AddTestResult("miniapi/add_result", HTTPRequestEnum.HTTPRequestMethod.POST),
		AddTestResultForCase("miniapi/add_result_for_case", HTTPRequestEnum.HTTPRequestMethod.POST),
		UpdateTestCase("miniapi/update_case", HTTPRequestEnum.HTTPRequestMethod.POST),
		RetrieveConfigs("company/retrieve_configs", HTTPRequestEnum.HTTPRequestMethod.GET),
		RetrieveRelatedCases("company/retrieve_related_cases", HTTPRequestEnum.HTTPRequestMethod.GET),
		RetrieveCases("company/retrieve_cases", HTTPRequestEnum.HTTPRequestMethod.POST),
		RetrieveTestDefects("company/retrieve_test_defects", HTTPRequestEnum.HTTPRequestMethod.GET),
		RetrieveAllDefects("company/retrieve_all_defects", HTTPRequestEnum.HTTPRequestMethod.GET);

		private String url;
		private HTTPRequestEnum.HTTPRequestMethod method;

		TestRailAPIs(String url, HTTPRequestEnum.HTTPRequestMethod method) {
			this.url = url;
			this.method = method;
		}

		public String getUrl() {
			return this.url;
		}

		/**
		 * @return the method
		 */
		public HTTPRequestEnum.HTTPRequestMethod getMethod() {
			return method;
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			logger.info("Usage:");
			logger.info("TestRailConnector <method> <parameters>");
			logger.info("e.g. TestRailConnector retrieveRelatedCases QTF-1201");
		} else {
			TestRailConnector trc = TestRailConnector.getConnector();
			if (args[0].equalsIgnoreCase("retrieveRelatedCases")) {
				List<TRRelatedCase> cases = trc.retrieveRelatedCases(args[1]);
				logger.info("There are [" + cases.size() + "] related to " + args[1]);
				logger.info("TestID\tTest Class\tScenario\tStatus");
				logger.info("=======================================================================");
				for (TRRelatedCase c : cases)
					logger.info(c.getTestId() + "\t" + c.getClazz() + "\t" + c.getScenario() + "\t" + c.getStatus());
			}
		}
		System.exit(0);
	}
}
