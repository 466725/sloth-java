package com.gurock.testrail;

import java.util.Properties;
import com.gurock.testrail.LocalEntities;
import com.gurock.testrail.enums.TestContextEnum;

/**
 * This class defines a test context which contains all the information for a given test thread.
 * e.g. orgCode, application DB connection information, application server, user and password.
 * Only one instance of this context will be created for a given test thread, one test can query/manipulate
 * information about their environment during whole execution.
 */
public class TestRunnerContext
{
	
	/**
	 * get the org code of the current test thread.
	 * 
	 * @return
	 */
	public static String getOrgCode()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.ORG_CODE);
	}
	
	/**
	 * set org code for the current test thread
	 * 
	 * @param orgCode
	 */
	public static void setOrgCode(String orgCode)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.ORG_CODE, orgCode);
	}
	
	/**
	 * retrieve application DB connection information for the current thread
	 * 
	 * @return
	 */
	public static Properties getDBConnInfo()
	{
		return (Properties) LocalEntities.getInstance().getEntity(TestContextEnum.APP_DB_CONN_INFO);
	}
	
	/**
	 * set application DB connection information for the current thread
	 * 
	 * @param p
	 */
	public static void setDBConnInfo(Properties p)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.APP_DB_CONN_INFO, p);
	}
	
	/**
	 * retrieve application server for the current thread
	 * 
	 * @return
	 */
	public static String getServer()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.SERVER);
	}
	
	/**
	 * set application server for the current thread
	 * 
	 * @param p
	 */
	public static void setServer(String server)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.SERVER, server);
	}
	
	/**
	 * retrieve application server user for the current thread
	 * 
	 * @return
	 */
	public static String getUser()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.USER);
	}
	
	/**
	 * set application server user for the current thread
	 * 
	 * @param user
	 */
	public static void setUser(String user)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.USER, user);
	}
	
	/**
	 * retrieve application server password for the current thread
	 * 
	 * @return
	 */
	public static String getPassword()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.PASSWORD);
	}
	
	/**
	 * set application server password for the current thread
	 * 
	 * @param password
	 */
	public static void setPassword(String password)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.PASSWORD, password);
	}
	
	/**
	 * Retrieve browser for the current thread
	 * 
	 * @return
	 */
	public static String getBrowser()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.BROWSER);
	}
	
	/**
	 * Set browser for the current thread
	 * 
	 * @param browser
	 */
	public static void setBrowser(String browser)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.BROWSER, browser);
	}
	
	/**
	 * Get the unique log id
	 * 
	 * @return
	 */
	public static String getUniqueLogId()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.UNIQUELOGID);
	}
	
	/**
	 * Set unique log id
	 * 
	 * @param ulogId
	 */
	public static void setUniqueLogId(String ulogId)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.UNIQUELOGID, ulogId);
	}
	
	/**
	 * Get the application version
	 * 
	 * @return
	 */
	public static String getAppVersion()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.APP_VERSION);
	}
	
	/**
	 * Set application version
	 * 
	 * @param version
	 */
	public static void setAppVersion(String version)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.APP_VERSION, version);
	}
	
	/**
	 * Get the application revision
	 * 
	 * @return
	 */
	public static String getAppRevision()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.APP_REVISION);
	}
	
	/**
	 * Set application revision
	 * 
	 * @param revision
	 */
	public static void setAppRevision(String revision)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.APP_REVISION, revision);
	}
	
	/**
	 * Retrieve remote for the current thread
	 * 
	 * @return
	 */
	public static String getRemote()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.REMOTE);
	}
	
	/**
	 * Set remote for the current thread
	 * 
	 * @param remote
	 */
	public static void setRemote(String remote)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.REMOTE, remote);
	}
	
	/**
	 * Get Default wait time for the current thread based on browser
	 * 
	 * @return
	 */
	public static int getDefaultWait()
	{
		return 30;
	}
	
	/**
	 * Get ajax timeout for the current thread based on browser
	 * 
	 * @return
	 */
	public static int getAjaxTimeout()
	{
		return 30;
	}
	
	/**
	 * clean all entities for the current thread
	 */
	public static void cleanContext()
	{
		LocalEntities.getInstance().clean();
	}
	
	/**
	 * retrieve Admin Console for the current thread
	 * 
	 * @return
	 */
	public static String getAdminConsole()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.ADMIN_CONSOLE);
	}
	
	/**
	 * set Admin Console for the current thread
	 * 
	 * @param adminConsole
	 */
	public static void setAdminConsole(String adminConsole)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.ADMIN_CONSOLE, adminConsole);
	}
	
	/**
	 * retrieve REST API URL for the current thread
	 * 
	 * @return
	 */
	public static String getRestAPIURL()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.REST_API_URL);
	}
	
	/**
	 * set REST API URL for the current thread
	 * 
	 * @param restApiURL
	 */
	public static void setRestAPIURL(String restApiURL)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.REST_API_URL, restApiURL);
	}
	
	/**
	 * retrieve CMS API URL for the current thread
	 * 
	 * @return
	 */
	public static String getCmsAPIURL()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.CMS_API_URL);
	}
	
	/**
	 * set CMS API URL for the current thread
	 * 
	 * @param p
	 */
	public static void setCmsAPIURL(String cmsApiURL)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.CMS_API_URL, cmsApiURL);
	}
	
	/**
	 * retrieve ignorePassed flag for the current thread
	 * 
	 * @return
	 */
	public static String getIgnorePassed()
	{
		return (String) LocalEntities.getInstance().getEntity(TestContextEnum.IGNORE_PASSED);
	}
	
	/**
	 * set ignorePassed flag for the current thread
	 * 
	 * @param ignorePassed
	 */
	public static void setIgnorePassed(String ignorePassed)
	{
		LocalEntities.getInstance().setEntity(TestContextEnum.IGNORE_PASSED, ignorePassed);
	}
}
