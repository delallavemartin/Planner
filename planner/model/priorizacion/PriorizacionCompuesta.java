package model.priorizacion;


import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import model.evento.Reunion;
import model.recurso.Recurso;
import exception.UserException;

//composite
public class PriorizacionCompuesta implements Priorizacion<Recurso> {

	private final Queue<Priorizacion<Recurso>> colaDePriorizaciones; //por ahora solo es una cola
		
	public PriorizacionCompuesta(Queue<Priorizacion<Recurso>> colaDePriorizaciones){
		this.colaDePriorizaciones = colaDePriorizaciones;
	}
	
	
	@Override 
	public Set<Recurso> aplicateSobreLosRecursosParaLaReunion(Set<Recurso> recursos, Reunion reunion) {
		
		this.validaPriorizaciones();

		return this.prioriza(recursos, reunion);
		
	}
	
	private void validaPriorizaciones() {
		if(this.colaDePriorizaciones.isEmpty())
			throw new UserException("Priorizacion compuesta no tiene ninguna priorizacion simple"); 
		
	}


	private Set<Recurso> actualizaRecursosARetornarAPartirDe(Set<Recurso> ultimosRecursosPriorizados, Set<Recurso> recursosAntesDeUltimaPriorizacion){
		
		 return !ultimosRecursosPriorizados.isEmpty() ? ultimosRecursosPriorizados: recursosAntesDeUltimaPriorizacion; 
	}

	private boolean seDebenSeguirAplicandoPriorizacionesSobre(Set<Recurso> recursos ){
		
		return (recursos.size() == 1)? false : true;
	}

	private Set<Recurso> prioriza(Set<Recurso> recursos, Reunion reunion) {
		
		Set<Recurso> recursosARetornar = new HashSet<Recurso>(recursos);	

		for(Priorizacion<Recurso> priorizacion : this.colaDePriorizaciones)
		{
			Set<Recurso>  ultimosRecursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursosARetornar, reunion);
			
			recursosARetornar = actualizaRecursosARetornarAPartirDe(ultimosRecursosPriorizados, recursosARetornar);
			
			if(!seDebenSeguirAplicandoPriorizacionesSobre(recursosARetornar) )
				break;
		}		
		return recursosARetornar;
	}


}
