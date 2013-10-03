package model;

import java.util.HashSet;
import java.util.Set;

import model.recurso.EmpleadoImp;
import model.recurso.Herramienta;
import model.recurso.Sala;

public class Edificio {

//Attributes
	private String nombre;
	private Set<EmpleadoImp> empleados = new HashSet<EmpleadoImp>();
	private Set<Sala> salas = new HashSet<Sala>();
	private Set<Herramienta> herramientas = new HashSet<Herramienta>();
	
//Accessing
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Set<EmpleadoImp> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(Set<EmpleadoImp> empleados) {
		this.empleados = empleados;
	}
	public Set<Sala> getSalas() {
		return salas;
	}
	public void setSalas(Set<Sala> salas) {
		this.salas = salas;
	}
	public void agregarSala(Sala sala) {
		this.salas.add(sala);
	}
	public Set<Herramienta> getHerramientas() {
		return herramientas;
	}
	public void setHerramientas(Set<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}
	
}
