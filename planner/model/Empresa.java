package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.Duration;
import model.evento.Evento;
import model.evento.Reunion;
import model.recurso.Empleado;
import model.recurso.PerfilRecurso;
import model.recurso.Recurso;
import model.recurso.Rol;

public class Empresa {
	// Attributes
	private String nombre;
	private double catering = 0.0;
	private double costoDeTransporte = 0.0;
	public String mailTransporte;
	public String mailCatering;
	private Set<Recurso> empleados = new HashSet<Recurso>();
	private Set<Edificio> edificios = new HashSet<Edificio>();
	private Set<Proyecto> proyecto = new HashSet<Proyecto>();

	// Accessing
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Recurso> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(Set<Recurso> empleados) {
		this.empleados = empleados;
	}

	public Set<Edificio> getEdificios() {
		return edificios;
	}

	public void setEdificios(Set<Edificio> edificios) {
		this.edificios = edificios;
	}

	public Set<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(Set<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}

// UserMethods
	
	public Set<Recurso> recursosParaReunionConPerfiles( List<PerfilRecurso> perfiles,Reunion reunion){
		
		Set<Recurso> recursos = new HashSet<Recurso>();
		
		Recurso recurso;
		
		for(PerfilRecurso<Recurso> perfil : perfiles){
			
			if( (recurso = this.buscarRecursoParaReunionConPerfil(perfil, reunion)) != null){
				
				recursos.add(recurso);
			}
		}
		
		return recursos;
	}
	

//	@SuppressWarnings("unchecked")
	private Recurso buscarRecursoParaReunionConPerfil(PerfilRecurso<Recurso> perfil,Reunion reunion){
		
		Set<Recurso> candidatos;
		
		try {
			candidatos = (Set<Recurso>) this.getClass().getMethod("recursosCon" + perfil.getClass().toString(), perfil.getClass() ).invoke(perfil); 
		
		}catch(Exception e){
			
			candidatos = new HashSet<Recurso>();
		}
		
		Set<Recurso> candidatosSeleccionados = new HashSet<Recurso>();
		
		for(Recurso recurso : candidatos){
			
			if (perfil.esCompatibleCon(recurso)){
				
				candidatosSeleccionados.add(recurso);
			}
		}
		
		return (Recurso) reunion.getPrioridadSeleccion().aplicateSobreLosRecursosParaLaReunion(candidatosSeleccionados, reunion);
	}
	
	@SuppressWarnings("unused")
	private Set<Recurso> recursosConPerfilEmpleado(){
		
		return this.getEmpleados();
	}
	
	@SuppressWarnings("unused")
	private Set<Recurso> recursosConPerfilHerramienta(){
		
		Set<Recurso> recursos = new HashSet<Recurso>();
		
		for(Edificio edificio : this.getEdificios()){
			
			recursos.addAll(edificio.getHerramientas());
		}

		return recursos;
	}
	
	//Calendario
	public Duration dameTiempoDedicadoAlEventoPorEmpleados(Set<Empleado> empleados, Evento evento){
		Duration tiempoDedicado = new Duration(0);
		
		for(Empleado empleado : empleados){
			tiempoDedicado = tiempoDedicado.plus(empleado.dameTiempoDedicadoPara(evento));
		}
		
		return tiempoDedicado;
	}
	public Duration dameTiempoDedicadoAlEventoPorSector(Set<Empleado> empleados,String sector, Evento evento){
		
		return this.dameTiempoDedicadoAlEventoPorEmpleados(this.dameEmpleadosDeUnSector(sector,empleados), evento);
	}
		private Set<Empleado> dameEmpleadosDeUnSector(String sector,Set<Empleado> empleados){
			Set<Empleado> empleadosDelSector = new HashSet<Empleado>();
			for(Empleado empleado : empleados){
				if(empleado.getSector().equals(sector)){
					empleadosDelSector.add(empleado);
				}
			}
			return empleadosDelSector;
		}
	public Duration dameTiempoDedicadoAlEventoPorRol(Set<Empleado> empleados,Rol rol, Evento evento){
		return this.dameTiempoDedicadoAlEventoPorEmpleados(this.dameEmpleadosDeUnRol(rol,empleados), evento);
	}
		private Set<Empleado> dameEmpleadosDeUnRol(Rol rol,Set<Empleado> empleados){
			Set<Empleado> empleadosDelRol = new HashSet<Empleado>();
			for(Empleado empleado : empleados){
				if(empleado.getRol().equals(rol)){
					empleadosDelRol.add(empleado);
				}
			}
			return empleadosDelRol;
		}
	
	public double damePorcentajeDeTiempoDedicadoAlEventoPorEmpleados(Set<Empleado> empleados, Evento evento){
		double totalTiempoDeTrabajo = tiempoDeTrabajoDeEmpleados(empleados);
		
		double totalTiempoDedicado = this.dameTiempoDedicadoAlEventoPorEmpleados(empleados, evento).getStandardHours() /60.0;
		double porcentaje =  totalTiempoDedicado * 100 / totalTiempoDeTrabajo; 	
		
		return porcentaje;
	}

		private double tiempoDeTrabajoDeEmpleados(Set<Empleado> empleados) {
			double totalTiempoDeTrabajo = 0.0;
					
			for(Empleado empleado :empleados){
				totalTiempoDeTrabajo += empleado.duracionDeTiempoDeTrabajo();
			}
			return totalTiempoDeTrabajo;
		}
	public double damePorcentajeDeTiempoDedicadoAlEventoPorSector(Set<Empleado> empleados,String sector, Evento evento){
		return this.damePorcentajeDeTiempoDedicadoAlEventoPorEmpleados(this.dameEmpleadosDeUnSector(sector,empleados), evento);
	}
	public double damePorcentajeDeTiempoDedicadoAlEventoPorRol(Set<Empleado> empleados,Rol rol, Evento evento){
		return this.damePorcentajeDeTiempoDedicadoAlEventoPorEmpleados(this.dameEmpleadosDeUnRol(rol,empleados), evento);
	}
	
	public double dameDineroInvertidoEnUnEvento(Set<Empleado> empleados, Evento evento){
		double totalInvertido = 0.0;
		
		for(Empleado empleado : empleados){
			totalInvertido += empleado.dameDineroInvertidoEn(evento);
		}
		
		return totalInvertido;
	}
	public double dameDineroInvertidoEnUnEventoPorSector(Set<Empleado> empleados,String sector, Evento evento){
		return this.dameDineroInvertidoEnUnEvento(this.dameEmpleadosDeUnSector(sector,empleados), evento);
	}
	public double dameDineroInvertidoEnUnEventoPorRol(Set<Empleado> empleados,Rol rol, Evento evento){
		return this.dameDineroInvertidoEnUnEvento(this.dameEmpleadosDeUnRol(rol,empleados), evento);
	}
	
	//Calendario para Grupos de eventos
	public Duration dameTiempoDedicadoAEventosPorEmpleados(Set<Empleado> empleados,Set<Evento> eventos){
		Duration tiempoDedicado = new Duration(0);
		for(Evento evento : eventos){
			tiempoDedicado = tiempoDedicado.plus(this.dameTiempoDedicadoAlEventoPorEmpleados(empleados, evento));
		}		
		return tiempoDedicado;
	}
	public Duration dameTiempoDedicadoAEventosPorSector(Set<Empleado> empleados,String sector,Set<Evento> eventos){
		return this.dameTiempoDedicadoAEventosPorEmpleados(this.dameEmpleadosDeUnSector(sector,empleados), eventos);
	}
	public Duration dameTiempoDedicadoAEventosPorRol(Set<Empleado> empleados,Rol rol,Set<Evento> eventos){
		return this.dameTiempoDedicadoAEventosPorEmpleados(this.dameEmpleadosDeUnRol(rol,empleados), eventos);
	}
	public double damePorcentajeDeTiempoDedicadoAEventosPorEmpleados(Set<Empleado> empleados,Set<Evento> eventos){
		double tiempoDeTrabajo = this.tiempoDeTrabajoDeEmpleados(empleados);
		double tiempoDedicadoAEventos = this.dameTiempoDedicadoAEventosPorEmpleados(empleados, eventos).getStandardHours() / 60.0;
		double porcentaje = tiempoDedicadoAEventos * 100 / tiempoDeTrabajo;
		return porcentaje;
	}
	public double damePorcentajeDeTiempoDedicadoAEventosPorSector(Set<Empleado> empleados,String sector,Set<Evento> eventos){
		return this.damePorcentajeDeTiempoDedicadoAEventosPorEmpleados(this.dameEmpleadosDeUnSector(sector,empleados), eventos);
	}
	public double damePorcentajeDeTiempoDedicadoAEventosPorRol(Set<Empleado> empleados,Rol rol,Set<Evento> eventos){
		return this.damePorcentajeDeTiempoDedicadoAEventosPorEmpleados(this.dameEmpleadosDeUnRol(rol,empleados), eventos);
	}
	
	public double dameDineroInvertidoEnEventos(Set<Empleado> empleados,Set<Evento> eventos){
		double totalInvertido = 0.0;
		
		for(Evento evento : eventos){
			totalInvertido += this.dameDineroInvertidoEnUnEvento(empleados, evento);
		}
		
		return totalInvertido;
	}
	public double dameDineroInvertidoEnEventosPorSector(Set<Empleado> empleados,String sector,Set<Evento> eventos){
		return this.dameDineroInvertidoEnEventos(this.dameEmpleadosDeUnSector(sector,empleados), eventos);
	}
	public double dameDineroInvertidoEnEventosPorRol(Set<Empleado> empleados,Rol rol,Set<Evento> eventos){
		return this.dameDineroInvertidoEnEventos(this.dameEmpleadosDeUnRol(rol,empleados), eventos);
	}
	//TODO ver que tipo es conveniente que reciba (EmpleadoImp o Empleado)
	public void agregarEmpleado(Empleado empleado) {

		this.getEmpleados().add(empleado);
	}

	public void agregarEdificio(Edificio edificio) {
		this.edificios.add(edificio);
	}

	public double getCatering() {
		return catering;
	}

	public void setCatering(double catering) {
		this.catering = catering;
	}

	public double getCostoDeTransporte() {
		return costoDeTransporte;
	}

	public void setCostoDeTransporte(double costoDeTransporte) {
		this.costoDeTransporte = costoDeTransporte;
	}

	public String getMailTransporte() {
		return mailTransporte;
	}

	public void setMailTransporte(String mailTransporte) {
		this.mailTransporte = mailTransporte;
	}

	public String getMailCatering() {
		return mailCatering;
	}
	
	
}
	
