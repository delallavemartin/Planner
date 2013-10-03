package model.evento.criterioModificacion;

import model.evento.Reunion;
import model.recurso.Recurso;

public class RecursosOpcionalesCriterio extends CriterioModificacionReunion {

	@Override
	protected boolean aplicarCriterio(Reunion reunion, Recurso recurso) {
		
		return reunion.faltanRecursosOpcional();
	}

	@Override
	protected String descripcionCriterio() {
		
		return "Mantener la reunion si de dio de  baja alguno de los recursos o personas opcionales";
	}

	protected void aplicoCriterio(Reunion reunion){
		
		//lanzar un warnning nada mas con la descripcion del criterio
	}

}
