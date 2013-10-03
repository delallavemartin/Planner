package model.priorizacion;

import model.recurso.Recurso;

public enum EstadoDeRecurso {
					//	Condicion de cada estado															Priorizacion de cada estado
	POCAS_REUNIONES		(CondicionFactory.getInstance().creaCondicionParaEstadoPocasReuniones() 	,PriorizacionFactory.getInstance().creaPriorizacionParaEstadoPocasReuniones()),
	NORMAL				(CondicionFactory.getInstance().creaCondicionParaEstadoNormal() 			,PriorizacionFactory.getInstance().creaPriorizacionParaEstadoNormal()),
	EXCESIVAS_REUNIONES	(CondicionFactory.getInstance().creaCondicionParaEstadoExcesivasReuniones() ,PriorizacionFactory.getInstance().creaPriorizacionParaEstadoExcesivasReuniones());
	
	private Priorizacion<Recurso> priorizacion;
	private CondicionDeEstado condicion;
	
	EstadoDeRecurso(CondicionDeEstado condicion , Priorizacion<Recurso> priorizacion){

		this.condicion = condicion;
		this.priorizacion = priorizacion;
	}
		
	public Priorizacion<Recurso> getPriorizacion() {
		return priorizacion;
	}

	public boolean elRecursoDebeTenerte(Recurso unRecurso){
		
		return this.condicion.teCumplisPara(unRecurso);
	}
	
	
}
