package com.gurock.testrail.models;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gurock.testrail.enums.HTTPRequestEnum;
import com.gurock.testrail.enums.MimeTypesEnum;
import com.utilities.StringUtils;

/**
 * The HTTPRequestProcessor wrap a set of method to help send a HTTP Request
 */
public class HTTPRequestProcessor {
	protected final static Logger logger = LogManager.getLogger(HTTPRequestProcessor.class.getName());
	private HttpURLConnection con = null;

	/**
	 * Constructor. set basic HttpURLConnection for the given url
	 * 
	 * @param url
	 * @throws Exception
	 */
	public HTTPRequestProcessor(String url) throws Exception {
		try {
			// For SSL certificate
			if (System.getProperty("javax.net.ssl.trustStore") == null)
				System.setProperty("javax.net.ssl.trustStore", "config/security/jssecacerts");
			this.con = (HttpURLConnection) new URL(url).openConnection();
			if (this.con == null)
				throw new Exception("Cannot set up HTTP connection to [" + url + "]");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Set headers for the HTTP request
	 * 
	 * @param headers
	 */
	public void setRequestHeaders(HashMap<String, String> headers) {
		if (headers == null)
			return;
		Set<String> properties = headers.keySet();
		for (String property : properties) {
			this.con.setRequestProperty(property, headers.get(property));
		}
	}

	/**
	 * Set basic authorization for the HTTP request
	 * 
	 * @param user
	 * @param password
	 */
	public void setBasicAuth(String user, String password) {
		if (StringUtils.isEmpty(user))
			return;
		this.con.setRequestProperty(HTTPRequestEnum.HTTPRequestHeader.AUTHORIZATION.getProperty(),
				"Basic " + new String(Base64.encodeBase64((user + ":" + password).getBytes())));
	}

	/**
	 * Set Cookies for the HTTP request
	 * 
	 * @param cookies
	 */
	public void setCookies(List<HttpCookie> cookies) {
		if (cookies == null)
			return;
		String cookieString = "";
		int size = cookies.size();
		for (int i = 0; i < size - 1; i++) {
			cookieString += cookies.get(i).getName() + "=" + cookies.get(i).getValue() + "; ";
		}
		cookieString += cookies.get(size - 1).getName() + "=" + cookies.get(size - 1).getValue();
		con.setRequestProperty(HTTPRequestEnum.HTTPRequestHeader.COOKIE.getProperty(), cookieString);
	}

	/**
	 * Set request method for the HTTP request. and open the URL connection's output
	 * for POST/PUT method
	 * 
	 * @param method
	 * @throws Exception
	 */
	public void setRequestMethod(HTTPRequestEnum.HTTPRequestMethod method) throws Exception {
		if (method == null)
			return;
		try {
			con.setRequestMethod(method.name());
			if ((method == HTTPRequestEnum.HTTPRequestMethod.POST) || (method == HTTPRequestEnum.HTTPRequestMethod.PUT))
				con.setDoOutput(true);
		} catch (ProtocolException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Set request message for the HTTP connection
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void setRequestMessage(String data) throws Exception {
		if (data != null) {
			if (!con.getDoOutput()) {
				throw new Exception(
						"This method [" + con.getRequestMethod() + "] does not support request body message!");
			}
			OutputStreamWriter out;
			try {
				out = new OutputStreamWriter(con.getOutputStream());
				out.write(data);
				out.flush();
				out.close();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
	}

	/**
	 * Send Http Request and return response object
	 * 
	 * @param isFile
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse doRequest(boolean isFile) throws Exception {
		HTTPResponse response = new HTTPResponse();
		try {
			// send request, and set response status code back to HTTPResponse
			response.setStatus(con.getResponseCode());
			// capture response
			if (isFile)
				response.setFilename(captureResponseAsAFile());
			else
				response.setMessage(captureResponseAsAString());
		} catch (IOException e) {
			throw new Exception(e);
		}
		return response;
	}

	/**
	 * Close the HTTP URL connection
	 */
	public void close() {
		if (con != null)
			con.disconnect();
	}

	/**
	 * send a HTTP request and get the response
	 * 
	 * @param method
	 * @param headers
	 * @param cookies
	 * @param data
	 * @param user
	 * @param password
	 * @param isFile
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse sendHttpRequest(HTTPRequestEnum.HTTPRequestMethod method, HashMap<String, String> headers,
			List<HttpCookie> cookies, String data, String user, String password, boolean isFile) throws Exception {
		// Set authorization
		setBasicAuth(user, password);
		// Set request header
		setRequestHeaders(headers);
		// Set request cookies
		setCookies(cookies);
		// Set request method
		setRequestMethod(method);
		// Set request message
		setRequestMessage(data);
		// Send request
		HTTPResponse response = doRequest(isFile);
		// Close connection
		close();
		return response;
	}

	/**
	 * send a POST HTTP request and get the response
	 * 
	 * @param headers
	 * @param data
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse sendHttpRequest(HashMap<String, String> headers, String data, String user, String password)
			throws Exception {
		return sendHttpRequest(HTTPRequestEnum.HTTPRequestMethod.POST, headers, null, data, user, password, false);
	}

	/**
	 * send a GET HTTP request and get the response
	 * 
	 * @param headers
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse sendHttpRequest(HashMap<String, String> headers, String user, String password)
			throws Exception {
		return sendHttpRequest(HTTPRequestEnum.HTTPRequestMethod.GET, headers, null, null, user, password, false);
	}

	/**
	 * send a POST HTTP request and get the response
	 * 
	 * @param headers
	 * @param isFile
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse sendHttpRequest(HashMap<String, String> headers, String data) throws Exception {
		return sendHttpRequest(HTTPRequestEnum.HTTPRequestMethod.POST, headers, null, data, null, null, false);
	}

	/**
	 * send a GET HTTP request and get the response
	 * 
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public HTTPResponse sendHttpRequest(HashMap<String, String> headers) throws Exception {
		return sendHttpRequest(HTTPRequestEnum.HTTPRequestMethod.GET, headers, null, null, null, null, false);
	}

	/**
	 * Save response message in a file, and return the filename
	 * 
	 * @return
	 * @throws IOException
	 */
	private String captureResponseAsAFile() throws IOException {
		// Capture response, which should be the new entity
		String filename = "Response_" + StringUtils.generateUniqueIdentifier();
		String contentType = con.getContentType();
		contentType = contentType.indexOf(";") > -1 ? contentType.substring(0, contentType.indexOf(";")) : contentType;
		filename = filename.concat("." + (String) MimeTypesEnum.get(contentType).toLowerCase());
		// writing the downloaded data into the file we created
		FileOutputStream fileOutput = new FileOutputStream(filename);
		// this will be used in reading the data from the internet
		BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getInputStream());
		int downloadedSize = 0;
		// create a buffer...
		byte[] buffer = new byte[1024];
		int bufferLength = 0; // used to store a temporary size of the buffer
		// Reading through the input buffer and write the contents to the file
		try {
			while ((bufferLength = bufferedInputStream.read(buffer)) > 0) {
				// add the data in the buffer to the file in the file output stream
				fileOutput.write(buffer, 0, bufferLength);
				// adding up the size
				downloadedSize += bufferLength;
				// reporting the progress:
				logger.info("This much downloaded: " + downloadedSize);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (fileOutput != null)
				fileOutput.close();
		}
		return filename;
	}

	/**
	 * Return response message as a string
	 * 
	 * @return
	 * @throws IOException
	 */
	private String captureResponseAsAString() throws IOException {
		String response = "";
		// Capture response, which should be the new entity
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
			response += line;
		}
		in.close();
		return response;
	}
}
