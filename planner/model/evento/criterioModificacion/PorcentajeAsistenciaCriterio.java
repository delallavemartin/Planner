package model.evento.criterioModificacion;

import model.evento.Reunion;
import model.recurso.Recurso;

public class PorcentajeAsistenciaCriterio extends CriterioModificacionReunion {

//Attributes
	private int porcentajePresenciaRequerido;
	
//Accessing
	public int getPorcentajePresenciaRequerido() {
		return porcentajePresenciaRequerido;
	}

	public void setPorcentajePresenciaRequerido(int porcentajePresenciaRequerido) {
		this.porcentajePresenciaRequerido = porcentajePresenciaRequerido;
	}

//UserMethods

	@Override
	protected boolean aplicarCriterio(Reunion reunion, Recurso recurso) {
		
		return ( ( reunion.cantidadDeRecurosQueAsisten()* 100) / reunion.totalRecursosRequeridos() ) < this.getPorcentajePresenciaRequerido(); 
	}

	@Override
	protected String descripcionCriterio() {
		
		return "Cancelar la reunion si hay menos de un porcentaje de asistencia elegido por el usuario";
	}
	
	protected void aplicoCriterio(Reunion reunion){
		
		reunion.cancelar();
		
		super.aplicoCriterio(reunion);
	}

}
