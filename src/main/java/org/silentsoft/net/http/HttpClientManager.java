package org.silentsoft.net.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
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

public class HttpClientManager {
	
	static {
		/**
		 * WARNING : DO NOT REMOVE BELOW CODE ! THIS IS VERY IMPORTANT FOR PERFORMANCE !!!
		 */
		Logger.getLogger("org.apache.http").setLevel(Level.OFF);
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
		return doAction(RequestType.GET, uri, null, returnType, null, null);
	}
	
	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.GET, uri, null, returnType, beforeRequest, null);
	}
	
	public static <T> T doGet(String uri, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.GET, uri, null, returnType, beforeRequest, afterResponse);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.POST, uri, param, returnType, null, null);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.POST, uri, param, returnType, beforeRequest, null);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.POST, uri, param, returnType, beforeRequest, afterResponse);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType) throws Exception {
		return doAction(RequestType.MULTIPART, uri, param, returnType, null, null);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest) throws Exception {
		return doAction(RequestType.MULTIPART, uri, param, returnType, beforeRequest, null);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		return doAction(RequestType.MULTIPART, uri, param, returnType, beforeRequest, afterResponse);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T doAction(RequestType requestType, String uri, Object param, Class<T> returnType, Consumer<HttpRequest> beforeRequest, Consumer<HttpResponse> afterResponse) throws Exception {
		T returnValue = null;
		
		HttpGet httpGet = null;
		HttpPost httpPost = null;
		HttpEntity httpEntity = null;
		CloseableHttpResponse httpResponse = null;
		
		try {
			switch (requestType) {
				case GET:
				{
					httpGet = new HttpGet(uri);
					
					httpResponse = execute(httpGet, beforeRequest, afterResponse);
					
					break;
				}
				case POST:
				{
					httpPost = new HttpPost(uri);
					
					if (param != null) {
						boolean isUrlEncodedFormEntity = true;
						
						if (param instanceof List<?>) {
							for (Object obj : ((List<?>) param)) {
								if (obj instanceof NameValuePair == false) {
									isUrlEncodedFormEntity = false;
									break;
								}
							}
						}
						
						if (isUrlEncodedFormEntity) {
							UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity((List<? extends NameValuePair>) param, Charset.forName("UTF-8"));
							urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
							
							httpPost.setEntity(urlEncodedFormEntity);
						} else {
							String json = "";
							if (param instanceof String) {
								json = new JSONObject((String)param).toString();
							} else {
								json = new JSONObject(param).toString();
							}
							StringEntity stringEntity = new StringEntity(json, Charset.forName("UTF-8"));
							stringEntity.setContentType("application/json; charset=UTF-8");
							
							httpPost.setEntity(stringEntity);
						}
					}
					
					httpResponse = execute(httpPost, beforeRequest, afterResponse);
					
					break;
				}
				case MULTIPART:
				{
					MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8"));
					
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
					httpPost.setEntity(multipartEntityBuilder.build());
					
					httpResponse = execute(httpPost, beforeRequest, afterResponse);
					
					break;
				}
			}
			
			if (returnType != null) {
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					if (returnType == String.class) {
						returnValue = (T) EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
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