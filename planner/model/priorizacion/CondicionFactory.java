package model.priorizacion;


import java.lang.reflect.Constructor;

import model.recurso.Herramienta;

import org.joda.time.Duration;
import org.joda.time.Period;

public class CondicionFactory {

	
	private static CondicionFactory instancia;
	
	private CondicionFactory(){}
	
	static CondicionFactory getInstance(){
		return instancia != null? instancia : new CondicionFactory(); 
	}
	
	
	
	public CondicionDeEstado creaCondicionParaEstadoPocasReuniones() {
		
		return this.creaCondicionQueSeCumpleParaTodos();

	}
	public CondicionDeEstado creaCondicionParaEstadoNormal() {
		
		Duration cienHorasDeReunion = Duration.standardHours(100);
		Period aPartirDecuatroMesesAtras = Period.months(4);
	
		return new CondicionDeEstado(cienHorasDeReunion,  aPartirDecuatroMesesAtras );
	}
	public CondicionDeEstado creaCondicionParaEstadoExcesivasReuniones() {
		
		Duration veinteHorasDeReunion = Duration.standardHours(20);
		Period aPartirDeCincoDiasAtras = Period.days(5);

		return new CondicionDeEstado(veinteHorasDeReunion ,  aPartirDeCincoDiasAtras);
	}

	public CondicionDeEstado creaCondicionQueSeCumpleParaTodos(){
		try {
			Constructor<CondicionDeEstado> constructor = CondicionDeEstado.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			
			return constructor.newInstance();
			
		} catch (Exception e) {
			throw new RuntimeException(e);			
		} 
		
	}
	
	
	
	
}
