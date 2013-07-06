package com.hp.et.log.mail.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hp.et.log.configure.PropertiesLoader;
import com.sun.mail.smtp.SMTPSendFailedException;

/**
 * This class utilize JavaMail to send mail. Because of time limitation, I don't
 * implement this class. Need to do in future release.
 * 
 * @author qizhou
 * 
 */
public class LocalMailService implements IMailService {
    	
    	static{
    	    Properties props = PropertiesLoader.loadProperties("appConfig.properties");
    	    String smtpHost = props.getProperty("email.smtphost");
    	    if(null==smtpHost||""==smtpHost.trim()){
    		smtpHost = "localhost";
    	    }
    	    System.setProperty("mail.smtp.host", smtpHost);
    	}

	protected InternetAddress[] splitAddr(String addrList) throws Exception {
		String[] addrStrs = AddressPaser.parseAddress(addrList);

		InternetAddress[] addrs = new InternetAddress[addrStrs.length];
		for (int i = 0; i < addrs.length; i++) {
			addrs[i] = new InternetAddress(addrStrs[i]);
		}

		return addrs;

	}

	public void sendMail(Mail mail) throws MailException {
		// TODO Auto-generated method stub
		Properties props = System.getProperties();

		// whenever the addresses are right or not, always try to send email out
		props.put("mail.smtp.sendpartial", "true");

		String envelopFrom = mail.getEnvelopeFrom();
		if (envelopFrom != null && envelopFrom.length() != 0) {
			props.put("mail.smtp.from", envelopFrom);
		}

		Session session = Session.getDefaultInstance(props, null);

		MimeMessage message = new MimeMessage(session);

		try {
			String to = mail.getTo();
			InternetAddress[] internetAddrs = splitAddr(to);

			message.setRecipients(Message.RecipientType.TO, internetAddrs);

			String cc = mail.getCc();
			if (cc != null && cc.trim().length() > 0) {
				InternetAddress[] ccAddrs = splitAddr(cc);
				message.setRecipients(Message.RecipientType.CC, ccAddrs);
			}

			message.setFrom(new InternetAddress(mail.getFrom()));
			// message.setEnvelopeFrom("zhou.qi@hp.com");

			message.setSubject(mail.getSubject());

			message.setContent(mail.getBody(), mail.getMailType());

			Transport.send(message);

		} catch (SMTPSendFailedException ex) {
			// even we get this exception, the email was still sent
			// successfully.
		} catch (Exception ex) {
			throw new MailException(ex);
		}

	}

}
