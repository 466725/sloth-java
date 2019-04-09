package com.framework.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PlatformDetector {
	protected final static Logger logger = LogManager.getLogger(PlatformDetector.class.getName());
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

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