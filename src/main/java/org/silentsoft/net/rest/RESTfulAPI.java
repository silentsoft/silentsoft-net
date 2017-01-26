package org.silentsoft.net.rest;

import java.util.function.Consumer;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.silentsoft.net.http.HttpClientManager;

/**
 * @author silentsoft
 */
public class RESTfulAPI {
	
	private static String uri;

	private static String root;
	
	/**
	 * call this method on child class's static initialization block after create dummy static init() method. 
	 * all you need to do is just call child class's dummy static init() method one time.
	 * reason of why to do this is just for prevent human mistake. if you do not want, which means
	 * if you want change uri and root any time, just write init code to static init method. (not at initialization block) 
	 * 
	 * @param uri <b>http://service-ip:port</b>/your-server
	 * @param root http://service-ip:port<b>/your-server</b>
	 */
	protected static void init(String uri, String root) {
		RESTfulAPI.uri = uri;
		RESTfulAPI.root = root;
	}
	
	private static String getURI(String api) {
		return uri + root + api;
	}
	
    protected static <T> T doGet(String api, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType);
	}
	
	protected static <T> T doGet(String api, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType, charsetName);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy, returnType);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy, returnType, charsetName);
	}
	
	protected static <T> T doGet(String api, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType, beforeRequest);
	}
	
	protected static <T> T doGet(String api, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy, returnType, beforeRequest);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy, returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doGet(String api, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doGet(String api, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(getURI(api), returnType, charsetName, beforeRequest, afterResponse);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy,returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doGet(String api, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(getURI(api), proxy, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType, charsetName);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType, charsetName);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType, beforeRequest);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType, beforeRequest);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doPost(String api, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(getURI(api), param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doPost(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(getURI(api), proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType, charsetName);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType, charsetName);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType, beforeRequest);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType, beforeRequest);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType, charsetName, beforeRequest);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doMultipart(String api, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType, beforeRequest, afterResponse);
	}
	
	protected static <T> T doMultipart(String api, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
}