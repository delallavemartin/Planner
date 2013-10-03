package model.evento;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.MutableInterval;

import model.recurso.PerfilRecurso;
import model.recurso.Recurso;
import model.evento.criterioModificacion.*;
public class RequerimientoReunion {
	
//Attributes
	
	
	private List<CriterioModificacionReunion> criteriosAAplicarAnteUnaBajaDeUnRecurso;

	
	protected MutableInterval horarioTentativo;
	protected DateTime fechaTentativa;
	
	private List<PerfilRecurso> perfilesOpcionales;
	private List<PerfilRecurso> perfilesObligatorios;

//Accessing	
	public List<PerfilRecurso> getRecursosOpcionales() {
		return perfilesOpcionales;
	}
	public void setRecursosOpcionales(List<PerfilRecurso> recursosOpcionales) {
		this.perfilesOpcionales = recursosOpcionales;
	}
	public List<PerfilRecurso> getRecursosObligatorios() {
		return perfilesObligatorios;
	}
	public void setRecursosObligatorios(List<PerfilRecurso> recursosObligatorios) {
		this.perfilesObligatorios = recursosObligatorios;
	}
	
		
	
	private DateTime getFechaTentativa() {
		return fechaTentativa;
	}
	private void setFechaTentativa(DateTime fechaTentativa) {
		this.fechaTentativa = fechaTentativa;
	}
	private MutableInterval getHorarioTentativo() {
		return horarioTentativo;
	}
	private void setHorarioTentativo(MutableInterval horarioTentativo) {
		this.horarioTentativo = horarioTentativo;
	}
	private List<CriterioModificacionReunion> getCriteriosAAplicarAnteUnaBajaDeUnRecurso() {
		return criteriosAAplicarAnteUnaBajaDeUnRecurso;
	}
	private void setCriteriosAAplicarAnteUnaBajaDeUnRecurso(
			List<CriterioModificacionReunion> criteriosAAplicarAnteUnaBajaDeUnRecurso) {
		this.criteriosAAplicarAnteUnaBajaDeUnRecurso = criteriosAAplicarAnteUnaBajaDeUnRecurso;
	}
//Constructor
	

	public RequerimientoReunion(List<PerfilRecurso> opcionales, List<PerfilRecurso> obligatorios){
		
		this.setRecursosObligatorios(obligatorios);
		this.setRecursosOpcionales(opcionales);
	}
	
//UserMethods
	
	public List<PerfilRecurso> perfiles(){
		
		List<PerfilRecurso> perfiles = new LinkedList<PerfilRecurso>();
		
		perfiles.addAll(this.getRecursosObligatorios());
		perfiles.addAll(this.getRecursosOpcionales());
		
		return perfiles;
	}
	
	public boolean faltanRecursosObligatorio(Reunion reunion) {
		
		return this.faltanRecursos(this.getRecursosObligatorios() , reunion);
	}
	
	public boolean faltanRecursosOpcional(Reunion reunion) {
		
		return this.faltanRecursos(this.getRecursosOpcionales(), reunion);
	}
	
	private boolean faltanRecursos(List<PerfilRecurso> recursosOpcionales, Reunion reunion) {

		for ( PerfilRecurso<Recurso> perfil : recursosOpcionales ){
			
			boolean coincidencia = false;
			
			for(Recurso recurso : reunion.getRecursosDisponibles() ){
				if( perfil.esCompatibleCon(recurso) ){
					
					coincidencia = true;
				}
			}
			if (!coincidencia){
				
				return true; //si no hubo ningun recurso con ese perfil => falta
			}
		}
		
		return false;
	}
	public void reempleazarPerfil(PerfilRecurso<Recurso> perfilOriginal, PerfilRecurso<Recurso> perfilReemplazo) {
		
		if(this.getRecursosObligatorios().contains(perfilOriginal)){
			
			this.quitarPerfilObligatiorio(perfilOriginal);
			this.agregarPerfilObligatorio(perfilReemplazo);
		}
		
		if(this.getRecursosOpcionales().contains(perfilOriginal)){
			this.quitarPerfilOpcional(perfilOriginal);
			this.agregarPerfilOpcional(perfilReemplazo);
		}
	}
	private void agregarPerfilOpcional(PerfilRecurso<Recurso> nuevoPerfil) {
		
		this.getRecursosOpcionales().add(nuevoPerfil);
	}
	
	private void quitarPerfilOpcional(PerfilRecurso<Recurso> viejoPerfil) {
		
		this.getRecursosOpcionales().remove(viejoPerfil);
	}
	private void agregarPerfilObligatorio(PerfilRecurso<Recurso> nuevoPerfil) {
		
		this.getRecursosObligatorios().add(nuevoPerfil);
	}
	private void quitarPerfilObligatiorio(PerfilRecurso<Recurso> viejoPerfil) {
		
		this.getRecursosObligatorios().remove(viejoPerfil);
	}
	
	
}
