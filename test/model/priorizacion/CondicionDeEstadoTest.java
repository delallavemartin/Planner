package model.priorizacion;


import model.recurso.Recurso;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CondicionDeEstadoTest {


	
	
	private final Duration diecinueveHorasDeReunion = Duration.standardHours(19);
//	private final Duration veinteHorasDeReunion = Duration.standardHours(20);
	private final Duration veintidosHorasDeReunion = Duration.standardHours(22);
	private final Duration noventaYNueveHorasDeReunion = Duration.standardHours(99);
//	private final Duration cienHorasDeReunion = Duration.standardHours(100);
	private final Duration cientoUnHorasDeReunion = Duration.standardHours(101);
	
	private final Period aPartirDecuatroMesesAtras = Period.months(4);
	private final Period aPartirDeCincoDiasAtras = Period.days(5);

	

	@Test
	public void crearUnaCondicionQueSeCumpleParaTodosSeEsperaQueSeCumplaParaCualquierRecurso(){
		
//			resto pasar´n al estado de pocas reuniones.
					
			Recurso	unRecurso = Mockito.mock(Recurso.class);
		
			CondicionDeEstado condicionQueSeCumpleParaTodos = CondicionFactory.getInstance()
																			  .creaCondicionQueSeCumpleParaTodos();
			Assert.assertTrue(condicionQueSeCumpleParaTodos.teCumplisPara(unRecurso));
			Mockito.verifyZeroInteractions(unRecurso);
				
	}
	
	@Test
	public void creaCondicionParaEstadoNormalSeEsperaQueElRecursoLoCumpla(){

//		Los que tengan mas de 100 horas en los ultimos 4 meses, pasaran al estado normal 
		
		
			Recurso unRecursoQueCumpleCondicion = this.creaRecursoConTiempoAcumuladoDadoEnUnTiempoAtrasDado(this.cientoUnHorasDeReunion , this.aPartirDecuatroMesesAtras);
		
			CondicionDeEstado condicion = CondicionFactory.getInstance().creaCondicionParaEstadoNormal();
			
			Assert.assertTrue(condicion.teCumplisPara(unRecursoQueCumpleCondicion));
			Mockito.verify(unRecursoQueCumpleCondicion).dameElTiempoDeReunionesAcumuladoDesdeHace(this.aPartirDecuatroMesesAtras);
			
	}

	@Test
	public void creaCondicionParaEstadoNormalSeEsperaQueElRecursoNoLoCumpla(){
		
		Recurso unRecursoQueNoCumpleCondicion = this.creaRecursoConTiempoAcumuladoDadoEnUnTiempoAtrasDado(this.noventaYNueveHorasDeReunion , this.aPartirDecuatroMesesAtras);

		CondicionDeEstado condicion = CondicionFactory.getInstance().creaCondicionParaEstadoNormal();
		
		Assert.assertFalse(condicion.teCumplisPara(unRecursoQueNoCumpleCondicion));
		
	}

	
	@Test
	public void crearCondicionParaEstadoExcesivasReunionesSeEsperaQueElRecursoLoCumpla(){
//		tengan mas de 20 horas de reuniones en los ultimos 5 dias pasaran al estado de excesivas reuniones. 
		Recurso unRecursoQueCumpleCondicion =  this.creaRecursoConTiempoAcumuladoDadoEnUnTiempoAtrasDado(this.veintidosHorasDeReunion , this.aPartirDeCincoDiasAtras);

		CondicionDeEstado condicion = CondicionFactory.getInstance().creaCondicionParaEstadoExcesivasReuniones();
		
		Assert.assertTrue(condicion.teCumplisPara(unRecursoQueCumpleCondicion));

	}
	@Test
	public void crearCondicionParaEstadoExcesivasReunionesSeEsperaQueElRecursoNoLoCumpla(){

		Recurso unRecursoQueNoCumpleCondicion = this.creaRecursoConTiempoAcumuladoDadoEnUnTiempoAtrasDado(this.diecinueveHorasDeReunion , this.aPartirDeCincoDiasAtras);

		CondicionDeEstado condicion = CondicionFactory.getInstance().creaCondicionParaEstadoExcesivasReuniones();
		
		Assert.assertFalse(condicion.teCumplisPara(unRecursoQueNoCumpleCondicion));

	}

	private Recurso creaRecursoConTiempoAcumuladoDadoEnUnTiempoAtrasDado(Duration tiempoAcumulado , Period tiempoAtras) {

		Recurso recurso = Mockito.mock(Recurso.class);
		
		Mockito.when(recurso.dameElTiempoDeReunionesAcumuladoDesdeHace(tiempoAtras)).thenReturn(tiempoAcumulado);
		return recurso;
	}

	
}
