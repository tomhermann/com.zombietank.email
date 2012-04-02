package com.zombietank.email;

import java.io.Serializable;
import java.util.Map;

import com.zombietank.email.exception.EmailException;

public interface TemplatedEmailService extends EmailService {
	void send(Email email, String templateLocation) throws EmailException;
	<T extends Serializable> void send(Email email, String templateLocation, String modelKey, T modelValue) throws EmailException;
	void send(Email email, String templateLocation, Map<String, ? extends Serializable> model) throws EmailException;
}
