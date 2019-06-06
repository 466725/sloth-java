package com.gurock.testrail.enums;

public enum TestResultStatusEnum
{
	PASSED(1, "Passed"), BLOCKED(2, "Blocked"), RETEST(4, "Retest"), FAILED(5, "Failed"), COMPLETED(6, "Completed"), SKIPPED(7, "Skipped"), POSTTESTFAILED(9, "PostTestFailed");
	
	private int statusId;
	private String status;
	
	TestResultStatusEnum(int statusId, String status)
	{
		this.statusId = statusId;
		this.status = status;
	}
	
	public int getStatusId()
	{
		return this.statusId;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public String toString()
	{
		return getStatus();
	}
	
	/**
	 * Return the enumeration based on its value.
	 * 
	 * @param status
	 *            ID
	 * @return TestResultStatusEnum
	 * @throws Exception
	 */
	public static TestResultStatusEnum getTestResultStatusEnum(int statusId) throws Exception
	{
		if (statusId > 0)
		{
			for (TestResultStatusEnum resultStatus : TestResultStatusEnum.values())
			{
				if (statusId == resultStatus.getStatusId())
				{
					return resultStatus;
				}
			}
		}
		throw new Exception("Invalid Test Result Status ID: " + statusId);
	}
	
	/**
	 * Return the enumeration based on its status.
	 * 
	 * @param status
	 * @return TestResultStatusEnum
	 * @throws Exception
	 */
	public static TestResultStatusEnum getTestResultStatusEnum(String status) throws Exception
	{
		if (status != null)
		{
			for (TestResultStatusEnum resultStatus : TestResultStatusEnum.values())
			{
				if (status.equalsIgnoreCase(resultStatus.getStatus()))
				{
					return resultStatus;
				}
			}
		}
		throw new Exception("Invalid Test Result Status: " + status);
	}
}
