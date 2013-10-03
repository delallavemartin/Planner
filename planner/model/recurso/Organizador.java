package model.recurso;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import model.evento.RequerimientoReunion;
import model.evento.Reunion;
import model.priorizacion.Priorizacion;
import model.recurso.Herramienta.NombreDeHerramienta;
import exception.UserException;

//decorator
public class Organizador extends EmpleadoDecorator {

	
	public Organizador(Empleado empleado) {
		super(empleado);
		this.validaQueTodaviaNoEsteDecoradoComoOrganizador(empleado);
	}

	private void validaQueTodaviaNoEsteDecoradoComoOrganizador(Empleado empleado) {		
		if(Organizador.class.equals(empleado.getClass() ) )
			throw new UserException("El Empleado que se quiere decorar como Organizador, ya es un Organizador");
	}
	

	public double precioDeCatering() {
		return this.getEmpresa().getCatering();
	}

	public void asignarCostoDeEvento(double unMonto){
		this.getProyecto().incrementarCosto(unMonto);
	}

		
	public Reunion organizaReunionCon(RequerimientoReunion requerimiento,Sala sala, Priorizacion<Recurso> prioridad)
	{
		Reunion reunion = new Reunion(this);
		
		reunion.setPrioridadSeleccion(prioridad);
		reunion.setRequerimiento(requerimiento);
		reunion.setSala(sala);
		
		Set<Recurso> recursos = this.obtenerRecursosParaRequerimientoReunion(requerimiento, reunion);
		
		reunion.agregarRecursos(recursos);
		
		return reunion;
	}
		
	private Set<Recurso> obtenerRecursosParaRequerimientoReunion(RequerimientoReunion requerimiento,Reunion reunion){
		
		return this.getEmpresa().recursosParaReunionConPerfiles(requerimiento.perfiles(),reunion);
	}

	
	//TODO hacer refactor necesario para que sea privado y se utilice organizaReunionCon(....)
//	public void asignaUnEmpleadoQueCumplaPerfilAReunion(PerfilEmpleado perfil, 	Reunion reunion) {
//
//		
//		
//		Set<Recurso> candidatos = this.getEmpresa().dameEmpleadosConPerfil(perfil);
//
//		if (candidatos.isEmpty()) 
//			throw new UserException("No hay un empleado que cumpla con el perfil pedido que pueda asistir a la reunion");
//		 
//		this.agregarUnEmpleadoAReunion(candidatos, reunion);
//		
//	}



	private void agregarUnEmpleadoAReunion(Set<Recurso> candidatos, Reunion reunion) {

		for (Recurso candidato : candidatos) {

			if (candidato.estasDisponiblePara(reunion)) {
				reunion.agregaRecurso(candidato);
				return;
			}

		}
		throw new UserException("No hay un empleado que pueda asistir a la reunion");

	}

	public String getMailTransporte() {
		return this.getEmpresa().getMailTransporte();
	}
	
	public String getMailCatering() {
		return this.getEmpresa().getMailCatering();
	}
	
	
}

