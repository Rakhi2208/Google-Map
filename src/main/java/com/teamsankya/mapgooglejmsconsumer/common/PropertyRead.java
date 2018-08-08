package com.teamsankya.mapgooglejmsconsumer.common;

import java.io.FileReader;
import java.util.Properties;

public class PropertyRead {
	private static String jmsUrl;
	private static String queueName;
	private Properties properties =null;
	private FileReader fileReader = null;
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
