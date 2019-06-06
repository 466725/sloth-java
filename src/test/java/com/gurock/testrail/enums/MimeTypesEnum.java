package com.gurock.testrail.enums;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * An enum defining the some common mime types used in HTTP headers
 */
public enum MimeTypesEnum
{
	PDF("application/pdf"), HTML("text/html"), XML("application/xml"), JAR("application/java-archive"), JPEG("image/jpeg"), ZIP("application/zip");
	
	private String mimeType;
	private static final Map<String, MimeTypesEnum> lookup = new HashMap<String, MimeTypesEnum>();
	
	private static boolean isValidMimeType(String mimeType)
	{
		return lookup.containsKey(mimeType);
	}
	
	static
	{
		for (MimeTypesEnum mime : MimeTypesEnum.values())
		{
			lookup.put(mime.getMimeType(), mime);
		}
	}
	
	/**
	 * Gets the file format/extension in lower case given the mime type
	 * 
	 * @param mimeType
	 * @return file format/extension in lower case
	 */
	public static String get(String mimeType)
	{
		if (isValidMimeType(mimeType))
		{
			return ((MimeTypesEnum) lookup.get(mimeType)).toString();
		}
		throw new InvalidParameterException("Invalid mimeType: " + mimeType);
	}
	
	private MimeTypesEnum(String mimeType)
	{
		this.mimeType = mimeType;
	}
	
	/**
	 * Getter method for the mime type
	 * 
	 * @return the mime type
	 */
	public String getMimeType()
	{
		return this.mimeType;
	}
}
