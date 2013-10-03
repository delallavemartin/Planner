package mailService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exception.UserException;

public class MailServiceImpl implements MailService{

	public void sendMail(Mail mail) throws UserException {		
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = dameSesion(props);
		
		Message mensaje;
		try {
			mensaje = dameUnMailMime(mail, session);
			Transport.send(mensaje);
		} catch (AddressException e) {
			throw new UserException("No se pudo enviar el mail");
		} catch (MessagingException e) {
			throw new UserException("No se pudo enviar el mail");
		}

	}

	protected Session dameSesion(Properties props) {
		Session session = Session.getInstance(props);
		return session;
	}

	protected Message dameUnMailMime(Mail mail, Session session)
			throws MessagingException, AddressException {
		
		Message mensaje = new MimeMessage(session);
		
		mensaje.setFrom(new InternetAddress(mail.getEmisor()));
		mensaje.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(mail.getDestinatario()));
		mensaje.setSubject(mail.getAsunto());
		mensaje.setText(mail.getTexto());
		
		return mensaje;
	}

}
