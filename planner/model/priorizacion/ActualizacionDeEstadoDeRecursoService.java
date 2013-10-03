package model.priorizacion;


import java.lang.reflect.Method;
import java.util.Set;

import model.recurso.Recurso;

public class ActualizacionDeEstadoDeRecursoService {

	private static ActualizacionDeEstadoDeRecursoService instancia;
	
	private ActualizacionDeEstadoDeRecursoService(){}
	
	public static ActualizacionDeEstadoDeRecursoService getInstance(){
		return instancia != null? instancia : new ActualizacionDeEstadoDeRecursoService(); 
	}

	public void actualizaEstadoDe(Set<Recurso> recursos){
		for(Recurso recurso : recursos){
			if(EstadoDeRecurso.POCAS_REUNIONES.elRecursoDebeTenerte(recurso))
				this.actualizaEstadoDe(recurso, EstadoDeRecurso.POCAS_REUNIONES);
			if(EstadoDeRecurso.NORMAL.elRecursoDebeTenerte(recurso))
				this.actualizaEstadoDe(recurso, EstadoDeRecurso.NORMAL);
			if(EstadoDeRecurso.EXCESIVAS_REUNIONES.elRecursoDebeTenerte(recurso))
				this.actualizaEstadoDe(recurso, EstadoDeRecurso.EXCESIVAS_REUNIONES);
			
		}
	}

	private void actualizaEstadoDe(Recurso unRecurso , EstadoDeRecurso nuevoEstado) {
		
		try {
			
			Method setterDeEstado = unRecurso.getClass().getDeclaredMethod("setEstado", EstadoDeRecurso.class);
			setterDeEstado.setAccessible(true);
			setterDeEstado.invoke(unRecurso, nuevoEstado);
		
		} catch (Exception e) {
			new RuntimeException(e);
		} 		
	}
}
