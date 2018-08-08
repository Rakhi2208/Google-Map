package com.teamsankya.mapgoogleapi.jmsproducer;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import com.teamsankya.mapgoogleapi.common.PropertyRead;



public class JMSProducer {
	private PropertyRead read=null;

	public void produce(String respose) throws JMSException {
		Logger logger = Logger.getLogger(JMSProducer.class);

		read = new PropertyRead();
		
		Layout layout = new SimpleLayout();
		Appender appender = new ConsoleAppender(layout);

		logger.addAppender(appender);

		//URL is the broker
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(read.getJmsUrl());
		logger.info("Creating Connection Factory Object");

		Connection connection = connectionFactory.createConnection();
		logger.info("Creating Connection via factory");

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		logger.info("Creating Queue via Session");

		Destination destination = session.createQueue(read.getQueueName());
		logger.info("Creating Queue via Session");
		
		MessageProducer messageProducer = session.createProducer(destination);
		logger.info("Creating MessageProducer via Session");
		
		TextMessage message = session.createTextMessage(respose);
		logger.info("Creating TextMessage via Session");
		messageProducer.send(message);
		logger.info("Sending the Message the ActiveMQ using Producer");
	}
}
