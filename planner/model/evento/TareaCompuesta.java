package model.evento;

import java.util.HashSet;
import java.util.Set;

import model.recurso.Organizador;

import org.joda.time.DateTime;

public class TareaCompuesta implements Tarea{
	private DateTime inicio = new DateTime(3900,1,1,12,0);
	private DateTime fin= new DateTime(1900,1,1,12,0);
	private Set<Tarea> tareas = new HashSet<Tarea>();
	private Organizador responsable;

	// GETTERS & SETTERS

	public DateTime getInicio() {
		return inicio;
	}

	public void setInicio() {
		for(Tarea tarea:this.tareas){
			if(this.inicio.isAfter(tarea.getInicio())){
				this.inicio = tarea.getInicio();
			}
		}
	}

	public DateTime getFin() {
		return fin;
	}

	public void setFin() {
		for(Tarea tarea:this.tareas){
			if(this.fin.isBefore(tarea.getFin())){
				this.fin = tarea.getFin();
			}
		}
	}

	public Set<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public Organizador getResponsable() {
		return responsable;
	}

	public void setResponsable(Organizador responsable) {
		this.responsable = responsable;
	}
	
	//IMPLEMENTACION METODOS INTERFAZ
	
	@Override
	public double costo() {
		double costoTareaCompuesta = 0;
		for(Tarea tarea : this.tareas){
			costoTareaCompuesta += tarea.costo();
		}
		return costoTareaCompuesta;
	}

	@Override
	public boolean estoyCompletada() {
		for(Tarea tarea : this.tareas){
			if(!tarea.estoyCompletada()){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void presupuestar() {
		this.getResponsable().asignarCostoDeEvento(this.costo());	
	}
	
	public void calcularFinEInicio() {
		this.setInicio();
		this.setFin();
	}

	public void agregarTarea(Tarea tarea) {
		this.tareas.add(tarea);		
	}

	



	

}
