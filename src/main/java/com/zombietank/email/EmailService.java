package com.zombietank.email;

import com.zombietank.email.exception.EmailException;

public interface EmailService {
	void send(Email email) throws EmailException;
}
