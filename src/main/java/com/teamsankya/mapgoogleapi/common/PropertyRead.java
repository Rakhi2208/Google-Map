package com.teamsankya.mapgoogleapi.common;

import java.io.FileReader;
import java.util.Properties;

public class PropertyRead {
	private static String mapUrl;
	private static String textLocalUrl;
	private static String apiKey;
	private static String mediaType;
	private static String jmsUrl;
	private static String queueName;
	private Properties properties =null;
	private FileReader fileReader = null;
	public String getMapUrl() {

		properties = new Properties();
		try {
			fileReader = new FileReader(getClass().getResource("/").getPath()+"mapgoogle.properties");
			properties.load(fileReader);
			mapUrl = properties.getProperty("url");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapUrl;
	}
	
	public String getMediaType() {
		
		properties = new Properties();
		try {
			fileReader = new FileReader(getClass().getResource("/").getPath()+"mapgoogle.properties");
			properties.load(fileReader);
			mediaType = properties.getProperty("mediaType");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaType;
	}
	
	public String getTextLocalUrl() {
		
		properties = new Properties();
		try {
			fileReader = new FileReader(getClass().getResource("/").getPath()+"textlocal.properties");
			properties.load(fileReader);
			textLocalUrl = properties.getProperty("url");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textLocalUrl;
	}
	
public String getApiKey() {
		
		properties = new Properties();
		try {
			fileReader = new FileReader(getClass().getResource("/").getPath()+"textlocal.properties");
			properties.load(fileReader);
			apiKey = properties.getProperty("apikey");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apiKey;
	}

public String getJmsUrl() {
	
	properties = new Properties();
	try {
		fileReader = new FileReader(getClass().getResource("/").getPath()+"jms.properties");
		properties.load(fileReader);
		jmsUrl = properties.getProperty("url");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return jmsUrl;
}

public String getQueueName() {
	
	properties = new Properties();
	try {
		fileReader = new FileReader(getClass().getResource("/").getPath()+"jms.properties");
		properties.load(fileReader);
		queueName = properties.getProperty("queueName");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return queueName;
}

}
