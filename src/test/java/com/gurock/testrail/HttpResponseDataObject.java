package com.gurock.testrail;

import config.HTTPStatusCodes;

/**
 * HttpResponseDataObject class for REST API HttpResponse data
 */
public class HttpResponseDataObject {

	private String statusCode = new String();
	private String responseBody = new String();

	public HttpResponseDataObject() {
		this.statusCode = new String("0");
		this.responseBody = new String("");
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = new String(statusCode);
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public boolean isCallSuccesfull() {
		String code = String.valueOf(HTTPStatusCodes.OK.getCode());
		if (statusCode.compareTo(code) == 0)
			return true;
		return false;
	}
}
