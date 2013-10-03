package model.priorizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import exception.UserException;

import model.evento.Reunion;
import model.recurso.Recurso;

public class PriorizacionSimple implements Priorizacion<Recurso> {


	private ComparacionDeRecursosParaReunion criterioDeComparacion;

		
	public PriorizacionSimple(ComparacionDeRecursosParaReunion criterioDeComparacion) {
		this.criterioDeComparacion = criterioDeComparacion;
	}

	@Override
	public Set<Recurso> aplicateSobreLosRecursosParaLaReunion(Set<Recurso> recursos, Reunion reunion) {
	
		this.validaConjunto( recursos);

		this.criterioDeComparacion.setReunion(reunion);
	
		List<Recurso> recursosPriorizados = this.ordenaRecursosPorPrioridad(recursos);
		
		return this.obteneRecursosConLaMismaPrioridadQue(recursosPriorizados.get(0) , recursosPriorizados);

	}

	private List<Recurso> ordenaRecursosPorPrioridad(Set<Recurso> recursos){

		List<Recurso> recursosPriorizados = new ArrayList<Recurso>(recursos);

		Collections.sort(recursosPriorizados, this.criterioDeComparacion);
		return recursosPriorizados;
		}
	
	
	private Set<Recurso> obteneRecursosConLaMismaPrioridadQue(Recurso recursoConMayorPrioridad ,
													  		  List<Recurso> recursosOrdenadosPorPrioridad) {
		Set<Recurso> recursosConLaMismaPrioridad = new HashSet<Recurso>();
		
		Iterator<Recurso> iterador = recursosOrdenadosPorPrioridad.iterator();
//		for( Recurso unRecursoDelConjunto = iterador.next() ; iterador.hasNext() ; unRecursoDelConjunto = iterador.next()) {
		while(iterador.hasNext()){
			Recurso unRecursoDelConjunto = iterador.next();
			
			if(!tienenIgualPrioridad(recursoConMayorPrioridad, unRecursoDelConjunto) )
				break;
			recursosConLaMismaPrioridad.add(unRecursoDelConjunto);
		}
		return recursosConLaMismaPrioridad;
		
	}

	private boolean tienenIgualPrioridad(Recurso recursoConMayorPrioridad,
										 Recurso unRecursoDelConjunto) {
		return this.criterioDeComparacion.compare(unRecursoDelConjunto , recursoConMayorPrioridad) == 
			   ComparacionDeRecursosParaReunion.ResultadoDeComparacion.TIENEN_IGUAL_PRIORIDAD.toInt();
	}

	private void validaConjunto(Set<Recurso> recursos) { 
		if(recursos.isEmpty())
			throw new UserException("el conjunto de recursos del que se quiere priorizar recursos esta vacio");
	}

}
