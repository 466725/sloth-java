package com.gurock.testrail.models;

public class TRTestCaseRun
{
	
	private String project;
	private String testid;
	private String[] testids;
	private String status_id; // 1: passed 2: blocked 3: untested 4: retest 5: failed
	private String runid;
	private String caseid;
	private String title;
	private String clazz;
	private String scenario;
	private String automated;
	private String type_id;
	private String priority_id;
	private String estimate;
	private String user;
	private String section;
	private String suite;
	private String milestone;
	private String config;
	
	/**
	 * @return the project
	 */
	public String getProject()
	{
		return project;
	}
	
	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(String project)
	{
		this.project = project;
	}
	
	/**
	 * @return the testid
	 */
	public String getTestid()
	{
		return testid;
	}
	
	/**
	 * @param testid
	 *            the testid to set
	 */
	public void setTestid(String testid)
	{
		this.testid = testid;
	}
	
	/**
	 * @return the testid
	 */
	public String[] getTestids()
	{
		return testids;
	}
	
	/**
	 * @param testid
	 *            the testid to set
	 */
	public void setTestids(String[] testids)
	{
		this.testids = testids;
	}
	
	/**
	 * @return the status_id
	 */
	public String getStatus_id()
	{
		return status_id;
	}
	
	/**
	 * @param status_id
	 *            the status_id to set
	 */
	public void setStatus_id(String status_id)
	{
		this.status_id = status_id;
	}
	
	/**
	 * @return the runid
	 */
	public String getRunid()
	{
		return runid;
	}
	
	/**
	 * @param runid
	 *            the runid to set
	 */
	public void setRunid(String runid)
	{
		this.runid = runid;
	}
	
	/**
	 * @return the caseid
	 */
	public String getCaseid()
	{
		return caseid;
	}
	
	/**
	 * @param caseid
	 *            the caseid to set
	 */
	public void setCaseid(String caseid)
	{
		this.caseid = caseid;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
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
	 * @return the automated
	 */
	public String getAutomated()
	{
		return automated;
	}
	
	/**
	 * @param automated
	 *            the automated to set
	 */
	public void setAutomated(String automated)
	{
		this.automated = automated;
	}
	
	/**
	 * @return the type_id
	 */
	public String getType_id()
	{
		return type_id;
	}
	
	/**
	 * @param type_id
	 *            the type_id to set
	 */
	public void setType_id(String type_id)
	{
		this.type_id = type_id;
	}
	
	/**
	 * @return the priority_id
	 */
	public String getPriority_id()
	{
		return priority_id;
	}
	
	/**
	 * @param priority_id
	 *            the priority_id to set
	 */
	public void setPriority_id(String priority_id)
	{
		this.priority_id = priority_id;
	}
	
	/**
	 * @return the estimate
	 */
	public String getEstimate()
	{
		return estimate;
	}
	
	/**
	 * @param estimate
	 *            the estimate to set
	 */
	public void setEstimate(String estimate)
	{
		this.estimate = estimate;
	}
	
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}
	
	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}
	
	/**
	 * @return the section
	 */
	public String getSection()
	{
		return section;
	}
	
	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section)
	{
		this.section = section;
	}
	
	/**
	 * @return the suite
	 */
	public String getSuite()
	{
		return suite;
	}
	
	/**
	 * @param suite
	 *            the suite to set
	 */
	public void setSuite(String suite)
	{
		this.suite = suite;
	}
	
	/**
	 * @return the milestone
	 */
	public String getMilestone()
	{
		return milestone;
	}
	
	/**
	 * @param milestone
	 *            the milestone to set
	 */
	public void setMilestone(String milestone)
	{
		this.milestone = milestone;
	}
	
	/**
	 * @return the config
	 */
	public String getConfig()
	{
		return config;
	}
	
	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(String config)
	{
		this.config = config;
	}
}
