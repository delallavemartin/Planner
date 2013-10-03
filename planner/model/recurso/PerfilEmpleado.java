package model.recurso;

import model.Proyecto;

public class PerfilEmpleado implements PerfilRecurso<Empleado> {

//Attributes
	private String nombre;
	private Rol rol;
	private Proyecto proyecto;
	private String sector;

	
//Constructors
	public PerfilEmpleado(Empleado empleado){
		
		this.setNombre(empleado.getNombre());
		this.setSector(empleado.getSector());
		this.setProyecto(empleado.getProyecto());
		this.setRol(empleado.getRol());
		
	};
	
	public PerfilEmpleado(){};

	
//UserMethods
	@Override
	public boolean esCompatibleCon(Empleado unEmpleado) {
		return(this.tieneIgualNombreQue(unEmpleado) && this.tieneIgualProyectoQue(unEmpleado) && this.tieneIgualRolQue(unEmpleado) && this.tieneIgualSectorQue(unEmpleado));		
	}

	public boolean tieneIgualNombreQue(Empleado unEmpleado) {
		if (unEmpleado.getNombre()!= null){
			return this.getNombre().equals(unEmpleado.getNombre());
		}
		return true;
	}

	public boolean tieneIgualProyectoQue(Empleado unEmpleado) {
		if (unEmpleado.getProyecto()!= null){
			return this.getProyecto().equals(unEmpleado.getProyecto());
		}
		return true;		
	}

	public boolean tieneIgualRolQue(Empleado unEmpleado) {
		if (unEmpleado.getRol()!= null){
			return this.getRol().equals(unEmpleado.getRol());
		}
		return true;
	}

	public boolean tieneIgualSectorQue(Empleado unEmpleado) {
		if (unEmpleado.getSector()!= null){
			return this.getSector().equals(unEmpleado.getSector());
		}
		return true;
	}
	

//Accessing
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Rol getRol() {
		return this.rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
		
	}
	public String getNombre() {
		return this.nombre;
	}
	public Proyecto getProyecto() {
		return this.proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
}

