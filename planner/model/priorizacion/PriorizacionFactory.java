package model.priorizacion;

import model.recurso.Recurso;

public class PriorizacionFactory {

	private static PriorizacionFactory instancia;
	
	private PriorizacionFactory(){}
	
	static PriorizacionFactory getInstance(){
		return instancia != null? instancia : new PriorizacionFactory(); 
	}


	public Priorizacion<Recurso> creaPriorizacionParaEstadoPocasReuniones() {
		
		PriorizacionBuilder builder = new PriorizacionBuilder();
		
		builder.agregaCriterioDeComparacion(new MenorCosto());
		builder.agregaCriterioDeComparacion(new QuienSeEncuentreEnElMismoEdificioQueLaReunion());
		
		return builder.build();
	}
	public Priorizacion<Recurso> creaPriorizacionParaEstadoNormal() {

		PriorizacionBuilder builder = new PriorizacionBuilder();
		
		builder.agregaCriterioDeComparacion(new MenosHorasDeReunionEnUltimaSemana());
		builder.agregaCriterioDeComparacion(new QuienSeEncuentreEnElMismoEdificioQueLaReunion());

		return builder.build();
	}
	public Priorizacion<Recurso> creaPriorizacionParaEstadoExcesivasReuniones() {
		return this.creaPriorizacionParaEstadoNormal();
	}

	public Priorizacion<Recurso> creaPriorizacionPorEstados() {

		PriorizacionPorEstadoBuilder builder = new PriorizacionPorEstadoBuilder();
		builder.agregaCriterioDeComparacion(new ComparacionPorEstadoDeRecursos());
		return builder.build();
	}	
	
}
