package com.teamsankya.mapgoogleapi.common;

import java.io.FileReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	//load the property file
	private Properties properties = new Properties();
	private FileReader fileReader = null;
	public void sendMail(String to,String subject, String text) {
		try {
		fileReader = new FileReader(getClass().getResource("/").getPath()+"mail.properties");
		properties.load(fileReader);
		
		// Get the Session object.
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Credentials.USERNAME, Credentials.PASSWORD);
			}
		});
		
		// Create a default MimeMessage object.
		Message message = new MimeMessage(session);
		// Set From: header field of the header.
			message.setFrom(new InternetAddress(Credentials.USERNAME));
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(to));
			// Set Subject: header field
			message.setSubject(subject);
			// Now set the actual message
			message.setText(text);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
