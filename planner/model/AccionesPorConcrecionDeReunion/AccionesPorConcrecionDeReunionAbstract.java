package model.AccionesPorConcrecionDeReunion;

import model.evento.Reunion;
import model.mailService.MailSender;
import exception.UserException;


public abstract class AccionesPorConcrecionDeReunionAbstract implements AccionesPorConcrecionDeReunion{
	
	public void informar(Reunion reunion,MailSender mailSender) throws UserException {
		mailSender.enviarMail(reunion);
	}
	
}
