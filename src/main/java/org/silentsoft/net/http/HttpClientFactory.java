package org.silentsoft.net.http;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {
	
	private static final int MAX_TOTAL = 300;
	private static final int MAX_PER_ROUTE = 20;
	
	private static CloseableHttpClient httpClient;
	private static PoolingHttpClientConnectionManager httpManager;
	
	public synchronized static CloseableHttpClient getHttpClient() {
		if (httpClient != null) {
			return httpClient;
		}
		
		httpManager = new PoolingHttpClientConnectionManager();
		httpManager.setMaxTotal(MAX_TOTAL);
		httpManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		
		httpClient = HttpClients.custom().setConnectionManager(httpManager).build();
		
		return httpClient;
	}
}
