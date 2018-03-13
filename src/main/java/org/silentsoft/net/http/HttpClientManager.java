package org.silentsoft.net.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.silentsoft.net.item.StoreItem;
import org.silentsoft.net.pojo.FilePOJO;
import org.slf4j.helpers.MessageFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.markusbernhardt.proxy.ProxySearch;

public class HttpClientManager {
	
	static boolean useDefaultProxy = false;
	
	static {
		/**
		 * WARNING : DO NOT REMOVE BELOW CODE ! THIS IS VERY IMPORTANT FOR PERFORMANCE !!!
		 */
		Logger.getLogger("org.apache.http").setLevel(Level.OFF);
	}
	
	public static boolean isUseDefaultProxy() {
		return useDefaultProxy;
	}

	public static void setUseDefaultProxy(boolean useDefaultProxy) {
		HttpClientManager.useDefaultProxy = useDefaultProxy;
	}
	
	static HttpHost proxyHost;
	private static HttpHost getDefaultProxyHost(String dummyUri) {
		if (useDefaultProxy) {
			if (proxyHost == null) {
				List<Proxy> proxies = ProxySearch.getDefaultProxySearch().getProxySelector().select(URI.create(dummyUri));
				if (proxies != null && proxies.isEmpty() == false) {
					for (Proxy proxy : proxies) {
						SocketAddress socketAddress = proxy.address();
						if (socketAddress instanceof InetSocketAddress) {
							InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
							proxyHost = new HttpHost(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
							break;
						}
					}
				}
			}
		}
		
		return proxyHost;
	}
	
	static class ObjectBackupManager {
		private Map<Object, Object> map;
		private Map<Object, Object> getMap() {
			if (map == null) {
				map = new HashMap<Object, Object>();
			}
			
			return map;
		}
		
		public boolean backup(Object key, Object value) {
			if (key == null) {
				return false;
			}
			
			getMap().put(key, value);
			
			return true;
		}
		
		public Object restore(Object key) {
			return restore(key, true);
		}
		
		public Object restore(Object key, boolean remove) {
			if (key == null) {
				return null;
			}
			
			Object value = getMap().get(key);
			
			if (remove && getMap().containsKey(key)) {
				getMap().remove(key);
			}
			
			return value;
		}
		
		public void clean() {
			if (map != null) {
				map.clear();
				map = null;
			}
		}
	}
	
	private static enum RequestType {
		GET,
		POST,
		MULTIPART
	};
	
	public static <T> T doGet(String uri, Class<T> returnType) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doGet(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, Class<T> returnType) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doPost(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doPost(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, Class<T> returnType) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, charsetName, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, null, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, charsetName, null, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, charsetName, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, charsetName, beforeRequest, afterResponse);
	}

	public static <T> T doMultipart(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, null, param, returnType, null, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, null, returnType, null, beforeRequest, afterResponse);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, null, beforeRequest, null);
	}
	public static <T> T doMultipart(String uri, HttpHost proxy, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, proxy, param, returnType, null, beforeRequest, afterResponse);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T doAction(RequestType requestType, String uri, HttpHost proxy, Object param, Class<T> returnType, String charsetName, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		T returnValue = null;
		
		HttpGet httpGet = null;
		HttpPost httpPost = null;
		HttpEntity httpEntity = null;
		CloseableHttpResponse httpResponse = null;
		
		String _charsetName = (charsetName == null || "".equals(charsetName)) ? "UTF-8" : charsetName;
		
		try {
			switch (requestType) {
				case GET:
				{
					httpGet = new HttpGet(uri);
					if (proxy == null) {
						proxy = getDefaultProxyHost(uri);
					}
					if (proxy != null) {
						httpGet.setConfig(RequestConfig.custom().setProxy(proxy).build());
					}
					
					if (param != null) {
						if (isUrlEncodedFormEntity(param)) {
							httpGet.setURI(new URIBuilder(httpGet.getURI()).addParameters((List<NameValuePair>) param).build());
						}
					}
					
					httpResponse = execute(httpGet, beforeRequest, afterResponse);
					
					break;
				}
				case POST:
				{
					httpPost = new HttpPost(uri);
					if (proxy == null) {
						proxy = getDefaultProxyHost(uri);
					}
					if (proxy != null) {
						httpPost.setConfig(RequestConfig.custom().setProxy(proxy).build());
					}
					
					if (param != null) {
						if (isUrlEncodedFormEntity(param)) {
							UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity((List<? extends NameValuePair>) param, Charset.forName(_charsetName));
							urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded; charset=".concat(_charsetName));
							
							httpPost.setEntity(urlEncodedFormEntity);
						} else {
							String json = "";
							if (param instanceof String) {
								json = new JSONObject((String)param).toString();
							} else {
								json = new JSONObject(param).toString();
							}
							StringEntity stringEntity = new StringEntity(json, Charset.forName(_charsetName));
							stringEntity.setContentType("application/json; charset=".concat(_charsetName));
							
							httpPost.setEntity(stringEntity);
						}
					}
					
					httpResponse = execute(httpPost, beforeRequest, afterResponse);
					
					break;
				}
				case MULTIPART:
				{
					MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(Charset.forName(_charsetName));
					
					if (param instanceof StoreItem) {
						StoreItem storeItem = (StoreItem) param;
						
						/**
						 * If necessary, comment out the following logic when you do not want to transfer directory.
						 */
						boolean hasContainsDirectory = storeItem.stream().anyMatch(filePOJO -> filePOJO.isDirectory() == true);
						if (hasContainsDirectory) {
							throw new Exception("Cannot contain directory to StoreItem !");
						}
						
						ObjectBackupManager fileBackupManager = new ObjectBackupManager();
						ObjectBackupManager bytesBackupManager = new ObjectBackupManager();
						
						for (FilePOJO filePOJO : storeItem) {
							if (filePOJO.isDirectory() == false && filePOJO.getFile() != null) {
								multipartEntityBuilder.addBinaryBody("binary", filePOJO.getFile(), ContentType.APPLICATION_OCTET_STREAM, filePOJO.getNameWithExtension());
							}
							
							fileBackupManager.backup(filePOJO, filePOJO.getFile());
							bytesBackupManager.backup(filePOJO, filePOJO.getBytes());
							
							filePOJO.setFile(null);
							filePOJO.setBytes(null);
						}
						
						multipartEntityBuilder.addTextBody("json", new JSONObject(storeItem).toString(), ContentType.APPLICATION_JSON);
						
						for (FilePOJO filePOJO : storeItem) {
							filePOJO.setFile((File) fileBackupManager.restore(filePOJO));
							filePOJO.setBytes((byte[]) bytesBackupManager.restore(filePOJO));
						}
						
						fileBackupManager.clean();
						bytesBackupManager.clean();
					} else {
						return null;
					}
					
					httpPost = new HttpPost(uri);
					if (proxy == null) {
						proxy = getDefaultProxyHost(uri);
					}
					if (proxy != null) {
						httpPost.setConfig(RequestConfig.custom().setProxy(proxy).build());
					}
					httpPost.setEntity(multipartEntityBuilder.build());
					
					httpResponse = execute(httpPost, beforeRequest, afterResponse);
					
					break;
				}
			}
			
			if (returnType != null) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					if (returnType == String.class) {
						returnValue = (T) EntityUtils.toString(httpEntity, Charset.forName(_charsetName));
					} else {
						InputStream content = httpEntity.getContent();
						if (content != null) {
							try {
								returnValue = (T) new ObjectMapper().readValue(content, returnType);
							} catch (Exception e) {
								returnValue = null;
							}
						}
					}
				}
			}
			
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			throw new Exception(MessageFormatter.arrayFormat("Failed connection to <{}> !", new Object[]{uri}).getMessage(), e);
		} finally {			
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException ioe) {
					throw new Exception("Failed close to http response !", ioe);
				}
			}
		}
		
		return returnValue;
	}
	
	private static boolean isUrlEncodedFormEntity(Object param) {
		boolean isUrlEncodedFormEntity = false;
		
		if (param instanceof List<?>) {
			List<?> list = (List<?>) param;
			for (int i=0, j=list.size(); i<j; i++) {
				Object object = list.get(i);
				if (object instanceof NameValuePair) {
					if (i == j-1) {
						isUrlEncodedFormEntity = true;
					}
					
					continue;
				}
				
				break;
			}
		}
		
		return isUrlEncodedFormEntity;
	}
	
	private static CloseableHttpResponse execute(HttpUriRequest request, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws ClientProtocolException, IOException {
		CloseableHttpResponse httpResponse = null;
		
		if (beforeRequest != null) {
			beforeRequest.accept(request);
		}
		
		httpResponse = HttpClientFactory.getHttpClient().execute(request);
		
		if (afterResponse != null) {
			afterResponse.accept(httpResponse);
		}
		
		return httpResponse;
	}
	
}