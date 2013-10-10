package model.recurso;

import java.util.ArrayList;
import java.util.List;

import model.ComparadorDeDisponibilidad;
import model.Edificio;
import model.evento.Reunion;
import model.priorizacion.EstadoDeRecurso;

public class Sala extends RecursoReunible {
	
	
	public double precio = 0.00;
	private List<Reunion> reuniones = new ArrayList<Reunion>();
	private Edificio edificio;
	private ComparadorDeDisponibilidad comparadorDeDisponibilidad = new ComparadorDeDisponibilidad();

//Composicion ->soluciona codigo repetido con Herramienta
	public boolean estasDisponiblePara(Reunion reunionAOrganizar) {
		return this.comparadorDeDisponibilidad.decimeSiEstoyDisponible(reunionAOrganizar,this);
	}	


	public ComparadorDeDisponibilidad getComparadorDeDisponibilidad() {
		return comparadorDeDisponibilidad;
	}

	public void setComparadorDeDisponibilidad(
			ComparadorDeDisponibilidad comparadorDeDisponibilidad) {
		this.comparadorDeDisponibilidad = comparadorDeDisponibilidad;
	}
	
	
	@Override
	public void agendarReunion(Reunion reunion){
		
		this.getReuniones().add(reunion);
		
	}
	
	@Override
	public double presupuestoPara(Reunion reunion){
		
		return this.getPrecio() * reunion.getDuracionEnMinutos(); 
	}
	
	

	public boolean estasUbicadaEnEdificio(Edificio edificio) {
		return this.getEdificio().equals(edificio);
	}
	
	//Getters & Setters
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public List<Reunion> getReuniones() {
		return reuniones;
	}
	public void setReuniones(List<Reunion> reuniones) {
		this.reuniones = reuniones;
	}
	public Edificio getEdificio() {
		return edificio;
	}
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}


}
