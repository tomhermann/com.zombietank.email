package com.zombietank.email.support;

import javax.mail.internet.InternetAddress;

import com.zombietank.email.exception.AddressException;

public final class InternetAddressBuilder {

	public static InternetAddress newAddress(String address) throws AddressException {
		try {
			return new InternetAddress(address);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	public static InternetAddress newAddress(String address, String personal) throws AddressException {
		try {
			return new InternetAddress(address, personal);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}
	
	private InternetAddressBuilder() {
	}
}
