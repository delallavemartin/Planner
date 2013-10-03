package model.evento.criterioModificacion;

import exception.UserException;
import model.evento.Reunion;
import model.recurso.Recurso;

public abstract class CriterioModificacionReunion {

//Attributes
	private CriterioModificacionReunion sucesor;

	
//Accessing
	
	public CriterioModificacionReunion getSucesor() {
		return sucesor;
	}
	public void setSucesor(CriterioModificacionReunion sucesor) {
		this.sucesor = sucesor;
	}
	
//UserMethods
	
	public void aplicarAlgunCriterioParaConfirmacionDeReunion(Reunion reunion){
		
		this.aplicarAlgunCriterio(reunion, null);
	}
	
	public void aplicarAlgunCriterioParaCancelacionAsistencia( Reunion reunion, Recurso recurso){
		
		this.aplicarAlgunCriterio(reunion, recurso);
	}
	
	protected void aplicarAlgunCriterio(Reunion reunion, Recurso recurso){
		
		if( this.aplicarCriterio(reunion, recurso)){
			
			this.aplicoCriterio(reunion);
		} else {
			
			this.siguienteCriterio(reunion).aplicarCriterio(reunion, recurso);
		}
		
	}
	
	protected abstract boolean aplicarCriterio(Reunion reunion, Recurso recurso);
		
	protected void aplicoCriterio(Reunion reunion){
		
		System.out.println( "Criterio aplicado: " + this.descripcionCriterio() );
	}
	
	protected CriterioModificacionReunion siguienteCriterio(Reunion reunion){
		if( this.getSucesor() != null ){
			
			 return this.getSucesor() ;
		} else {
			
			reunion.cancelar();
			
			throw new UserException("Se cancelo la reunion por falta de criterio a la hora saber que hacer cuando se modifica reunion");
		}
	}
	
	protected abstract String descripcionCriterio();
}
