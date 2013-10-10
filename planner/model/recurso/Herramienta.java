package model.recurso;

import java.util.List;

import model.ComparadorDeDisponibilidad;
import model.Edificio;
import model.evento.Reunion;
import model.priorizacion.EstadoDeRecurso;

public class Herramienta extends RecursoReunible implements PerfilRecurso<Herramienta> {
	
//Attributes
	
	public double precio = 0;
	private List<Reunion> reuniones;
	private Edificio edificio;
	private ComparadorDeDisponibilidad comparadorDeDisponibilidad;
	private Herramienta.NombreDeHerramienta nombre;
	public enum NombreDeHerramienta 
	{
		laptop,
		canion;		
	}

//Constractors
	public Herramienta(Herramienta.NombreDeHerramienta nombreDeHerramienta){
		this.setNombre(nombreDeHerramienta);
	}
	
//Composicion ->soluciona codigo repetido con Sala
	@Override
	public boolean estasDisponiblePara(Reunion reunionAOrganizar) {
		return comparadorDeDisponibilidad.decimeSiEstoyDisponible(reunionAOrganizar,this);
	}
	
	public ComparadorDeDisponibilidad getComparadorDeDisponibilidad() {
		return comparadorDeDisponibilidad;
	}

	public void setComparadorDeDisponibilidad(
			ComparadorDeDisponibilidad comparadorDeDisponibilidad) {
		this.comparadorDeDisponibilidad = comparadorDeDisponibilidad;
	}
	
	@Override
	public double presupuestoPara(Reunion unaReunion) {
		return this.getPrecio() * unaReunion.getDuracionEnMinutos();
	}

	@Override
	public void agendarReunion(Reunion reunion) {
		
		this.getReuniones().add(reunion);
			
	}
	
	
	@Override
	public boolean esCompatibleCon(Herramienta unaHerramienta) {	
		return this.getNombre().equals(unaHerramienta.getNombre());	//por el momento con que tengan el mismo nombre ya son compatibles
	}

//Accessing
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

	public Herramienta.NombreDeHerramienta getNombre() {
		return nombre;
	}

	private void setNombre(Herramienta.NombreDeHerramienta nombreDeHerramienta) {
		this.nombre = nombreDeHerramienta;
	}


}
