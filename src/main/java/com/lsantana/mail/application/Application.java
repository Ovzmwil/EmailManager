package com.lsantana.mail.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.lsantana.mail.core.Mail;
import com.lsantana.mail.util.Smtp;

public class Application {

	public static void main(String args[]) {

		Properties properties = new Properties();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("configs/mail.properties"));
			properties.load(reader);
			
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			Mail mail = new Mail(username, password, Smtp.GMAIL);

			MimeMessage msg = mail.getMessage();
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("xpto@gmail.com", false));
			msg.setSubject("Just testing");
			msg.setText("Testing.");
			
//			msg.setContent(mail.setTextAndAttach(text, pathFile));
			
			Transport transport = mail.getTransport();

			transport.connect();
			Transport.send(msg);
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
