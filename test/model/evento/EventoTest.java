package model.evento;

import static org.junit.Assert.*;

import java.util.Set;

import model.recurso.Empleado;
import model.recurso.EmpleadoImp;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableInterval;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import exception.UserException;

public class EventoTest {

	private Actividad unEvento;
	private Actividad otroEvento;
	private MutableInterval horario;
	private DateTime fecha;
	private DateTime otraFecha;
	@Mock
	private EmpleadoImp unEmpleadoMocked;
	@Mock
	private Actividad unEventoMocked;
	@Mock
	private Set<Empleado> empleadosMocked;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		this.unEvento = new Actividad();
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 30, 0, 0);
		fecha = new DateTime(2012, 1, 1, 0, 0, 0, 0);
		otraFecha = new DateTime(2012, 1, 2, 0, 0, 0, 0);
		this.horario = new MutableInterval(inicio, fin);

		this.otroEvento = new Actividad();
	}
	
	@Test
	public void testTenesHorarioSinFecha() {
		this.unEvento.setHorario(horario);
		assertFalse(unEvento.tenesHorario());
	}
	
	@Test
	public void testTenesHorarioSinHorario() {
		this.unEvento.setFecha(fecha);
		assertFalse(unEvento.tenesHorario());
	}
	
	@Test
	public void testTenesHorarioConFechaYHorario() {
		this.unEvento.setHorario(horario);
		this.unEvento.setFecha(fecha);
		assertTrue(unEvento.tenesHorario());
	}
	
	@Test
	public void testTenesMismaFecha() {
		this.unEvento.setFecha(fecha);
		this.otroEvento.setFecha(fecha);
		assertTrue(unEvento.tenesMismaFechasQue(otroEvento));
	}
	
	@Test
	public void testTenesDistintaFecha() {
		this.unEvento.setFecha(fecha);
		this.otroEvento.setFecha(otraFecha);
		assertFalse(unEvento.tenesMismaFechasQue(otroEvento));
	}
	
	@Test
	public void testSeSuperponesConHorarioYFechaSinMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(0);
		
		assertTrue(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testSeSuperponesConHorarioYFechaConMargen() {
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(30);
		
		assertTrue(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testNoSeSuperponesConHorarioSinMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(0);
		
		assertFalse(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testNoSeSuperponesConHorarioConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 12, 30, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(20);
		
		assertFalse(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testNoSeSuperponesConFechaSinMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(otraFecha);
		
		Duration margen = new Duration(0);
		
		assertFalse(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testNoSeSuperponesConFechaConMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(otraFecha);
		
		Duration margen = new Duration(30);
		
		assertFalse(this.unEvento.seSuperponeConHorarioYFechaDe(otroEvento, margen));
	}
	
	@Test
	public void testTeSuperponesConHorarioSinMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(0);
		
		assertTrue(this.unEvento.teSuperponesCon(otroEvento, margen));
	}
	
	@Test
	public void testTeSuperponesConHorarioConMargen() {
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(30);
		
		assertTrue(this.unEvento.teSuperponesCon(otroEvento, margen));
	}
	
	@Test
	public void testNoTeSuperponesConHorarioSinMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);

		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(0);
		
		assertFalse(this.unEvento.teSuperponesCon(otroEvento, margen));
	}
	
	@Test
	public void testNoTeSuperponesConHorarioConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 12, 30, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);

		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		Duration margen = new Duration(20);
		
		assertFalse(this.unEvento.teSuperponesCon(otroEvento, margen));
	}
	
	@Test
	public void testNoTeSuperponesConFechaSinMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(otraFecha);
		
		Duration margen = new Duration(0);
		
		assertFalse(this.unEvento.teSuperponesCon(otroEvento, margen));
	}
	
	@Test
	public void testNoTeSuperponesConFecha() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(otraFecha);
		
		assertFalse(this.unEvento.teSuperponesCon(otroEvento));
	}
	
	@Test
	public void testNoTeSuperponesConHorario() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);

		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		assertFalse(this.unEvento.teSuperponesCon(otroEvento));
	}
	

	@Test
	public void testTeSuperponesCon() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(fecha);
		
		assertTrue(this.unEvento.teSuperponesCon(otroEvento));
	}
	
	@Test
	public void testCompareTo() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(fecha);
		
		assertEquals(0,this.unEvento.compareTo(this.otroEvento));
	}
	
	@Test
	public void testCompareToFalseBefore() {
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		assertEquals(1, this.unEvento.compareTo(this.otroEvento));
	}
	
	@Test
	public void testCompareToFalseAfter() {
		DateTime inicio = new DateTime(2012, 1, 1, 14, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 16, 0, 0, 0);
		MutableInterval otroHorario = new MutableInterval(inicio, fin);
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(otroHorario);
		otroEvento.setFecha(fecha);
		
		assertEquals(-1, this.unEvento.compareTo(this.otroEvento));
	}
	
	@Test
	public void testGetDuracionEnMinutos() {
		
		this.unEvento.setHorario(horario);
		assertEquals(30, this.unEvento.getDuracionEnMinutos(), 30);
		
	}
	
	@Test
	public void testGetDuracion() {
		
		this.unEvento.setHorario(horario);
		assertTrue(this.unEvento.getDuracion().isEqual(new Duration(1800000)));
		
	}
	
	@Test
	public void testEstasDisponible() {
		Mockito.when(this.unEmpleadoMocked.estasDisponiblePara(unEvento)).thenReturn(true);
		this.unEvento.estaDisponible(unEmpleadoMocked);
	}
	
	@Test(expected=UserException.class)
	public void testNoEstasDisponible() {
		Mockito.when(this.unEmpleadoMocked.estasDisponiblePara(unEvento)).thenReturn(false);
		this.unEvento.estaDisponible(unEmpleadoMocked);
	}

	
	@Test
	public void testAgregarRecursoDisponible() {
		
		Mockito.doCallRealMethod().when(this.unEventoMocked).agregaRecurso(unEmpleadoMocked);
		this.unEventoMocked.agregaRecurso(unEmpleadoMocked);
		Mockito.verify(this.unEventoMocked).estaDisponible(unEmpleadoMocked);
		Mockito.verify(this.unEventoMocked).agregarEmpleado(unEmpleadoMocked);
		
	}
	
	@Test(expected=UserException.class)
	public void testAgregarRecursoNoDisponible() {
		
		Mockito.doCallRealMethod().when(this.unEventoMocked).agregaRecurso(unEmpleadoMocked);
		Mockito.doThrow(UserException.class).when(this.unEventoMocked).estaDisponible(unEmpleadoMocked);
		this.unEventoMocked.agregaRecurso(unEmpleadoMocked);
		
	}

}
