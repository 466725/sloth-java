package com.gurock.testrail.models;

/**
 * Represent a case retrieved by defect.
 * For example,
 * "defect": "Jira-1234",
 * "testId": 123456,
 * "clazz": "com.*.TC_Name",
 * "scenario": "7",
 * "status": 2
 */
public class TRRelatedCase
{
	
	private String defect;
	private String testId;
	private String clazz;
	private String scenario;
	private String status;
	
	/**
	 * @return the defect
	 */
	public String getDefect()
	{
		return defect;
	}
	
	/**
	 * @param defect
	 *            the defect to set
	 */
	public void setDefect(String defect)
	{
		this.defect = defect;
	}
	
	/**
	 * @return the testId
	 */
	public String getTestId()
	{
		return testId;
	}
	
	/**
	 * @param testId
	 *            the testId to set
	 */
	public void setTestId(String testId)
	{
		this.testId = testId;
	}
	
	/**
	 * @return the clazz
	 */
	public String getClazz()
	{
		return clazz;
	}
	
	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}
	
	/**
	 * @return the scenario
	 */
	public String getScenario()
	{
		return scenario;
	}
	
	/**
	 * @param scenario
	 *            the scenario to set
	 */
	public void setScenario(String scenario)
	{
		this.scenario = scenario;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}
	
	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}
}
