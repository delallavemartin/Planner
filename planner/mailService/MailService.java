package mailService;

import exception.UserException;

public interface MailService {
	public void sendMail(Mail mail) throws UserException;
}
