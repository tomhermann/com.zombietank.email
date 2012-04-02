package com.zombietank.email;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailPreparatorTest {
	private EmailPreparator emailPreparator;
	private Email email;
	@Mock private MimeMessage mimeMessage;
	@Captor private ArgumentCaptor<InternetAddress> addressCaptor;
	@Captor private ArgumentCaptor<InternetAddress[]> addressesCaptor;
	
	@Before
	public void setup() {
		email = new Email();
		emailPreparator = new EmailPreparator(email);
	}
	
	@Test
	public void shouldSetRecipient() throws Exception {
		final String toAddress = RandomStringUtils.randomAlphanumeric(5) + "@example.com";
		email.to(toAddress).from("from@example.com").withMessage("message");
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setRecipients(eq(RecipientType.TO), addressesCaptor.capture());
		assertEquals(toAddress, addressesCaptor.getValue()[0].getAddress());
	}	
	
	@Test
	public void shouldSetMultipleRecipients() throws Exception {
		final String firstRecipient = RandomStringUtils.randomAlphanumeric(5) + "@example.com";
		final String secondRecipient = RandomStringUtils.randomAlphanumeric(5) + "@example.com";
		email.to(firstRecipient).to(secondRecipient).from("from@example.com").withMessage("message");
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setRecipients(eq(RecipientType.TO), addressesCaptor.capture());
		assertEquals(firstRecipient, addressesCaptor.getValue()[0].getAddress());
		assertEquals(secondRecipient, addressesCaptor.getValue()[1].getAddress());
	}	
	
	@Test
	public void shouldSetFrom() throws Exception {
		final String fromAddress = RandomStringUtils.randomAlphanumeric(5) + "@example.com";
		email.to("to@example.com").from(fromAddress).withMessage("message");
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setFrom(addressCaptor.capture());
		assertEquals(fromAddress, addressCaptor.getValue().getAddress());
	}	
	
	@Test
	public void shouldSetBcc() throws Exception {
		final String bcc = RandomStringUtils.randomAlphanumeric(5) + "@example.com";
		email.to("to@example.com").from("from@example.com").bcc(bcc).withMessage("message");
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setRecipients(eq(RecipientType.BCC), addressesCaptor.capture());
		assertEquals(bcc, addressesCaptor.getValue()[0].getAddress());
	}	
	
	@Test
	public void shouldSetPriorityToNormalByDefault() throws Exception {
		email.to("to@example.com").from("from@example.com").withMessage("message");
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setHeader("X-Priority", Integer.toString(Priority.NORMAL.getValue()));
	}	
	
	@Test
	public void shouldSetPriorityToSpecifiedValue() throws Exception {
		final Priority priorty = Priority.valueOf(random(1, 5));
		email.to("to@example.com").from("from@example.com").withMessage("message").withPriority(priorty);
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setHeader("X-Priority", Integer.toString(priorty.getValue()));
	}	
	
	@Test
	public void shouldSetMessage() throws Exception {
		final String message = RandomStringUtils.randomAlphanumeric(10);
		email.to("to@example.com").from("from@example.com").withMessage(message);
		
		emailPreparator.prepare(mimeMessage);
		
		verify(mimeMessage).setText(message);
	}
	
	private static int random(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

}
