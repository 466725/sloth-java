package com.gurock.testrail.models;

import java.util.List;

public class TRResult
{
	
	private TRMilestone milestone;
	private TRTest test;
	private TRTestCase testcase;
	private TRTestRun run;
	private List<TRTestCaseRun> testcaseruns;
	private List<TRTestCase> testcases;
	private List<TRConfig> configs;
	private List<TRRelatedCase> cases;
	private List<TRTestChange> testchanges;
	private boolean result;
	
	/**
	 * @return the milestone
	 */
	public TRMilestone getMilestone()
	{
		return milestone;
	}
	
	/**
	 * @param milestone
	 *            the milestone to set
	 */
	public void setMilestone(TRMilestone milestone)
	{
		this.milestone = milestone;
	}
	
	/**
	 * @return the test
	 */
	public TRTest getTest()
	{
		return test;
	}
	
	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(TRTest test)
	{
		this.test = test;
	}
	
	/**
	 * @return the run
	 */
	public TRTestRun getRun()
	{
		return run;
	}
	
	/**
	 * @param run
	 *            the run to set
	 */
	public void setRun(TRTestRun run)
	{
		this.run = run;
	}
	
	/**
	 * @return the testcaseruns
	 */
	public List<TRTestCaseRun> getTestcaseruns()
	{
		return testcaseruns;
	}
	
	/**
	 * @param testcaseruns
	 *            the testcaseruns to set
	 */
	public void setTestcaseruns(List<TRTestCaseRun> testcaseruns)
	{
		this.testcaseruns = testcaseruns;
	}
	
	/**
	 * @return the result
	 */
	public boolean isResult()
	{
		return result;
	}
	
	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(boolean result)
	{
		this.result = result;
	}
	
	/**
	 * @return the testcase
	 */
	public TRTestCase getTestcase()
	{
		return testcase;
	}
	
	/**
	 * @param testcase
	 *            the testcase to set
	 */
	public void setTestcase(TRTestCase testcase)
	{
		this.testcase = testcase;
	}
	
	public List<TRTestCase> getTestcases()
	{
		return testcases;
	}
	
	public void setTestcases(List<TRTestCase> testcases)
	{
		this.testcases = testcases;
	}
	
	/**
	 * @return the configs
	 */
	public List<TRConfig> getConfigs()
	{
		return configs;
	}
	
	/**
	 * @param configs
	 *            the configs to set
	 */
	public void setConfigs(List<TRConfig> configs)
	{
		this.configs = configs;
	}
	
	/**
	 * @return the cases
	 */
	public List<TRRelatedCase> getCases()
	{
		return cases;
	}
	
	/**
	 * @param relatedcases
	 *            the cases to set
	 */
	public void setCases(List<TRRelatedCase> cases)
	{
		this.cases = cases;
	}
	
	/**
	 * @return the testchanges
	 */
	public List<TRTestChange> getTestchanges()
	{
		return testchanges;
	}
	
	/**
	 * @param testchanges
	 *            the testchanges to set
	 */
	public void setTestchanges(List<TRTestChange> testchanges)
	{
		this.testchanges = testchanges;
	}
}
