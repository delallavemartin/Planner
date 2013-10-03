package model.priorizacion;

import model.recurso.Recurso;

public class MenorCosto extends ComparacionDeRecursosParaReunion {

	@Override
	protected ResultadoDeComparacion comparaRecursos(Recurso primerRecurso,
			Recurso segundoRecurso) {
		
		if(primerRecurso.getPrecio() == segundoRecurso.getPrecio())
			return ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD;
		
		return primerRecurso.getPrecio() < segundoRecurso.getPrecio()	? ResultadoDeComparacion.EL_PRIMERO_TIENE_MAS_PRIORIDAD 
															   			: ResultadoDeComparacion.EL_SEGUNDO_TIENE_MAS_PRIORIDAD; 
	}

}
