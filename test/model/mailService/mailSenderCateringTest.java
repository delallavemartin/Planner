package model.mailService;

import static org.junit.Assert.*;

import java.util.HashSet;

import mailService.Mail;
import mailService.SimpleMail;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;
import model.recurso.Herramienta;
import model.recurso.Organizador;
import model.recurso.RecursoReunible;

import org.joda.time.DateTime;
import org.joda.time.MutableInterval;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class mailSenderCateringTest {
	
	private Reunion reunion;
	@Mock private Reunion reunionMockeada;
	private Organizador organizador;
	private MutableInterval horario;
	private DateTime fecha = new DateTime(2012, 1, 1, 0, 0, 0, 0);
	private HashSet<RecursoReunible> recursos = new HashSet<RecursoReunible>();
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		recursos.add(new EmpleadoImp());
	
	}
	@Test
	public void testObtenerCantidadDePersonasParaReunionConUnEmpleado(){
		MailSenderCatering mailSender = new MailSenderCatering();
		Mockito.doReturn(recursos).when(reunionMockeada).getRecursos();
		assertEquals(1,mailSender.obtenerCantidadDePersonasPara(reunionMockeada));
	}
	
	@Test
	public void testObtenerCantidadDePersonasParaReunionConMasDeUnEmpleado(){
		recursos.add(new EmpleadoImp());
		MailSenderCatering mailSender = new MailSenderCatering();
		Mockito.doReturn(recursos).when(reunionMockeada).getRecursos();
		assertEquals(2,mailSender.obtenerCantidadDePersonasPara(reunionMockeada));
	}
	
	@Test
	public void testObtenerCantidadDePersonasParaReunionConUnEmpleadoYOtroRecurso(){
		recursos.add(new Herramienta(Herramienta.NombreDeHerramienta.laptop));
		MailSenderCatering mailSender = new MailSenderCatering();
		Mockito.doReturn(recursos).when(reunionMockeada).getRecursos();
		assertEquals(1,mailSender.obtenerCantidadDePersonasPara(reunionMockeada));
	}
	
//	@Test
//	public void testArmarMail(){
//		EmpleadoImp empleado = new EmpleadoImp();
//		empleado.setMail("Martin");
//		organizador = new Organizador(empleado);
//		reunion.setOrganizador(organizador);
//		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
//		DateTime fin = new DateTime(2012, 1, 1, 13, 20, 0, 0);
//		horario = new MutableInterval(inicio,fin);
//		reunion.setHorario(horario);
//		reunion.setFecha(fecha);
//		
//		Mail mail = new SimpleMail();
//		SimpleMail mailEsperado = new SimpleMail();
//		mailEsperado.setEmisor("Martin");
//		mailEsperado.setDestinatario("Daniel");
//		mailEsperado.setAsunto("Pedido de Catering");
//		mailEsperado.setTexto("Solicito Catering Para 2 "
//				+ "el dia " + reunion.getFecha().toString() + "a las"
//				+ reunion.getHorario().getStart().toString());
//		
//		MailSenderCatering mailSender = new MailSenderCatering();
//		mail = mailSender.armarMail(reunionMockeada);
//		
//		assertEquals(mailEsperado.getEmisor(),mail.getEmisor());
//		assertEquals(mailEsperado.getDestinatario(),mail.getDestinatario());
//		assertEquals(mailEsperado.getAsunto(),mail.getAsunto());
//		assertEquals(mailEsperado.getEmisor(),mailSender.armarMail(reunionMockeada));
//	}
}
