package com.atbm.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailUtil {
	private static final Logger LOGGER = Logger.getLogger(EmailUtil.class.getName());
	private static final String USERNAME = "lydung853@gmail.com";
	private static final String PASSWORD = "powj nhms wkyz tyky";

	public static boolean sendEmail(String to, String subject, String content) {
		LOGGER.info("Chuẩn bị gửi email đến: " + to + ", Chủ đề: " + subject);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			LogUtils.debug(EmailUtil.class, "Lỗi khi gửi email: " + e.getMessage());
			return false;
		}
	}
}