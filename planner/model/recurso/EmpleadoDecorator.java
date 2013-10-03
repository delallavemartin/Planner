package model.recurso;

import java.util.List;

import model.Edificio;
import model.Empresa;
import model.Proyecto;
import model.evento.Evento;
import model.evento.Reunion;
import model.evento.Actividad;
import model.priorizacion.EstadoDeRecurso;

import org.joda.time.Duration;
import org.joda.time.MutableInterval;
import org.joda.time.Period;

public abstract class EmpleadoDecorator implements Empleado{

	private Empleado empleado;
	
	protected EmpleadoDecorator(Empleado empleado){
		this.setEmpleado(empleado);
	}
	protected Empleado getEmpleado() {
		return empleado;
	}

	protected void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	@Override
	public MutableInterval getHorarioDeTrabajo(){
		return this.getEmpleado().getHorarioDeTrabajo();
	}
	@Override
	public double duracionDeTiempoDeTrabajo(){
		return this.getEmpleado().duracionDeTiempoDeTrabajo();
	}
	@Override
	public void agendarReunion(Reunion reunion) {
		this.getEmpleado().agendarReunion(reunion);		
	}
	@Override
	public void agendarActividad(Actividad actividad){
		this.getEmpleado().agendarActividad(actividad);
	}
	@Override
	public boolean estasDisponiblePara(Reunion reunion) {
		return this.getEmpleado().estasDisponiblePara(reunion);
		
	}
	@Override
	public boolean estasDisponiblePara(Evento evento) {
		return this.getEmpleado().estasDisponiblePara(evento);
		
	}
	@Override
	public List<MutableInterval> dameLosIntervalosDeCuatroHorasLibresQueTengo(){
		return this.getEmpleado().dameLosIntervalosDeCuatroHorasLibresQueTengo();
	}	
	@Override
	public List<Evento> dameTodosLosCompromisos(){
		return this.getEmpleado().dameTodosLosCompromisos();
	}	
	@Override
	public List<Reunion> getReuniones() {
		return this.getEmpleado().getReuniones();
		
	}
	@Override
	public List<Actividad> getActividades() {
		return this.getEmpleado().getActividades();
		
	}
	@Override
	public double getPrecio() {
		return this.getEmpleado().getPrecio();
		
	}
	@Override
	public Edificio getEdificio() {
		return this.getEmpleado().getEdificio();
	}
	@Override
	public double presupuestoPara(Reunion reunion) {
		return this.getEmpleado().presupuestoPara(reunion);		
	}
	@Override
	public Duration obtenerMargen(Evento eventoAOrganizar) {
		return this.getEmpleado().obtenerMargen( eventoAOrganizar);
		
	}
	@Override
	public Duration tiempoParaTrasladarmeA(Evento evento) {
		return this.getEmpleado().tiempoParaTrasladarmeA(evento);
		
	}
	@Override
	public PerfilEmpleado getPerfil() {
		return this.getEmpleado().getPerfil();
		
	}
	@Override
	public Empresa getEmpresa() {
		return this.getEmpleado().getEmpresa(); 
		
	}
	@Override
	public boolean requerisCatering() {
		return this.getEmpleado().requerisCatering();
		
	}
	@Override
	public String getNombre() {
		return this.getEmpleado().getNombre(); 
		
	}
	@Override
	public Proyecto getProyecto() {
		return this.getEmpleado().getProyecto(); 
		
	}
	@Override
	public Rol getRol() {
		return this.getEmpleado().getRol(); 
		
	}
	@Override
	public String getSector() {
		return this.getEmpleado().getSector(); 
		
	}

	@Override
	public void avisarQueNecesitoCatering(Reunion reunion) {
		this.getEmpleado().avisarQueNecesitoCatering( reunion);
		
	}
	@Override
	public double costoTransporte() {
		return this.getEmpleado().costoTransporte();
		
	}
	@Override
	public Duration dameTiempoDedicadoPara(Evento evento){
		return this.getEmpleado().dameTiempoDedicadoPara(evento);
	}
	@Override
	public Duration dameTiempoDedicadoPara(List<Evento> eventos){
		return this.getEmpleado().dameTiempoDedicadoPara(eventos);
	}
	@Override
	public double damePorcentajeDeTiempoDedicadoPara(Evento evento){
		return this.getEmpleado().damePorcentajeDeTiempoDedicadoPara(evento);
	}
	@Override
	public double damePorcentajeDeTiempoDedicadoPara(List<Evento> eventos){
		return this.getEmpleado().damePorcentajeDeTiempoDedicadoPara(eventos);
	}
	@Override
	public double dameDineroInvertidoEn(Evento evento){
		return this.getEmpleado().dameDineroInvertidoEn(evento);
	}
	@Override
	public double dameDineroInvertidoEn(List<Evento> eventos){
		return this.getEmpleado().dameDineroInvertidoEn(eventos);
	}
	@Override
	public void setEmpresa(Empresa empresa) {
		this.getEmpleado().setEmpresa( empresa);
		
	}
	@Override
	public void setNombre(String nombre) {
		this.getEmpleado().setNombre( nombre) ;
		
	}
	@Override
	public void setProyecto(Proyecto proyecto) {
		this.getEmpleado().setProyecto( proyecto);
		
	}
	@Override
	public void setRol(Rol rol) {
		this.getEmpleado().setRol( rol);
		
	}
	@Override
	public void setSector(String sector) {
		this.getEmpleado().setSector( sector); 
		
	}
	@Override
	public void setPrecio(double precio) {
		this.getEmpleado().setPrecio( precio) ;
		
	}
	@Override
	public void setReuniones(List<Reunion> reuniones) {
		this.getEmpleado().setReuniones( reuniones);
		
	}
	@Override
	public void setActividades(List<Actividad> actividades) {
		this.getEmpleado().setActividades( actividades);
		
	}
	@Override
	public void setEdificio(Edificio edificio) {
		this.getEmpleado().setEdificio( edificio) ;
		
	}
	public void setHorarioDeTrabajo(MutableInterval horarioDeTrabajo){
		this.getEmpleado().setHorarioDeTrabajo(horarioDeTrabajo);
	}
	
	@Override
	public EstadoDeRecurso getEstado(){
		return this.getEmpleado().getEstado();
	}	
	@Override
	public Duration dameElTiempoDeReunionesAcumuladoDesdeHace(
			Period unTiempoAtras) {
		return this.getEmpleado().dameElTiempoDeReunionesAcumuladoDesdeHace(unTiempoAtras);
	}
	@Override
	public String getMail() {
		return this.getEmpleado().getMail();
	}
	
	
	//Necesarios para que un empleado se lo considere el mismo para comparadores y las tablas Hash lo accedan (mas alla de que sea un decorador de empleado)
	@Override
	public boolean equals(Object obj) {
		return this.getEmpleado().equals(obj);
	}
	@Override
	public int hashCode() {
		return this.getEmpleado().hashCode();
	}
	
	
	public static boolean esUnDecorador(Object objeto)
	{
		if(objeto == null)
			return false;
		
		Class<?> claseDelObjeto = objeto.getClass();	//si es de tipo Object va a retornar null
		if(claseDelObjeto == null)
			return false;
		
		return claseDelObjeto.getSuperclass()
							 	.equals(EmpleadoDecorator.class);
		
	}
	
	
}
