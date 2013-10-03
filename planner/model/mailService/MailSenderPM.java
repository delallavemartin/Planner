package model.mailService;

import mailService.Mail;
import mailService.SimpleMail;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;

public class MailSenderPM extends MailSender {

	private EmpleadoImp projectManager;
	private EmpleadoImp empleado;

	public MailSenderPM(EmpleadoImp pm, EmpleadoImp empleadoImp) {
		this.projectManager = pm;
		this.empleado = empleadoImp;
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

	protected String obtenerTexto(Reunion reunionConcretada) {
		return this.empleado.getNombre() +" fue solicitado para "  
				+ "el dia " + reunionConcretada.getFecha().toString() + "a las"
				+ reunionConcretada.getHorario().getStart().toString();
	}

	protected String obtenerAsunto() {
		return "Aviso a los Projects Managers";
	}

	protected String obtenerDestinatario() {
		return this.getProjectManager().getMail();
	}

	protected String obtenerEmisor(Reunion reunionConcretada) {
		return reunionConcretada.getOrganizador().getMail();
	}
	
	//GETTERS Y SETTERS
	public EmpleadoImp getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(EmpleadoImp projectManager) {
		this.projectManager = projectManager;
	}

}
