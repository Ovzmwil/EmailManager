package com.lsantana.mail.util;

import java.util.Properties;

public class MailProperties {
	
	protected static final String username = "";
	protected static final String password = "";
	
	protected Properties prop;
	
	public Properties setMailProperties(Smtp smtp) {
		prop = System.getProperties();
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");	
		prop.put("mail.debug", "true");
		
		switch (smtp) {
		case GMAIL:
			prop.setProperty("mail.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "465");
		    prop.put("mail.smtp.socketFactory.port", "465");
		    prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		    prop.put("mail.smtp.socketFactory.fallback", "false");  
			break;
		case OUTLOOK:
			prop.setProperty("mail.host", "smtp.live.com");
			prop.put("mail.smtp.port", "587");
			break;
		case OFFICE365:
			prop.setProperty("mail.host", "smtp.office365.com");
			prop.put("mail.smtp.port", "587");
			break;
		case YAHOO:
			prop.setProperty("mail.host", "smtp.mail.yahoo.com");
			prop.put("mail.smtp.port", "465");
		    prop.put("mail.smtp.socketFactory.port", "465");
		    prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		    prop.put("mail.smtp.socketFactory.fallback", "false");  
			break;
		default:
			prop = null;
			break;
		}
		return prop;
	}

}
