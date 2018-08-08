package com.teamsankya.mapgoogleapi.controller;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamsankya.mapgoogleapi.common.PropertyRead;
import com.teamsankya.mapgoogleapi.common.SendMail;
import com.teamsankya.mapgoogleapi.jaxb.GeocodeResponse;
import com.teamsankya.mapgoogleapi.jmsproducer.JMSProducer;

@Controller
public class GoogleMapController {
	private static final Logger LOGGER = Logger.getLogger(GoogleMapController.class);
	// private static final String API_KEY =
	// "mopaO6bXadM-HvAkg3iiBwkDNs3qIMrGVuk26cxznJ";
	// private static final String API_KEY =
	// "eIPWXrltiPU-yYuGRN9Dj0LmpDVgZR0FsjVoi8c1vt";
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	private String home() {
		return "home";
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET)
	public String getResponse(@RequestParam("location") String location, HttpSession session) {
		PropertyRead read = new PropertyRead();

		// 1. get the client
		Client client = ClientBuilder.newClient();
		// 2.get the target through client
		WebTarget target = client.target(read.getMapUrl() + location);
		// 3. get the response
		Response response = target.request().accept(MediaType.APPLICATION_XML).get();
		// 4.process the response
		GeocodeResponse geocodeResponse = response.readEntity(GeocodeResponse.class);
		System.out.println(geocodeResponse.getResult().getAddressComponent().get(0).getLongName());
		// sending response to jms
		if (geocodeResponse != null) {
			String resp = "Long Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getLongName()
					+ "\n Short Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getShortName()
					+ "\n Latitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLat()
					+ "\n Longitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLng();
			
			JMSProducer jmsProducer = new JMSProducer();
			try {
				jmsProducer.produce(resp);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session.setAttribute("geocodeResponse", geocodeResponse);
		
		return "index";
	}

	@RequestMapping(path = "/send", method = RequestMethod.POST)
	public String sendMessage(HttpSession session, String mobileNumber) {
		GeocodeResponse geocodeResponse = (GeocodeResponse) session.getAttribute("geocodeResponse");
		PropertyRead read = new PropertyRead();
		String mediaType = null;

		if (read.getMediaType().equals("json")) {
			mediaType = MediaType.APPLICATION_JSON;
		} else {
			mediaType = MediaType.APPLICATION_XML;
		}
		final String MESSAGE = "Long Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getLongName() +
				"\n Short Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getShortName() + 
				"\n Latitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLat()
				+ "\n Longitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLng();

		Form form = new Form();
		form.param("apikey", read.getApiKey());
		form.param("message", MESSAGE);
		form.param("numbers", mobileNumber);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(read.getTextLocalUrl());
		Response response = target.request().accept(mediaType).post(Entity.form(form));
		System.out.println(response.readEntity(String.class));
		return "sent";
	}
	
	@RequestMapping(path = "/sendMail", method=RequestMethod.POST)
	public String sendMail(HttpSession session, @RequestParam("emailId") String emailId) {
		SendMail mail = new SendMail();
		GeocodeResponse geocodeResponse = (GeocodeResponse) session.getAttribute("geocodeResponse");
		String subject = "google map";
		String text =  "Long Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getLongName() +
				"\n Short Name = " + geocodeResponse.getResult().getAddressComponent().get(0).getShortName() + 
				"\n Latitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLat()
				+ "\n Longitude = " + geocodeResponse.getResult().getGeometry().getLocation().getLng();
		mail.sendMail(emailId, subject, text);
		return "sent";
	}
}
