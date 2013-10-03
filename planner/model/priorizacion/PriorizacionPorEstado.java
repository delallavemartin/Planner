package model.priorizacion;

import java.util.Set;

import model.evento.Reunion;
import model.recurso.Recurso;

public class PriorizacionPorEstado implements Priorizacion<Recurso> {

	private Priorizacion<Recurso> priorizacionSimplePorAtributoEstado; 
	
	
	public PriorizacionPorEstado(Priorizacion<Recurso> priorizacionSimplePorAtributoEstado) {

		this.priorizacionSimplePorAtributoEstado = priorizacionSimplePorAtributoEstado;
	}


	@Override
	public Set<Recurso> aplicateSobreLosRecursosParaLaReunion(Set<Recurso> recursos, Reunion reunion) {

		Set<Recurso> recursosDeUnEstado = priorizacionSimplePorAtributoEstado.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Priorizacion<Recurso> priorizacionDelEstado = this.obtenePriorizacionPropiaDelEstadoDe(recursosDeUnEstado);
		
		return priorizacionDelEstado.aplicateSobreLosRecursosParaLaReunion(recursosDeUnEstado, reunion);
	}


	private Priorizacion<Recurso> obtenePriorizacionPropiaDelEstadoDe(Set<Recurso> recursosConUnEstado) {
		
		return obteneAlgunRecursoDe(recursosConUnEstado).getEstado().getPriorizacion();
		
	}


	private Recurso obteneAlgunRecursoDe(Set<Recurso> recursosConUnEstado) {
		return recursosConUnEstado.iterator().next();
	}

}
