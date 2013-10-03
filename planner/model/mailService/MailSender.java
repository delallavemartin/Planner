package model.mailService;

import exception.UserException;
import mailService.Mail;
import mailService.MailServiceImpl;
import model.evento.Reunion;

public abstract class MailSender {
	
	private MailServiceImpl mailService = new MailServiceImpl();

	public void enviarMail(Reunion reunion){
		this.despacharMail(this.armarMail(reunion));
	}
	
	public void despacharMail(Mail mail) throws UserException {
		this.getMailService().sendMail(mail);
	
	}
	
	public abstract Mail armarMail(Reunion reunion);
	
	//SETTES Y GETTERS
	public MailServiceImpl getMailService() {
		return mailService;
	}

	public void setMailService(MailServiceImpl mailService) {
		this.mailService = mailService;
	}
	
}
