package com.framework.helpers;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RestApiHelper {

    protected final static Logger logger = LogManager.getLogger(RestApiHelper.class.getName());
    public final static String REST_API_SERVER = "http://162.209.124.9";
    public final static String REST_API_PORT = "8084";
    public final static String REST_API_HOST = REST_API_SERVER + ":" + REST_API_PORT;
    public final static String POLARIS_PROXY_SERVER = "172.27.112.112";
    public final static int POLARIS_PROXY_PORT = 3128;

    public static CloseableHttpClient setConnection() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        return httpClient;
    }

    /**
     * Set Get Request Header
     *
     * @param url
     * @return
     */
    public static HttpGet setGetRequestHeader(String url) {
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("content-type", "application/json");
        return getRequest;
    }

    /**
     * Set Put Request Header
     *
     * @param url
     * @return
     */
    public static HttpPut setPutRequestHeader(String url) {
        HttpPut putRequest = new HttpPut(url);
        putRequest.addHeader("Content-Type", "application/json");
        putRequest.addHeader("Accept", "application/json");
        return putRequest;
    }

    /**
     * Set Post Request Header
     *
     * @param url
     * @return
     */
    public static HttpPost setPostRequestHeader(String url) {
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "text/xml");
        postRequest.addHeader("Accept", "text/xml");
        return postRequest;
    }

    /**
     * Get JSon By Get Request With Url
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String getJSonByGetRequestWithUrl(String url) throws ClientProtocolException, IOException {
        logger.info("RestAPI: Executing get request url:[" + url + "]");
        CloseableHttpClient httpClient = setConnection();
        HttpGet request = setGetRequestHeader(url);
        HttpResponse result = httpClient.execute(request);
        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
        return json;
    }

    /**
     * Put Json Object Throw RestAPI
     *
     * @param url
     * @param json
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void putJsonObjectThrowRestAPI(String url, String json) throws ClientProtocolException, IOException {
        logger.info("RestAPI: Executing put request url:[" + url + "] json object:[" + json + "]");
        CloseableHttpClient httpClient = setConnection();
        HttpPut putRequest = setPutRequestHeader(url);
        StringEntity input = null;
        try {
            input = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        putRequest.setEntity(input);
        HttpResponse response = httpClient.execute(putRequest);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }
    }

    /**
     * Get Post Request Response (status code:[204] status:[HTTP/1.1 204 No
     * Content]) IS FINE!!!
     *
     * @param url
     * @param data
     * @return
     * @throws ClientProtocolException
     * @throws IOException             JSONObject inputJsonObj = new JSONObject();
     *                                 inputJsonObj.put("userId", userId);
     *                                 inputJsonObj.put("productId", productId);
     *                                 HttpResponse httpResponse =
     *                                 RestAPIHelper.getPostRequestResponse(url,
     *                                 inputJsonObj.toString());
     */
    public static HttpResponse getPostRequestResponse(String url, String data)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = setConnection();
        HttpPost request = setPostRequestHeader(url);
        // request.setEntity(new UrlEncodedFormEntity(new ArrayList<NameValuePair>()));
        StringEntity input = null;
        try {
            input = new StringEntity(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(input);
        logger.info("RestAPI: Executing post request url:[" + url + "] ");
        // + "with data json:["+data+"]");
        HttpResponse result = httpClient.execute(request);
        return result;
    }

    /**
     * Get GET RequestResponse
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponse getGetRequestResponse(String url) throws ClientProtocolException, IOException {
        logger.info("RestAPI: Get request response url:[" + url + "]");
        CloseableHttpClient httpClient = setConnection();
        HttpGet request = setGetRequestHeader(url);
        HttpResponse result = httpClient.execute(request);
        return result;
    }

    /**
     * Get Response Status Code
     *
     * @param response
     * @return
     */
    public static String getResponseStatusCode(HttpResponse response) {
        StatusLine statusLine = response.getStatusLine();
        String statusCode = String.valueOf(statusLine.getStatusCode());
        logger.info("RestAPI: Response status code:[" + statusCode + "] status:[" + statusLine + "]");
        return statusCode;
    }

    /**
     * Get ResponseBody as a String
     *
     * @param httpResponse
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String getResponseBody(HttpResponse httpResponse) throws ParseException, IOException {
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }
}
