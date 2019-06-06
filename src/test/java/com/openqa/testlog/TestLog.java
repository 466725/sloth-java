package com.openqa.testlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

/**
 * A static logging tool that automatically keeps track of what tests are being
 * executed by what threads, and allows for parsing of the global log specific
 * to an individual test at a later point. The beginning of a test must be
 * initialized using a unique test identifier, and should be deinitialized after
 * the test is complete in order to prevent that same thread from logging
 * actions against the test accidentally.
 */
public class TestLog
{
	
	private static ArrayList<Entry> logEntries = new ArrayList<Entry>();
	private static ThreadLocal<Boolean> beVerbose = new ThreadLocal<Boolean>();
	private static ThreadLocal<Integer> stepDelay = new ThreadLocal<Integer>();
	public static final int STEP = 0;
	public static final int DEBUG = 1;
	public static final int INIT = 2;
	public static final int DONE = 4;
	public static final int SERVER = 5;
	public static final int ORGCODE = 6;
	public static final int FACILITY = 7;
	public static final int APP_VERSION = 8;
	public static final int USERNAME = 9;
	public static final int SCENARIO_ID = 10;
	public static final int VERIFY = 11;
	public static final int CONSOLE = 12;
	public static final int BROWSER = 13;
	public static final int REMOTE = 14;
	
	/**
	 * Initialize the test logger to tell it that the current thread will be
	 * running the given test
	 * 
	 * @param uniqueLogId
	 */
	public static void initialize(String uniqueLogId)
	{
		beVerbose.set(false);
		newEntry(uniqueLogId, INIT);
	}
	
	/**
	 * Deinitialize the test logger from the given test for the current thread.
	 * This should be done after the test is complete to prevent accidental logging
	 * against the test thread.
	 * 
	 * @param uniqueLogId
	 */
	public static void done(String uniqueLogId)
	{
		newEntry(uniqueLogId, DONE);
		beVerbose.set(false);
	}
	
	/**
	 * Log a step for future debugging or failure analysis
	 * 
	 * @param step
	 */
	public static void step(String step)
	{
		newEntry(step, STEP);
		if (stepDelay.get() != null && stepDelay.get().intValue() > 0)
		{
			try
			{
				Thread.sleep(stepDelay.get().intValue());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Log a verification for future debugging or failure analysis
	 * 
	 * @param verification
	 */
	public static void verify(String verify)
	{
		newEntry(verify, VERIFY);
	}
	
	/**
	 * Log a piece of console information. Not used externally at the moment.
	 * 
	 * @param verification
	 */
	@SuppressWarnings("unused")
	private static void console(String log)
	{
		newEntry(log, CONSOLE);
	}
	
	/**
	 * Log debug information for future debugging or failure analysis
	 * 
	 * @param step
	 */
	public static void debug(String debug)
	{
		newEntry(debug, DEBUG);
	}
	
	public static void server(String server)
	{
		newEntry(server, SERVER);
	}
	
	public static void orgCode(String orgCode)
	{
		newEntry(orgCode, ORGCODE);
	}
	
	public static void facility(String facility)
	{
		newEntry(facility, FACILITY);
	}
	
	public static void appVersion(String appVersion)
	{
		newEntry(appVersion, APP_VERSION);
	}
	
	public static void userName(String userName)
	{
		newEntry(userName, USERNAME);
	}
	
	public static void scenarioId(String scenarioId)
	{
		newEntry(scenarioId, SCENARIO_ID);
	}
	
	public static void browser(String browser)
	{
		newEntry(browser, BROWSER);
	}
	
	public static void remote(String remote)
	{
		newEntry(remote, REMOTE);
	}
	
	/**
	 * @param action
	 * @param logType
	 */
	private static void newEntry(String action, int logType)
	{
		synchronized (logEntries)
		{
			Entry entry = new Entry(logType, action);
			logEntries.add(entry);
			if (beVerbose.get() != null && beVerbose.get())
				System.out.println(entry);
		}
	}
	
	/**
	 * Provides the full log for the given test unique id
	 * 
	 * @param uniqueLogId
	 * @return list of log entries
	 */
	public static ArrayList<Entry> getLogEntries(String uniqueLogId)
	{
		boolean inTest = false;
		long threadId = -1;
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for (int i = 0; i < logEntries.size(); i++)
		{
			Entry entry = logEntries.get(i);
			if (inTest && threadId == entry.getThreadID())
			{
				if (entry.getLogType() == INIT || entry.getLogType() == DONE)
					break;
				else
					entries.add(entry);
			}
			else if (entry.getLogType() == INIT)
			{
				threadId = entry.getThreadID();
				inTest = true;
			}
		}
		ArrayList<Entry> setup = new ArrayList<Entry>();
		inTest = false;
		for (int i = 0; i < logEntries.size(); i++)
		{
			Entry entry = logEntries.get(i);
			if (entry.getThreadID() == threadId)
			{
				if (inTest)
				{
					if (entry.getLogType() == DONE)
					{
						inTest = false;
						continue;
					}
					else if (entry.getLogType() != INIT)
						continue;
					else if (entry.getLogType() == INIT)
						inTest = false;
				}
				if (entry.getLogType() == INIT && stripParameters(entry.getAction()).equals(stripParameters(uniqueLogId)))
				{
					setup.addAll(entries);
					entries = setup;
					break;
				}
				else if (entry.getLogType() == INIT)
				{
					setup.clear();
					inTest = true;
				}
				else
					setup.add(entry);
			}
		}
		return entries;
	}
	
	public static void removeLogEntries(String uniqueLogId)
	{
		removeLogEntries(uniqueLogId, -1);
	}
	
	/**
	 * Remove log entries for the given test unique id
	 * 
	 * @param uniqueLogId
	 */
	public static void removeLogEntries(String uniqueLogId, long threadId)
	{
		if (threadId == -1)
		{
			for (int i = 0; i < logEntries.size(); i++)
			{
				Entry entry = logEntries.get(i);
				if (entry.getLogType() == INIT && entry.getAction().equals(uniqueLogId))
					if (threadId == -1)
					{
						threadId = entry.getThreadID();
						break;
					}
			}
		}
		synchronized (logEntries)
		{
			for (ListIterator<Entry> it = logEntries.listIterator(logEntries.size()); it.hasPrevious();)
			{
				Entry entry = it.previous();
				if (entry.getThreadID() == threadId)
					it.remove();
			}
		}
	}
	
	public static String getLogAsText(String uniqueLogId)
	{
		return entriesToString(getLogEntries(uniqueLogId));
	}
	
	public static ArrayList<String> getLogList(String uniqueLogId)
	{
		ArrayList<String> logList = new ArrayList<String>();
		ArrayList<Entry> entries = getLogEntries(uniqueLogId);
		int i = 0;
		for (Entry entry : entries)
		{
			i += (entry.getLogType() == STEP) ? 1 : 0;
			logList.add(entry.toString(i));
		}
		return logList;
	}
	
	public static String stripParameters(String test)
	{
		return test.replaceAll("\\[[-0123456789]+\\]", "");
	}
	
	/**
	 * Returns the entire log, inclusive of all running tests in entered order
	 * 
	 * @return String
	 */
	public static String getEntireLog()
	{
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for (Entry entry : logEntries)
			entries.add(entry);
		return entriesToString(entries);
	}
	
	/**
	 * Given an array list of entries, prints out those entries
	 * 
	 * @param entries
	 * @return
	 */
	public static String entriesToString(ArrayList<Entry> entries)
	{
		String out = "";
		int i = 0;
		for (Entry entry : entries)
		{
			i += (entry.getLogType() == STEP) ? 1 : 0;
			out += entry.toString(i) + "\n";
		}
		return out;
	}
	
	/**
	 * Allows you to set the verbosity. Passing true into this function will cause
	 * all logged logEntries to be printed to the console automatically. This
	 * setting is test specific, and is reset upon the beginning of a new test
	 * method.
	 * VERBOSITY SHOULD BE SET BY CONFIGURATION OPTION
	 * 
	 * @param verbose
	 */
	public static void setVerbose(String verbose)
	{
		if ("I_KNOW_THIS_SHOULD_ONLY_BE_SET_BY_CONFIGURATION_OPTIONS".equals(verbose))
			beVerbose.set(true);
		else
			beVerbose.set(false);
	}
	
	/**
	 * Return verbosity of test log
	 * 
	 * @return true means verbose
	 */
	public static boolean getVerbose()
	{
		return beVerbose.get();
	}
	
	/**
	 * Delay each logged step by a number of milliseconds to facilitate a more user
	 * observable test
	 * 
	 * @param delay
	 *            seconds to delay each step
	 */
	public static void delaySteps(int delay)
	{
		stepDelay.set(new Integer(delay * 1000));
	}
	
	/**
	 * Check for entry, if null, return null
	 * 
	 * @param uniqueLogId
	 * @param entryType
	 * @return
	 */
	private static String getEntryOrNull(String uniqueLogId, int entryType)
	{
		Entry entry = getEnvironmentEntry(uniqueLogId, entryType);
		return entry == null ? null : entry.getAction();
	}
	
	public static String getServer(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, SERVER);
	}
	
	public static String getOrgCode(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, ORGCODE);
	}
	
	public static String getAppVersion(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, APP_VERSION);
	}
	
	public static String getFacility(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, FACILITY);
	}
	
	public static String getUser(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, USERNAME);
	}
	
	public static String getScenarioId(String uniqueLogId)
	{
		return getEntryOrNull(uniqueLogId, SCENARIO_ID);
	}
	
	private static Entry getEnvironmentEntry(String uniqueLogId, int entryType)
	{
		ArrayList<Entry> entries = getLogEntries(uniqueLogId);
		for (int i = 0; i < entries.size(); i++)
		{
			Entry entry = entries.get(i);
			if (entry.getLogType() == entryType)
				return entry;
		}
		return null;
	}
	
	/**
	 * An inner Step class, tracking information about each step
	 */
	public static class Entry
	{
		
		private int logType;
		private long threadID;
		String action;
		Date timeStamp;
		int stepNumber = 0;
		
		public Entry(int logType, String action)
		{
			this.logType = logType;
			this.threadID = Thread.currentThread().getId();
			this.action = action;
			this.timeStamp = new Date();
		}
		
		public long getThreadID()
		{
			return this.threadID;
		}
		
		public String getAction()
		{
			return this.action;
		}
		
		public int getLogType()
		{
			return this.logType;
		}
		
		public String toString(int stepNum)
		{
			this.stepNumber = stepNum;
			String entryStr = "";
			String timestamp = new SimpleDateFormat("HH:mm:ss").format(this.timeStamp);
			entryStr = timestamp + " " + this.threadID + " " + this.logTypeToPrint() + " " + this.action;
			return entryStr;
		}
		
		private String logTypeToPrint()
		{
			if (this.logType == STEP)
				if (this.stepNumber == 0)
					return "[STEP]";
				else
					return "[STEP " + this.stepNumber + "]";
			else if (this.logType == VERIFY)
				return "[VERIFY]";
			else if (this.logType == DEBUG)
				return "[DEBUG]";
			else if (this.logType == INIT)
				return "[INIT]";
			else if (this.logType == DONE)
				return "[DONE]";
			else if (this.logType == SERVER)
				return "[SERVER]";
			else if (this.logType == ORGCODE)
				return "[ORGCODE]";
			else if (this.logType == FACILITY)
				return "[FACILITY]";
			else if (this.logType == APP_VERSION)
				return "[VERSION]";
			else if (this.logType == USERNAME)
				return "[USER]";
			else if (this.logType == SCENARIO_ID)
				return "[SCENARIO]";
			else if (this.logType == CONSOLE)
				return "[CONSOLE]";
			else if (this.logType == BROWSER)
				return "[BROWSER]";
			else if (this.logType == REMOTE)
				return "[REMOTE]";
			return "";
		}
	}
}
