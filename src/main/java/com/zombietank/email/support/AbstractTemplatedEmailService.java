package com.zombietank.email.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;

import com.zombietank.email.Email;
import com.zombietank.email.TemplatedEmailService;

/***
 * Starter e-mail service for template engines that use maps.
 */
public abstract class AbstractTemplatedEmailService extends SimpleEmailService implements TemplatedEmailService {

	public AbstractTemplatedEmailService(JavaMailSender mailSender) {
		super(mailSender);
	}

	public void send(Email email, String templateLocation) {
		send(email, templateLocation, null);
	}

	public <T extends Serializable> void send(Email email, String templateLocation, String modelKey, T modelValue) {
		// Made mutable for engines like velocity, which explode when trying to modify an immutable map.
		Map<String, T> model = new HashMap<String, T>(1);
		model.put(modelKey, modelValue);
		send(email, templateLocation, model);
	}
}
