package edu.porgamdor.util.desktop;

import java.io.File;
import java.net.PasswordAuthentication;
import java.util.Properties;


public class Email {
	/*private String[] emails;
	private String subject;
	private String attachPath;
	private String attachName;

	public void setEmails(String ... emails) {
		this.emails = emails;
	}

	public void setAttach(String attachPath, String attachName) {
		this.attachPath = attachPath;
		this.attachName = attachName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void send() throws Exception {
		final String username = "porgamador@gmail.com";
		final String password = "t3st3123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("porgamador@gmail.com"));
			InternetAddress[] toAddress = new InternetAddress[emails.length];
			for (int i = 0; i < emails.length; i++) {
				toAddress[i] = new InternetAddress(emails[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(
					"Este é um email de backup da base de dados local do sistema CFIP - Seu Controle Financeiro Pessoal");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			// String filename = "/cfip/database.zip";
			// FIXME: Tornar uma unica implementacao
			if (!attachPath.endsWith(File.separator)) {
				attachPath = attachPath + File.separator;
			}
			FileDataSource source = new FileDataSource(attachPath + attachName);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachName);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("E-mail enviado");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}*/
	/*
	 * public static void main(String[] args) { send("gleyson.s@hotmail.com"); }
	 */
}
