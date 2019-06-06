/**
 * TestCaseID API binding for Java (API v2, available since TestCaseID 3.0)
 * Learn more:
 * http://docs.gurock.com/testrail-api2/start
 * http://docs.gurock.com/testrail-api2/accessing
 * Copyright Gurock Software GmbH. See license.md for details.
 */
package com.gurock.testrail;

public class APIException extends Exception
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9137579358179572215L;
	
	public APIException(String message)
	{
		super(message);
	}
}
