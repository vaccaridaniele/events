package org.e2d.smartfarm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public EmailUtil() {

	}

	/**
	 * Send email
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @return true/false
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public boolean sendEmail(String to, String subject, String text) throws AddressException, MessagingException {

		String from = "info@e2d.it";
		final String username = "info@e2d.it";
		final String password = "startup2015";
		String host = "smtp.e2d.it";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("info@e2d.it"));
			message.setSubject(subject);
			// message.setText(text);
			message.setContent(text, "text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}