package model.mailService;

import mailService.MailServiceImpl;
import mailService.SimpleMail;
import model.evento.Reunion;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import exception.UserException;

public class MailSenderTest {
	
	@Mock private SimpleMail mail;
	@Mock private MailServiceImpl mailServiceImpl;
	@Mock private Reunion reunion;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	@Test(expected=UserException.class)
	public void testDespacharMailConException() throws UserException {
		MailSender mailSender = new MailSenderCatering();
		mailSender.setMailService(mailServiceImpl);
		Mockito.doThrow(UserException.class).when(mailServiceImpl).sendMail(mail);
		mailSender.despacharMail(mail);
	}
	
//	@Test(expected=UserException.class)
//	public void testEnviarMailConException() throws UserException {
//		MailSender mailSender = new MailSenderTransporte();
//		mailSender.setMailService(mailServiceImpl);
//		Mockito.doReturn(UserException.class).when(mailServiceImpl).sendMail(mail);
//		
//		Mockito.doThrow(UserException.class).when(mailServiceImpl).sendMail(mail);
//		mailSender.armarMail(reunion);
//	}

}
