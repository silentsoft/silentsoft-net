package org.silentsoft.net.push.pushbullet;

import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.silentsoft.net.rest.RESTfulAPI;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PushbulletAPI extends RESTfulAPI {

	static {
		init("https://api.pushbullet.com", "/v2");
	}
	
	public enum TargetType {
		device_iden,
		client_iden,
		channel_tag,
		email
	}
	
	public static UserInfo getUserInfo(String accessToken) throws Exception {
		return doGet("/users/me", new Header[]{new BasicHeader("Access-Token", accessToken)}, UserInfo.class);
	}
	
	public static List<Device> getDevices(String accessToken) throws Exception {
		Devices devices = doGet("/devices", new Header[]{new BasicHeader("Access-Token", accessToken)}, Devices.class);
		
		return (devices == null) ? null : devices.getDevices();
	}
	
	public static Push sendNote(String accessToken, TargetType type, String target, String title, String body) throws Exception {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("type", "note");
		param.put(type.name(), target);
		param.put("title", title);
		param.put("body", body);
		
		return doPost("/pushes", new Header[]{new BasicHeader("Access-Token", accessToken)}, new ObjectMapper().writeValueAsString(param), Push.class);
	}
	
}
