package com.gurock.testrail.models;

public class TRMilestone
{
	
	private String id;
	private String name;
	private String description;
	private String is_completed;
	private String due_on;
	private String completed_on;
	private String project_id;
	
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * @return the is_completed
	 */
	public String getIs_completed()
	{
		return is_completed;
	}
	
	/**
	 * @param is_completed
	 *            the is_completed to set
	 */
	public void setIs_completed(String is_completed)
	{
		this.is_completed = is_completed;
	}
	
	/**
	 * @return the due_on
	 */
	public String getDue_on()
	{
		return due_on;
	}
	
	/**
	 * @param due_on
	 *            the due_on to set
	 */
	public void setDue_on(String due_on)
	{
		this.due_on = due_on;
	}
	
	/**
	 * @return the completed_on
	 */
	public String getCompleted_on()
	{
		return completed_on;
	}
	
	/**
	 * @param completed_on
	 *            the completed_on to set
	 */
	public void setCompleted_on(String completed_on)
	{
		this.completed_on = completed_on;
	}
	
	/**
	 * @return the project_id
	 */
	public String getProject_id()
	{
		return project_id;
	}
	
	/**
	 * @param project_id
	 *            the project_id to set
	 */
	public void setProject_id(String project_id)
	{
		this.project_id = project_id;
	}
}
