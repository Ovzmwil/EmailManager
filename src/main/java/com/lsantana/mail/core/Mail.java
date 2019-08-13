package com.lsantana.mail.core;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.lsantana.mail.util.Smtp;

public class Mail {
	
	private Session session;
	private Smtp smtp;
	private Properties prop;
	private Transport transport;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Smtp getSmtp() {
		return smtp;
	}

	public void setSmtp(Smtp smtp) {
		this.smtp = smtp;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public Mail(String username, String password, Smtp smtp) {
		this.smtp = smtp;
		setMailProperties(smtp);
		setSession(username, password);
		try {
			transport = session.getTransport();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} 
	}
	
	private Properties setMailProperties(Smtp smtp) {
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
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.port", "587");
			break;
		case OFFICE365:
			prop.setProperty("mail.host", "smtp.office365.com");
			prop.put("mail.smtp.starttls.enable", "true");
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
	
	private void setSession(final String username, final String password) {
		session = Session.getInstance(setMailProperties(smtp), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	public MimeMultipart setTextAndAttach(String text, String pathFile) {
		MimeBodyPart p1 = new MimeBodyPart();
		MimeBodyPart p2 = new MimeBodyPart();
		FileDataSource dataSource = new FileDataSource(pathFile);
		Multipart mp = new MimeMultipart();

		try {
			p1.setText(text);
			p2.setDataHandler(new DataHandler(dataSource));
			p2.setFileName(dataSource.getName());

			mp.addBodyPart(p1);
			mp.addBodyPart(p2);

			return (MimeMultipart) mp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public MimeMessage getMessage() {
		return new MimeMessage(session);
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

}
