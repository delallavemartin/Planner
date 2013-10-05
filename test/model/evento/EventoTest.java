package model.evento;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableInterval;
import org.junit.Before;
import org.junit.Test;

public class EventoTest {

	private Actividad unEvento;
	private Actividad otroEvento;
	private MutableInterval horario;
	private DateTime fecha;
	private DateTime otraFecha;
	
	@Before
	public void setUp(){
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
	public void testNoSeSuperponesConFechaSinMargen() {
		
		unEvento.setFecha(fecha);
		unEvento.setHorario(this.horario);
		otroEvento.setHorario(this.horario);
		otroEvento.setFecha(otraFecha);
		
		Duration margen = new Duration(0);
		
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
	

}
