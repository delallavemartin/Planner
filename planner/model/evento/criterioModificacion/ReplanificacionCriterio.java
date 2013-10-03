package model.evento.criterioModificacion;

import model.evento.Reunion;
import model.recurso.Recurso;

public class ReplanificacionCriterio extends CriterioModificacionReunion {

	@Override
	protected boolean aplicarCriterio(Reunion reunion, Recurso recurso) {
		
		return true;
	}

	@Override
	protected String descripcionCriterio() {
		
		return " Replanificar la reunion con todos sus req. para mas adelante";
	}
	
	protected void aplicoCriterio(Reunion reunion){
		
		reunion.replanificar();
		
		super.aplicoCriterio(reunion);
	}


}
