package mailService;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

public class MailServiceTest {

	 private MailServiceImpl mailSender;

	 @Before
	 public void setUp() {
	  mailSender = new MailServiceImpl();
	  //limpiar Mock JavaMail box
	  Mailbox.clearAll();
	 }
	 
	@Test
	public void testSendEmail() throws MessagingException, IOException {
		
		String subject = "Test2";
		String body = "Test Message2";
		
		SimpleMail mail = new SimpleMail("delallave.martin@gmail.com", "martindelallave@yahoo.com",
				subject, body);
		
		this.mailSender.sendMail(mail);

		List<Message> inbox = Mailbox.get("delallave.martin@gmail.com");
		
		assertFalse(inbox.isEmpty());
		assertTrue(inbox.size() == 1);
		assertEquals(subject, inbox.get(0).getSubject());
		assertEquals(body, inbox.get(0).getContent());

	}

}
