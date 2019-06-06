package config;

/**
 * Enum class for HTTP Status Codes
 * Jira#:
 */
public enum HTTPStatusCodes
{
	OK(200), BAD_REQUEST(400), NO_CONTENT(204), UNAUTHORIZED(401), NOT_FOUND(404);
	
	private final int code;
	
	private HTTPStatusCodes(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return code;
	}
}
