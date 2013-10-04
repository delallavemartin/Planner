package model;

import static org.junit.Assert.*;

import java.util.Set;

import model.evento.Tarea;
import model.recurso.EmpleadoImp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProyectoTest {
	
	private Proyecto proyecto = new Proyecto();
	@Mock
	private EmpleadoImp empleado;
	@Mock
	private Set<EmpleadoImp> empleadosClaves;
	@Mock
	private Tarea tarea1;
	@Mock
	private Tarea tarea2;
	@Mock
	private Tarea tarea3;
	@Mock
	private Tarea tarea4;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.proyecto.agregarTarea(tarea1);
		this.proyecto.agregarTarea(tarea2);
		this.proyecto.agregarTarea(tarea3);
		this.proyecto.agregarTarea(tarea4);
		this.empleadosClaves.add(empleado);
		this.proyecto.setEmpleadosClaves(empleadosClaves);
	}

	@Test
	public void testPorcentajeDeTareasCompletadas() {
		Mockito.doReturn(true).when(tarea1).estoyCompletada();
		Mockito.doReturn(true).when(tarea2).estoyCompletada();
		Mockito.doReturn(true).when(tarea3).estoyCompletada();
		Mockito.doReturn(false).when(tarea4).estoyCompletada();
		assertTrue(this.proyecto.porcentajeDeTareasCompletadas()==75);
	}
	
	@Test
	public void testSoyPersonaClave() {
		Mockito.when(empleadosClaves.contains(empleado)).thenReturn(true);
		assertTrue(this.proyecto.soyPersonaClave(empleado));
	}
}
