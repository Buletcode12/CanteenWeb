package nirmalya.aathithya.webmodule.common.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailAttachmentSender {

	public static void sendEmailWithAttachments(String host, String port, final String addresses, final String password,
			List<String> toAddress, List<String> ccAddress, String subject, String message, String[] attachFiles)
			throws AddressException, MessagingException {
		
		System.out.println(host);
		System.out.println(port);
		System.out.println(addresses);
		System.out.println(password);
		System.out.println(toAddress);
		
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", addresses);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(addresses, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(addresses));

		InternetAddress[] toAddresses = new InternetAddress[toAddress.size()];
		int counter = 0;
		for (String toAddress1 : toAddress) {
			toAddresses[counter] = new InternetAddress(toAddress1.trim());
			counter++;
		}
		if (ccAddress != null) {
			InternetAddress[] ccAddresses = new InternetAddress[ccAddress.size()];
			int counter1 = 0;
			for (String ccAddress1 : ccAddress) {
				ccAddresses[counter1] = new InternetAddress(ccAddress1.trim());
				counter1++;
			}
			msg.setRecipients(Message.RecipientType.CC, ccAddresses);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses);

		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}
	//view-new-requi-mstr
		public static void sendEmailWithAttachmentsURL(String host, String port, final String addresses, final String password,
				List<String> toAddress, List<String> ccAddress, String subject, String message, String attachURL,String attachURLName)
				throws AddressException, MessagingException {
			
			System.out.println("host@@@"+host);
			System.out.println(port);
			System.out.println(addresses);
			System.out.println(password);
			System.out.println(toAddress);
			
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.user", addresses);
			properties.put("mail.password", password);

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(addresses, password);
				}
			};
			Session session = Session.getInstance(properties, auth);

			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(addresses));

			InternetAddress[] toAddresses = new InternetAddress[toAddress.size()];
			int counter = 0;
			for (String toAddress1 : toAddress) {
				toAddresses[counter] = new InternetAddress(toAddress1.trim());
				counter++;
			}
			if (ccAddress != null) {
				InternetAddress[] ccAddresses = new InternetAddress[ccAddress.size()];
				int counter1 = 0;
				for (String ccAddress1 : ccAddress) {
					ccAddresses[counter1] = new InternetAddress(ccAddress1.trim());
					counter1++;
				}
				msg.setRecipients(Message.RecipientType.CC, ccAddresses);
			}
			msg.setRecipients(Message.RecipientType.TO, toAddresses);

			msg.setSubject(subject);
			msg.setSentDate(new Date());

//			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");
	//
//			// creates multi-part
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(messageBodyPart);
	//
//			// adds attachments
//			if (attachURL != null) {
//				for (String filePath : attachFiles) {
//					MimeBodyPart attachPart = new MimeBodyPart();
	//
//					try {
//						attachPart.attachFile(filePath);
//					} catch (IOException ex) {
//						ex.printStackTrace();
//					}
	//
//					multipart.addBodyPart(attachPart);
//				}
//			}
			
			// Create the Multipart object to hold the email content
	        MimeMultipart multipart = new MimeMultipart();

	        multipart.addBodyPart(messageBodyPart);

	        // Create the attachment part
	        BodyPart attachmentPart = new MimeBodyPart();
	        DataSource source = null;
			try {
				source = new URLDataSource(new URL(attachURL));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        attachmentPart.setDataHandler(new DataHandler(source));
	        //attachmentPart.setFileName("Attachment");
	        attachmentPart.setFileName(attachURLName);
	        multipart.addBodyPart(attachmentPart);

			// sets the multi-part as e-mail's content
			msg.setContent(multipart);

			// sends the e-mail
			Transport.send(msg);

		}

}
