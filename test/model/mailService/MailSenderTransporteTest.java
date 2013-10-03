package model.mailService;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import model.evento.Reunion;
import model.recurso.EmpleadoImp;
import model.recurso.Herramienta;
import model.recurso.Organizador;
import model.recurso.RecursoReunible;

import org.joda.time.MutableInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MailSenderTransporteTest {
	@Mock private Reunion reunion;
	@Mock private Organizador organizador;
	@Mock private MutableInterval horario;
	private HashSet<RecursoReunible> recursos = new HashSet<RecursoReunible>();
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		EmpleadoImp empleado = new EmpleadoImp();
		empleado.setNombre("Martin");
		recursos.add(empleado);
	}
	@Test
	public void testObtenerAsistentesConExito(){
		MailSenderTransporte mailSender = new MailSenderTransporte();
		Mockito.doReturn(recursos).when(reunion).getRecursos();
		assertEquals(1,recursos.size());
		assertEquals("Martin",mailSender.obtenerAsistentes(reunion));
	}
	
	@Test
	public void testObtenerAsistentesSinExito(){
		MailSenderTransporte mailSender = new MailSenderTransporte();
		Mockito.doReturn(recursos).when(reunion).getRecursos();
		assertEquals(1,recursos.size());
		Assert.assertNotSame("Martina",mailSender.obtenerAsistentes(reunion));
	}
	
}
