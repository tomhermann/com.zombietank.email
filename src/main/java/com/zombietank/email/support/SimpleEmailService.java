package com.zombietank.email.support;

import static com.zombietank.email.EmailPreparator.preparable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import com.zombietank.email.Email;
import com.zombietank.email.EmailService;
import com.zombietank.email.exception.EmailException;

public class SimpleEmailService implements EmailService {
	private static final Logger log = LoggerFactory.getLogger(SimpleEmailService.class);
	private final JavaMailSender mailSender;

	public SimpleEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Async
	public void send(Email email) throws EmailException {
		log.info("Sending email to: {}", email.getTo());
		log.debug("Sending: {}", email);
	
		try {
			mailSender.send(preparable(email));
		} catch(Exception e) {
			log.warn("Unable to send email.", e);
			throw new EmailException("Unable to send email.", e);
		}
	}
	
	protected JavaMailSender getMailSender() {
		return mailSender;
	}
}
