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
	
	public static <T> T doGet(String uri, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doGet(uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doPost(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doPost(uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doMultipart(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return HttpClientManager.doMultipart(uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
}