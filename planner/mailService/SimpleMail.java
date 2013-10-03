package mailService;

public class SimpleMail implements Mail {
	
	private String destinatario;
	private String emisor;
	private String asunto;
	private String texto;	
	
	public SimpleMail() {
		super();
	}
	
	public SimpleMail(String destinatario, String emisor, String asunto,
			String texto) {

		this.destinatario = destinatario;
		this.emisor = emisor;
		this.asunto = asunto;
		this.texto = texto;
	}
	
	//Getters y Setters
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
}
