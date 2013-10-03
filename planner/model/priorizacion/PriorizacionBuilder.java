package model.priorizacion;

import java.util.Queue;

import java.util.concurrent.LinkedBlockingQueue;

import model.recurso.Recurso;
import exception.UserException;

public class PriorizacionBuilder {

	
	private Queue<ComparacionDeRecursosParaReunion> colaDeCriteriosDeComparacion = new LinkedBlockingQueue<ComparacionDeRecursosParaReunion>();
	
	public void agregaCriterioDeComparacion(ComparacionDeRecursosParaReunion unCriterioDeComparacion){
		
		this.getColaDeCriteriosDeComparacion().add(unCriterioDeComparacion);
	}
	
	public Priorizacion<Recurso> build() {
		
		
		this.validaConstruccion();
		Queue<Priorizacion<Recurso>> colaDePriorizaciones = obteneColaDePriorizacionesSimplesAPartirDeColaDeComparadores(this.getColaDeCriteriosDeComparacion());
		
		return creaPriorizacionARetornarAPartirDe(colaDePriorizaciones);
		 
	}


	
	private void validaConstruccion() {
		if(this.getColaDeCriteriosDeComparacion().size() == 0 )
			throw new UserException("no se puede construir una Priorizacion sin un criterio de comparaciocion");

	}

	private Queue<Priorizacion<Recurso>> obteneColaDePriorizacionesSimplesAPartirDeColaDeComparadores(Queue<ComparacionDeRecursosParaReunion> colaDeCriteriosDeComparacion) {
	
		Queue<Priorizacion<Recurso>> colaDePriorizaciones = new LinkedBlockingQueue<Priorizacion<Recurso>>();		

		while(!colaDeCriteriosDeComparacion.isEmpty() ){
			
			colaDePriorizaciones.add(new PriorizacionSimple(colaDeCriteriosDeComparacion.poll()));
		}
		return colaDePriorizaciones;
	}


	private boolean esUnaPriorizacionCompuesta(Queue<Priorizacion<Recurso>> colaDePriorizaciones) {
		return colaDePriorizaciones.size() > 1;
	}


	protected Priorizacion<Recurso> creaPriorizacionARetornarAPartirDe(Queue<Priorizacion<Recurso>> colaDePriorizaciones){
		return esUnaPriorizacionCompuesta(colaDePriorizaciones)? new PriorizacionCompuesta(colaDePriorizaciones) : colaDePriorizaciones.remove();	
	}
	
	protected Queue<ComparacionDeRecursosParaReunion> getColaDeCriteriosDeComparacion() {
		return colaDeCriteriosDeComparacion;
	}

	
	
	

}
