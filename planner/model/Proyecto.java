package model;

import java.util.HashSet;
import java.util.Set;

import model.evento.Tarea;
import model.recurso.Empleado;
import model.recurso.EmpleadoImp;

public class Proyecto {
	private String nombre;
	private Set<Tarea> tareas = new HashSet<Tarea>();
	private Set<EmpleadoImp> empleadosClaves = new HashSet<EmpleadoImp>();
	private Set<EmpleadoImp> projectManagers;
	
	public Set<EmpleadoImp> getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(Set<EmpleadoImp> projectManagers) {
		this.projectManagers = projectManagers;
	}

	public Set<EmpleadoImp> getEmpleadosClaves() {
		return empleadosClaves;
	}

	public void setEmpleadosClaves(Set<EmpleadoImp> empleadosClaves) {
		this.empleadosClaves = empleadosClaves;
	}

	private double costo = 0;
	

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	public void incrementarCosto(double unMonto){
		this.setCosto(this.getCosto()+ unMonto);
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}		
	
	public double porcentajeDeTareasCompletadas(){
		int cantidadTareasCompletas = 0;
		for(Tarea tarea:this.getTareas()){
			if(tarea.estoyCompletada()){
				cantidadTareasCompletas++;
			}
		}
		return obtenerPorcentajeDeTareasCompletadas(cantidadTareasCompletas);
	}

	private double obtenerPorcentajeDeTareasCompletadas(
			int cantidadTareasCompletas) {
		return (double) cantidadTareasCompletas * 100.00 / (double) this.getTareas().size();
	}

	public Set<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public void agregarTarea(Tarea tarea){
		this.tareas.add(tarea);
	}

	public boolean soyPersonaClave(EmpleadoImp empleado) {
		return this.getEmpleadosClaves().contains(empleado);
	}
}
