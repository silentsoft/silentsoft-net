package org.silentsoft.net.rest;

import org.apache.http.Header;
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
	
	public static void doGet(String api, Header[] headers) throws Exception {
		doGet(api, headers, null);
	}
	
	public static <T> T doGet(String api, Header[] headers, Class<T> returnType) throws Exception {
		return HttpClientManager.doGet(getURI(api), headers, returnType);
	}
	
	public static void doPost(String api, Header[] headers, Object param) throws Exception {
		doPost(api, headers, param, null);
	}
	
	public static <T> T doPost(String api, Header[] headers, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doPost(getURI(api), headers, param, returnType);
	}
	
	public static void doMultipart(String api, Header[] headers, Object param) throws Exception {
		doMultipart(api, headers, param, null);
	}
	
	public static <T> T doMultipart(String api, Header[] headers, Object param, Class<T> returnType) throws Exception {
		return HttpClientManager.doMultipart(getURI(api), headers, param, returnType);
	}
	
}