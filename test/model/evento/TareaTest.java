package model.evento;

import static org.junit.Assert.*;

import model.recurso.Organizador;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TareaTest {
	private TareaCompuesta tareaCompuesta;
	private TareaCompuesta tareaCompuesta2;
	private TareaCompuesta tareaCompuesta3;
	private TareaComplejidadSimple tareaSimple;
	private TareaComplejidadSimple tareaSimple2;
	private TareaComplejidadMedia tareaMedia;
	private TareaComplejidadComplicada tareaComplicada;
	private DateTime horaInicio1 = new DateTime(2012,4,4,15,0);
	private DateTime horaFin1 = new DateTime(2012,4,4,20,0);
	private DateTime horaInicio2 = new DateTime(2012,4,4,20,0);
	private DateTime horaFin2 = new DateTime(2012,4,5,1,0);
	private DateTime horaInicio3 = new DateTime(2012,4,5,10,0);
	private DateTime horaFin3 = new DateTime(2012,4,15,16,0);
	private Organizador responsable;
	
	@Before
	public void setUp() throws Exception {
		responsable = Mockito.mock(Organizador.class);
		
		tareaCompuesta = new TareaCompuesta();
		tareaCompuesta2 = new TareaCompuesta();
		tareaCompuesta3 = new TareaCompuesta();
		
		tareaSimple = new TareaComplejidadSimple();
		tareaSimple2 = new TareaComplejidadSimple();
		tareaMedia = new TareaComplejidadMedia();
		tareaComplicada = new TareaComplejidadComplicada();
	
		tareaSimple2.setInicio(horaInicio1);
		//TODO: Esto se deberia automatizar para que no haya que harcodear una fecha que no haya terminado la reunion
		tareaSimple2.setFin(new DateTime(2018,4,4,20,0));
		tareaSimple2.setCostoPorHora(20.00);
		
		tareaSimple.setInicio(horaInicio1);
		tareaSimple.setFin(horaFin1);
		tareaSimple.setCostoPorHora(20.00);
		
		tareaMedia.setInicio(horaInicio2);
		tareaMedia.setFin(horaFin2);
		tareaMedia.setCostoPorHora(20.00);
		tareaMedia.setResponsable(responsable);
		
		tareaComplicada.setInicio(horaInicio3);
		tareaComplicada.setFin(horaFin3);
		tareaComplicada.setCostoPorHora(20.00);
		tareaComplicada.setResponsable(responsable);
							
		tareaCompuesta.agregarTarea(tareaSimple);
		tareaCompuesta.agregarTarea(tareaMedia);
		tareaCompuesta2.agregarTarea(tareaComplicada);
		tareaCompuesta3.agregarTarea(tareaSimple2);
		
	}

	@Test
	public void asignarFinEInicioAUnaTareaCompuesta(){		
		tareaCompuesta.calcularFinEInicio();
		assertTrue(tareaCompuesta.getInicio() == horaInicio1 && tareaCompuesta.getFin() == horaFin2 );
	}
	
	@Test
	public void calcularCostoTareaSimple(){
		assertEquals((long) tareaSimple.costo(),100);
	}
	
	@Test
	public void calcularCostoTareaMedia(){
		Mockito.doReturn(30.00).when(responsable).getPrecio();
		assertEquals((long) tareaMedia.costo(),159);
	}
	@Test
	public void calcularCostoTareaComplejaCuandoDuraMasDeCincoDias(){
		Mockito.doReturn(30.00).when(responsable).getPrecio();
		assertEquals((long) tareaComplicada.costo(),7530);
	}
	@Test
	public void calcularCostoTareaComplejaCuandoDuraMenosDeCincoDias(){
		Mockito.doReturn(30.00).when(responsable).getPrecio();
		horaInicio3 = new DateTime(2012,4,5,10,0);
		horaFin3 = new DateTime(2012,4,5,16,0);
		tareaComplicada.setInicio(horaInicio3);
		tareaComplicada.setFin(horaFin3);
		assertEquals((long) tareaComplicada.costo(),180);
	}
	@Test
	public void calcularCostoTareaCompuestaConTareasSimples(){
		Mockito.doReturn(30.00).when(responsable).getPrecio();
		assertEquals( (long) tareaCompuesta.costo(), 259);
	}
	
	@Test
	public void calcularCompletitudTareaSimple(){
		assertTrue(tareaSimple.estoyCompletada());
	}
	
	@Test
	public void calcularCompletitudTareaSimpleSinTerminar(){
		assertFalse(tareaSimple2.estoyCompletada());
	}
	
	@Test
	public void calcularCompletitudTareaCompuestaConTareasSimplesTerminadas(){
		assertTrue(tareaCompuesta.estoyCompletada());
	}
	
	@Test
	public void calcularCompletitudTareaCompuestaConUnaTareaSimpleSinTerminar(){
		assertFalse(tareaCompuesta3.estoyCompletada());
	}
	
	@Test
	public void asignarInicioATareaCompuestaConOtraCompuesta(){
		tareaCompuesta.calcularFinEInicio();
		tareaCompuesta2.agregarTarea(tareaCompuesta);
		tareaCompuesta2.calcularFinEInicio();
		assertTrue(tareaCompuesta2.getInicio() == horaInicio1 && tareaCompuesta2.getFin() == horaFin3);
	}
	
	@Test
	public void calcularCostoDeTareaCompuestaConOtraCompuesta(){
		Mockito.doReturn(30.00).when(responsable).getPrecio();
		tareaCompuesta2.agregarTarea(tareaCompuesta);
		assertEquals((long)tareaCompuesta2.costo(),7789);

	}
}
