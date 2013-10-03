package model.mailService;

import java.util.Iterator;

import mailService.Mail;
import mailService.SimpleMail;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;
import model.recurso.Recurso;
import model.recurso.RecursoReunible;

public class MailSenderTransporte extends MailSender {

	@Override
	public Mail armarMail(Reunion reunion) {
		
		String emisor = obtenerEmisor(reunion);
		String destinatario = obtenerDestinatario(reunion);
		String asunto = obtenerAsunto();
		
		String asistentes = this.obtenerAsistentes(reunion);
		
		String texto = obtenerTexto(reunion, asistentes);
		
		Mail mail = new SimpleMail(destinatario,emisor,asunto,texto);
		return mail;
	}

	protected String obtenerTexto(Reunion reunion, String asistentes) {
		return "Solicito Transporte Para " + asistentes
				+ "el dia " + reunion.getFecha().toString() + "a las"
				+ reunion.getHorario().getStart().toString();
	}

	protected String obtenerAsunto() {
		return "Pedido de Transporte";
	}

	protected String obtenerDestinatario(Reunion reunion) {
		return reunion.getOrganizador().getMailTransporte();
	}

	protected String obtenerEmisor(Reunion reunion) {
		return reunion.getOrganizador().getMail();
	}

	public String obtenerAsistentes(Reunion reunion) {
		String asistentes = "";
		for (Iterator<Recurso> iterator = reunion.getRecursos().iterator(); iterator.hasNext();) {
			RecursoReunible recurso = (RecursoReunible) iterator.next();
			if (recurso instanceof EmpleadoImp){
				asistentes = asistentes.concat(((EmpleadoImp) recurso).getNombre());
			}
		}
		
		return asistentes;
	}

}
