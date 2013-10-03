package model.priorizacion;

import java.util.HashMap;
import java.util.Map;

import model.recurso.Recurso;

public class ComparacionPorEstadoDeRecursos extends ComparacionDeRecursosParaReunion {

	private Map<EstadoDeRecurso, Integer> estadosConNivelDePrioridad = new HashMap<EstadoDeRecurso, Integer>();
	
	@Override
	protected ResultadoDeComparacion comparaRecursos(Recurso primerRecurso,
			Recurso segundoRecurso) {

		
		this.asignaNivelDePrioridadAEstado();
		
		if(this.losDosTienenElMismoEstado(primerRecurso,segundoRecurso))
			return ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD;
		
		if(this.estadosConNivelDePrioridad.get(primerRecurso.getEstado()) > this.estadosConNivelDePrioridad.get(segundoRecurso.getEstado())  )
			return ResultadoDeComparacion.EL_PRIMERO_TIENE_MAS_PRIORIDAD;

		if(this.estadosConNivelDePrioridad.get(segundoRecurso.getEstado()) > this.estadosConNivelDePrioridad.get(primerRecurso.getEstado())  )
			return ResultadoDeComparacion.EL_SEGUNDO_TIENE_MAS_PRIORIDAD;

		throw new RuntimeException();
	}

	
	private void asignaNivelDePrioridadAEstado(){
		
		this.estadosConNivelDePrioridad.put(EstadoDeRecurso.EXCESIVAS_REUNIONES, 1);
		this.estadosConNivelDePrioridad.put(EstadoDeRecurso.NORMAL, 2);
		this.estadosConNivelDePrioridad.put(EstadoDeRecurso.POCAS_REUNIONES, 3);
		
	}
	
	private boolean losDosTienenElMismoEstado(  Recurso primerRecurso,
												Recurso segundoRecurso) {

		if(primerRecurso.getEstado().equals(segundoRecurso.getEstado()))
			return true;
		return false;
	}

}
