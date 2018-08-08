package com.teamsankya.mapgooglejmsconsumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import com.teamsankya.mapgooglejmsconsumer.common.PropertyRead;

public class JMSConsumer {

	private static PropertyRead read = null;

	public static void main(String[] args) throws JMSException {
		Logger logger = Logger.getLogger(JMSConsumer.class);

		read = new PropertyRead(); 
		Layout layout = new SimpleLayout();
		Appender appender = new ConsoleAppender(layout);

		logger.addAppender(appender);

		// URL is the broker
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(read.getJmsUrl());
		logger.info("Creating Connection Factory Object");

		Connection connection = connectionFactory.createConnection();
		connection.start();
		logger.info("Creating Connection via factory");

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		logger.info("Creating Queue via Session");

		Destination destination = session.createQueue(read.getQueueName());
		logger.info("Creating Queue via Session");

		MessageConsumer messageConsumer = session.createConsumer(destination);
		logger.info("Creating MessageConsumer via Session");

		Message message = messageConsumer.receive();
		logger.info("Recieving the Message from the queue");

		if(((TextMessage) message).getText().contains("Latitude")) {
		logger.info(((TextMessage) message).getText());
		}else {
			logger.fatal(((TextMessage) message).getText());
		}
		System.out.println(((TextMessage) message).getText());
		//session.close();
		//connection.close();

	}// End of Main
}// End of class
