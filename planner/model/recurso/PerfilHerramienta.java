package model.recurso;

import model.Edificio;

public class PerfilHerramienta implements PerfilRecurso<Herramienta>{
	
//Attributes
	private Edificio edificio;
	private Herramienta.NombreDeHerramienta nombre;
	
		
//Accessing
	
	public Herramienta.NombreDeHerramienta getNombre() {
		return nombre;
	}
	public void setNombre(Herramienta.NombreDeHerramienta nombre) {
		this.nombre = nombre;
	}
	public Edificio getEdificio() {
		return edificio;
	}
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	
	
//Constructors
	public PerfilHerramienta(Herramienta herramienta){
		
		this.setNombre(herramienta.getNombre());
		this.setEdificio(herramienta.getEdificio());

		
	};
	
	public PerfilHerramienta(){};

	
//UserMethods
	@Override
	public boolean esCompatibleCon(Herramienta herramienta) {
		return this.tieneIgualNombreQue(herramienta) 
					&& this.tieneIgualEdificioQue(herramienta);		
	}
	
	private boolean tieneIgualEdificioQue(Herramienta herramienta) {
		if (herramienta.getEdificio()!= null){
			return this.getEdificio().equals(herramienta.getEdificio());
		}
		return true;
	}
	private boolean tieneIgualNombreQue(Herramienta herramienta) {
		if (herramienta.getNombre()!= null){
			return this.getNombre().equals(herramienta.getNombre());
		}
		return true;
	}

}
