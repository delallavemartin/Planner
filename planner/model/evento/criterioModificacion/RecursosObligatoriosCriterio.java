package model.evento.criterioModificacion;

import model.evento.Reunion;
import model.recurso.Recurso;

public class RecursosObligatoriosCriterio extends CriterioModificacionReunion {

	@Override
	protected boolean aplicarCriterio(Reunion reunion, Recurso recurso) {
		
		return reunion.faltanRecursosObligatorio();
	}

	@Override
	protected String descripcionCriterio() {
		
		return "Cancelar reunion si se dio de baja alguna de los recursos obligatorios";
	}

	protected void aplicoCriterio(Reunion reunion){
		
		reunion.cancelar();
		
		super.aplicoCriterio(reunion);
	}
}
