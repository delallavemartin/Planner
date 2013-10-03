package model.mailService;

import java.util.Iterator;

import mailService.Mail;
import mailService.SimpleMail;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;
import model.recurso.Recurso;
import model.recurso.RecursoReunible;

public class MailSenderCatering extends MailSender {

	@Override
	public Mail armarMail(Reunion reunion) {
		
		String emisor = obtenerEmisor(reunion);
		String destinatario = obtenerDestinatario(reunion);
		String asunto = obtenerAsunto();
		
		int cantidadDePersonasEnReunion = this.obtenerCantidadDePersonasPara(reunion);
		
		String texto = obtenerTexto(reunion, cantidadDePersonasEnReunion);
		
		Mail mail = new SimpleMail(destinatario,emisor,asunto,texto);
		return mail;
	}

	protected String obtenerTexto(Reunion reunion,
			int cantidadDePersonasEnReunion) {
		return "Solicito Catering Para " + cantidadDePersonasEnReunion
				+ "el dia " + reunion.getFecha().toString() + "a las"
				+ reunion.getHorario().getStart().toString();
	}

	protected String obtenerAsunto() {
		return "Pedido de Catering";
	}

	protected String obtenerDestinatario(Reunion reunion) {
		return reunion.getOrganizador().getMailCatering();
	}

	protected String obtenerEmisor(Reunion reunion) {
		return reunion.getOrganizador().getMail();
	}

	public int obtenerCantidadDePersonasPara(Reunion reunion) {
		
		int cantPersonas=0;
		
		for (Iterator<Recurso> iterator = reunion.getRecursos().iterator(); iterator.hasNext();) {
			RecursoReunible recurso = (RecursoReunible) iterator.next();
			if (recurso instanceof EmpleadoImp){
				cantPersonas++;
			}
		}
		
		return cantPersonas;
	}

}
