package model.evento;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.MutableInterval;
import org.junit.Before;
import org.junit.Test;

public class EventoTest {

	private Evento evento;
	private MutableInterval horario;
	private DateTime fecha;
	
	@Before
	public void setUp(){
		this.evento = new Actividad();
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 30, 0, 0);
		fecha = new DateTime(2012, 1, 1, 13, 30, 0, 0);
		this.horario = new MutableInterval(inicio, fin);
		
	}
	
	@Test
	public void testTenesHorarioSinFecha() {
		this.evento.setHorario(horario);
		assertFalse(evento.tenesHorario());
	}
	
	@Test
	public void testTenesHorarioSinHorario() {
		this.evento.setFecha(fecha);
		assertFalse(evento.tenesHorario());
	}
	
	@Test
	public void testTenesHorarioConFechaYHorario() {
		this.evento.setHorario(horario);
		this.evento.setFecha(fecha);
		assertTrue(evento.tenesHorario());
	}

}
