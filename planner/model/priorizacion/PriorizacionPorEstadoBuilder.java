package model.priorizacion;

import java.util.Comparator;
import java.util.Queue;

import exception.UserException;

import model.recurso.Recurso;

public class PriorizacionPorEstadoBuilder extends PriorizacionBuilder{

	@Override
	public void agregaCriterioDeComparacion(ComparacionDeRecursosParaReunion unCriterioDeComparacion) {

		this.validaAgregacionDeNuevoComparador(unCriterioDeComparacion);
		super.agregaCriterioDeComparacion(unCriterioDeComparacion);
	}

	private void validaAgregacionDeNuevoComparador(Comparator<Recurso> unCriterioDeComparacion) {
		if(this.getColaDeCriteriosDeComparacion().size() >= 1)
			throw new UserException("no se puede agregar mas de un criterio de comparacion para la construccion de una priorizacionPorEstado");
	
	}

	@Override
	protected Priorizacion<Recurso> creaPriorizacionARetornarAPartirDe(Queue<Priorizacion<Recurso>> colaDePriorizaciones) {
		
		return new PriorizacionPorEstado(colaDePriorizaciones.remove());
	}

	

	
	
}
