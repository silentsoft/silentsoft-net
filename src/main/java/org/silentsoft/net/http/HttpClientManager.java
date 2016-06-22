package org.silentsoft.net.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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
		return doGet(uri, null, returnType);
	}
	
	public static <T> T doGet(String uri, Header[] headers, Class<T> returnType) throws Exception {
		return doAction(uri, headers, null, returnType, RequestType.GET);
	}
	
	public static <T> T doPost(String uri, Object param, Class<T> returnType) throws Exception {
		return doPost(uri, null, param, returnType);
	}
	
	public static <T> T doPost(String uri, Header[] headers, Object param, Class<T> returnType) throws Exception {
		return doAction(uri, headers, param, returnType, RequestType.POST);
	}
	
	public static <T> T doMultipart(String uri, Object param, Class<T> returnType) throws Exception {
		return doMultipart(uri, null, param, returnType);
	}
	
	public static <T> T doMultipart(String uri, Header[] headers, Object param, Class<T> returnType) throws Exception {
		return doAction(uri, headers, param, returnType, RequestType.MULTIPART);
	}
	
	private static <T> T doAction(String uri, Header[] headers, Object param, Class<T> returnType, RequestType requestType) throws Exception {
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
					
					httpResponse = execute(httpGet, headers);
					break;
				}
				case POST:
				{
					httpPost = new HttpPost(uri);
					
					if (param != null) {
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
					
					httpResponse = execute(httpPost, headers);
					break;
				}
				case MULTIPART:
				{
					MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8"));
					
					if (param instanceof StoreItem) {
						StoreItem storeItem = (StoreItem) param;
						
						boolean hasContainsDirectory = storeItem.stream().anyMatch(filePOJO -> filePOJO.isDirectory() == true);
						if (hasContainsDirectory) {
							throw new Exception("Cannot contain directory to StoreItem !");
						}
						
						ObjectBackupManager fileBackupManager = new ObjectBackupManager();
						ObjectBackupManager bytesBackupManager = new ObjectBackupManager();
						
						for (FilePOJO filePOJO : storeItem) {
							if (filePOJO.getFile() != null) {
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
					
					httpResponse = execute(httpPost, headers);
					break;
				}
			}
			
			httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream content = httpEntity.getContent();
				if (content != null) {
					try {
						returnValue = (T) new ObjectMapper().readValue(content, returnType);
					} catch (Exception e) {
						returnValue = null;
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
	
	private static CloseableHttpResponse execute(HttpUriRequest request, Header[] headers) throws IOException, ClientProtocolException {
		request.setHeaders(headers);
		
		return HttpClientFactory.getHttpClient().execute(request);
	}
}