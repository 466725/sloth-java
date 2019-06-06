package com.gurock.testrail.models;

/**
 * @author artemi.chiriaev
 */
public class HTTPResponse
{
	
	private int status; // HttpURLConnection Status code
	private String message = null;
	private String filename = null;
	
	/**
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}
	
	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename()
	{
		return filename;
	}
	
	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
}
