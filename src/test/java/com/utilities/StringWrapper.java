/**
 * 
 */
package com.utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * StringWrapper to host all String related methods for the convenience of
 * automation
 * 
 * @author Weipeng Zheng
 *
 */
public class StringWrapper {
	protected final static Logger logger = LogManager.getLogger(StringWrapper.class.getName());

	/**
	 * Check if a Object is null
	 * 
	 * @param str string to check
	 * @return true if the string is empty
	 */
	public static boolean isObjectNull(Object obj) {
		return obj != null;
	}

	/**
	 * Check if a String is null
	 * 
	 * @param str string to check
	 * @return true if the string is empty
	 */
	public static boolean isStringNull(String str) {
		return str != null;
	}

	/**
	 * Check if a String is empty
	 * 
	 * @param str string to check
	 * @return true if the string is empty
	 */
	public static boolean isStringEmpty(String str) {
		return str.isEmpty();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
