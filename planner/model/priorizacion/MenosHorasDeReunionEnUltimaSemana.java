package model.priorizacion;

import org.joda.time.Duration;
import org.joda.time.Period;



import model.recurso.Recurso;




public class MenosHorasDeReunionEnUltimaSemana extends ComparacionDeRecursosParaReunion {

	public static final Period UNA_SEMANA_ATRAS = Period.days(7);

	@Override
	protected ResultadoDeComparacion comparaRecursos(Recurso primerRecurso,
													 Recurso segundoRecurso) {

			
		Duration horasDeReunionDelPrimerRecurso = primerRecurso
												  .dameElTiempoDeReunionesAcumuladoDesdeHace(UNA_SEMANA_ATRAS);
		Duration horasDeReunionDelSegundoRecurso = segundoRecurso
												  .dameElTiempoDeReunionesAcumuladoDesdeHace(UNA_SEMANA_ATRAS);
		
		return obteneResultadoDeComparar(horasDeReunionDelPrimerRecurso, horasDeReunionDelSegundoRecurso);
	}

	private ResultadoDeComparacion obteneResultadoDeComparar(Duration horasDeReunionDelPrimerRecurso,
															 Duration horasDeReunionDelSegundoRecurso) {
															
		if(horasDeReunionDelPrimerRecurso.isShorterThan(horasDeReunionDelSegundoRecurso))
			return ResultadoDeComparacion.EL_PRIMERO_TIENE_MAS_PRIORIDAD;

		if(horasDeReunionDelPrimerRecurso.isEqual(horasDeReunionDelSegundoRecurso))
			return ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD;
		if(horasDeReunionDelPrimerRecurso.isLongerThan(horasDeReunionDelSegundoRecurso))
			return ResultadoDeComparacion.EL_SEGUNDO_TIENE_MAS_PRIORIDAD;

		throw new RuntimeException("La comparacion entre duracion de reuniones no dio mayor,menor, ni igual");
	}

	
	

}
