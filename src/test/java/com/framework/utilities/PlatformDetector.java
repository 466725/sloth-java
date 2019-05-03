package com.framework.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Utility class to detect platform/OS smartly
 * 
 * @author Weipeng Zheng
 *
 */
public class PlatformDetector {
	protected final static Logger logger = LogManager.getLogger(PlatformDetector.class.getName());
	private static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * Check if OS is Windows
	 * 
	 * @return true, if platform is Windows
	 */
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * Check if OS is Mac
	 * 
	 * @return true, if platform is Mac
	 */
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	/**
	 * Check if OS is Unix
	 * 
	 * @return true, if platform is Unix
	 */
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	/**
	 * Check if OS is Solaris
	 * 
	 * @return true, if platform is Solaris
	 */
	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	/**
	 * Fetch OS and log it accordingly, to generate WebDriver smartly
	 * 
	 * @return String, platform info
	 */
	public static String getOS() {
		if (isWindows()) {
			logger.info("This is Windows");
			return "win";
		} else if (isMac()) {
			logger.info("This is Mac");
			return "osx";
		} else if (isUnix()) {
			logger.warn("This is Unix or Linux");
			return "uni";
		} else if (isSolaris()) {
			logger.error("This is Solaris");
			return "sol";
		} else {
			logger.fatal("Your OS is not support!!");
			return "err";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(OS);
		if (isWindows()) {
			logger.info("This is Windows");
		} else if (isMac()) {
			logger.info("This is Mac");
		} else if (isUnix()) {
			logger.warn("This is Unix or Linux");
		} else if (isSolaris()) {
			logger.error("This is Solaris");
		} else {
			logger.fatal("Your OS is not support!!");
		}
	}
}