package model;

import static org.junit.Assert.*;

import model.evento.Tarea;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProyectoTest {
	
	private Proyecto proyecto = new Proyecto();
	private Tarea tarea1;
	private Tarea tarea2;
	private Tarea tarea3;
	private Tarea tarea4;

	@Before
	public void setUp() throws Exception {
		tarea1 = Mockito.mock(Tarea.class);
		tarea2 = Mockito.mock(Tarea.class);
		tarea3 = Mockito.mock(Tarea.class);
		tarea4 = Mockito.mock(Tarea.class);
		proyecto.agregarTarea(tarea1);
		proyecto.agregarTarea(tarea2);
		proyecto.agregarTarea(tarea3);
		proyecto.agregarTarea(tarea4);
	}

	@Test
	public void testPorcentajeDeTareasCompletadas() {
		Mockito.doReturn(true).when(tarea1).estoyCompletada();
		Mockito.doReturn(true).when(tarea2).estoyCompletada();
		Mockito.doReturn(true).when(tarea3).estoyCompletada();
		Mockito.doReturn(false).when(tarea4).estoyCompletada();
		assertTrue(proyecto.porcentajeDeTareasCompletadas()==75);
	}
	
}
