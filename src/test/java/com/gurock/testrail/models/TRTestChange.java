package com.gurock.testrail.models;

import java.util.Date;

/**
 * Data object for TestCaseID APIs, retrieve_defects and get_results
 */
public class TRTestChange
{
	
	private String id;
	private String test_id;
	private String test_title;
	private String defects;
	private Date created_on;
	private String run_id;
	private String latest_status;
	
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * @return the test_id
	 */
	public String getTest_id()
	{
		return test_id;
	}
	
	/**
	 * @param test_id
	 *            the test_id to set
	 */
	public void setTest_id(String test_id)
	{
		this.test_id = test_id;
	}
	
	/**
	 * @return the defects
	 */
	public String getDefects()
	{
		return defects;
	}
	
	/**
	 * @param defects
	 *            the defects to set
	 */
	public void setDefects(String defects)
	{
		this.defects = defects;
	}
	
	/**
	 * @return the run_id
	 */
	public String getRun_id()
	{
		return run_id;
	}
	
	/**
	 * @param run_id
	 *            the run_id to set
	 */
	public void setRun_id(String run_id)
	{
		this.run_id = run_id;
	}
	
	public Date getCreated_on()
	{
		return created_on;
	}
	
	public void setCreated_on(long created_on)
	{
		// Unix Timestamp in Mysql is seconds since epoch (Jan 1 1970) and Java timestamp is milliseconds since epoch.
		// Hence to convert Mysql time to java time, just multiply by 1000
		this.created_on = new Date(1000l * created_on);
	}
	
	public String getLatest_status()
	{
		return latest_status;
	}
	
	public void setLatest_status(String latest_status)
	{
		this.latest_status = latest_status;
	}
	
	public String getTest_title()
	{
		return test_title;
	}
	
	public void setTest_title(String test_title)
	{
		this.test_title = test_title;
	}
}
