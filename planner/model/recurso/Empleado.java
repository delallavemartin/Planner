package model.recurso;

import java.util.List;

import model.Edificio;
import model.Empresa;
import model.Proyecto;
import model.evento.Actividad;
import model.evento.Evento;
import model.evento.Reunion;

import org.joda.time.Duration;
import org.joda.time.MutableInterval;

public interface Empleado extends Recurso{

	public Duration obtenerMargen(Evento actividadAOrganizar);
	public Duration tiempoParaTrasladarmeA(Evento evento);
	public PerfilEmpleado getPerfil();
	public Empresa getEmpresa();	//chequear si debería ser de RecursoReunible
	public boolean requerisCatering();
	public String getNombre();
	public Proyecto getProyecto();
	public Rol getRol();
	public String getSector(); 
	public String getMail();
	public void avisarQueNecesitoCatering(Reunion reunion);
	public double costoTransporte(); 
	public List<Actividad> getActividades();
	boolean estasDisponiblePara(Evento evento);
	public List<Evento> dameTodosLosCompromisos();
	public double duracionDeTiempoDeTrabajo();
		
	public Duration dameTiempoDedicadoPara(Evento evento);
	public Duration dameTiempoDedicadoPara(List<Evento> eventos);
	public double damePorcentajeDeTiempoDedicadoPara(Evento evento);
	public double dameDineroInvertidoEn(Evento evento);
	public double damePorcentajeDeTiempoDedicadoPara(List<Evento> eventos);
	public double dameDineroInvertidoEn(List<Evento> eventos);
	public void agendarActividad(Actividad actividad);
//	public double precioDeCatering(); //organizador
//	public void asignarCostoDeReunion(double unMonto);//organizador
	public List<MutableInterval> dameLosIntervalosDeCuatroHorasLibresQueTengo();
	public MutableInterval getHorarioDeTrabajo();
	
	public void setEmpresa(Empresa empresa); //TODO  que no se pueda setear, salvo en su creación
	public void setNombre(String nombre); //idem
	public void setProyecto(Proyecto proyecto); //idem
	public void setRol(Rol rol);  //idem
	public void setSector(String sector); //idem
	public void setPrecio(double precio); //idem
	public void setReuniones(List<Reunion> reuniones); //idem
	public void setEdificio(Edificio edificio); //idem
	public void setHorarioDeTrabajo(MutableInterval horarioDeTrabajo);
	public void setActividades(List<Actividad> actividades);
	
	
	
	
	
	
	



}
