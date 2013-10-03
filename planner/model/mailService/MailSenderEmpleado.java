package model.mailService;

import mailService.Mail;
import mailService.SimpleMail;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;

public class MailSenderEmpleado extends MailSender {

	private EmpleadoImp empleado;
	
	public MailSenderEmpleado(EmpleadoImp empleadoImp) {
		this.empleado = empleadoImp;
	}

	public EmpleadoImp getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadoImp empleado) {
		this.empleado = empleado;
	}

	@Override
	public Mail armarMail(Reunion reunionConcretada) {
		String emisor = obtenerEmisor(reunionConcretada);
		String destinatario = obtenerDestinatario();
		String asunto = obtenerAsunto();
		String texto = obtenerTexto(reunionConcretada);
		
		Mail mail = new SimpleMail(destinatario,emisor,asunto,texto);
		return mail;
	}

	private String obtenerTexto(Reunion reunionConcretada) {
		return "La reunion del dia " + reunionConcretada.getFecha().toString() + "a las "
				+ reunionConcretada.getHorario().getStart().toString() + "tiene Catering";
	}

	private String obtenerAsunto() {
		return "Reunion con catering";
	}

	private String obtenerDestinatario() {
		return this.getEmpleado().getMail();
	}

	private String obtenerEmisor(Reunion reunionConcretada) {
		return reunionConcretada.getOrganizador().getMail();
	}

}
