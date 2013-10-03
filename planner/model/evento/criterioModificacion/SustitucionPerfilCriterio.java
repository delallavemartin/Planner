package model.evento.criterioModificacion;

import model.evento.Reunion;
import model.recurso.PerfilRecurso;
import model.recurso.Recurso;

public class SustitucionPerfilCriterio extends CriterioModificacionReunion {

//Attributes
	private PerfilRecurso<Recurso> perfilOriginal;
	private PerfilRecurso<Recurso> perfilReemplazo;

//Accessing
	public PerfilRecurso<Recurso> getPerfilOriginal() {
		return perfilOriginal;
	}

	public void setPerfilOriginal(PerfilRecurso<Recurso> perfilOriginal) {
		this.perfilOriginal = perfilOriginal;
	}

	public PerfilRecurso<Recurso> getPerfilReemplazo() {
		return perfilReemplazo;
	}

	public void setPerfilReemplazo(PerfilRecurso<Recurso> perfilReemplazo) {
		this.perfilReemplazo = perfilReemplazo;
	}

//UserMethods

	@Override
	protected boolean aplicarCriterio(Reunion reunion, Recurso recurso) {
		
		return this.perfilOriginal.esCompatibleCon(recurso);
		
	}

	@Override
	protected String descripcionCriterio() {
		
		return "Asociar un criterio de seleccion alternativo para el criterio original";
	}

	protected void aplicoCriterio(Reunion reunion){
		
		reunion.reempleazarPerfil(this.getPerfilOriginal(), this.getPerfilReemplazo());
		
		super.aplicoCriterio(reunion);
	}
}
