package model.priorizacion;

import model.Edificio;
import model.recurso.Recurso;

public class QuienSeEncuentreEnElMismoEdificioQueLaReunion extends
		ComparacionDeRecursosParaReunion {

	@Override
	protected ResultadoDeComparacion comparaRecursos(Recurso primerRecurso,
													 Recurso segundoRecurso) {
		if(this.losDosRecursosEstanEnElMismoEdificio(primerRecurso, segundoRecurso))
			return ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD;
		
		if(laReunionEstaEnElEdificioDelRecurso(primerRecurso) )
			return ResultadoDeComparacion.EL_PRIMERO_TIENE_MAS_PRIORIDAD;

		if(laReunionEstaEnElEdificioDelRecurso(segundoRecurso) )
			return ResultadoDeComparacion.EL_SEGUNDO_TIENE_MAS_PRIORIDAD;

		return ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD; // la reunion no esta en el edificio de ninguno de los 2
	}

	private boolean laReunionEstaEnElEdificioDelRecurso(Recurso recurso) {
		return this.getEdificioDeReunion().equals(recurso.getEdificio());
	}

	private boolean losDosRecursosEstanEnElMismoEdificio( Recurso primerRecurso,
														  Recurso segundoRecurso){

		if(	primerRecurso.getEdificio().equals(segundoRecurso.getEdificio()) )			
				return true;
		return false;
	}
	
	private Edificio getEdificioDeReunion() {
		return this.getReunion().getSala().getEdificio();
	}

}
