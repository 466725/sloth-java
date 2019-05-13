package config;

/**
 * Put all constant and ENUM here, please 
 * 
 * @author Weipeng Zheng
 *
 */
public final class Constants {
	public static final int IMPLICIT_WAIT_TIME = 5;
	public static final int EXPLICIT_WAIT_TIME = 10;
	public static final int PAGE_LOAD_TIME = 10;
	public static final int PAGE_RENDER_TIME = 1000; //sleep for one second
	
	public static final String RESOURCE_FOLDER = "src/test/resources/";
	public static final String EXTENT_REPORT_CONFIG = "extent-report-config.xml";
	public static final String TEST_REPORT_FOLDER = "test-output/ExtentReport/";
	public static final String SCREENSHOT_FOLDER = "test-output/ExtentReport/ScreenShot/";
	public static final String SITE_REQUEST_BODY = "ApiRequestBody/SiteMgmt/";

	public static final String WIN64_DRIVER_IE = RESOURCE_FOLDER + "IEDrivers/win/IEDriverServer-64.exe";
	public static final String WIN64_DRIVER_CHROME = RESOURCE_FOLDER + "ChromeDrivers/win/chromedriver-64.exe";
	public static final String WIN64_DRIVER_FIREFOX = RESOURCE_FOLDER + "FirefoxDrivers/win/geckodriver-64.exe";
	public static final String OSX64_DRIVER_CHROME = RESOURCE_FOLDER + "ChromeDrivers/osx/chromedriver-64";
	public static final String OSX64_DRIVER_FIREFOX = RESOURCE_FOLDER + "FirefoxDrivers/osx/geckodriver-64";

	public static final String LOCALHOST_POSTGRE_JDBC_URL = "jdbc:postgresql://localhost:5432";
	public static final String LOCALHOST_POSTGRE_JDBC_USERNAME = "postgres";
	public static final String LOCALHOST_POSTGRE_JDBC_PASSWORD = "Allen91$";
	
	public static final String LOCALHOST_SYBASE_JDBC_URL = "jdbc:sqlanywhere:ENG=test;links=tcpip(host=localhost;port=60000;verify=no)";
	public static final String LOCALHOST_SYBASE_JDBC_USERNAME = "vsupport";
	public static final String LOCALHOST_SYBASE_JDBC_PASSWORD = "V01ant#9VU";
	
	public static final String SQL_EXAMPLE_001 = 
			"SELECT Name\n" + 
			"FROM Customers\n" + 
			"WHERE Phone='6476211311';";
	public static final String SQL_EXAMPLE_002 = 
			"SELECT *\n" + 
			"FROM Customers;";
	public static final String SQL_EXAMPLE_003 = 
			"SELECT *\n" + 
			"FROM TAX_TYPE;";
	
	public static enum DB_CONN_ENUM {
		LOCALHOST_POSTGRE, 
		LOCALHOST_SYBASE, 
		BAMBOO_SERVER_POSTGRE, 
		BAMBOO_SERVER_SYBASE
	}
	public static enum CLICK_METHOD_ENUM {
		CLICK, 
		SENDENTER, 
		SENDRETURN, 
		SUBMIT, 
		RUNJS
	}
}