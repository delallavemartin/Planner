package model.evento;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.joda.time.Duration;
import exception.UserException;
import model.Edificio;
import model.Proyecto;
import model.AccionesPorConcrecionDeReunion.AccionesPorConcrecionDeReunion;
import model.AccionesPorConcrecionDeReunion.ObservadorDeEstadoConcretadoDeReunion;
import model.evento.criterioModificacion.CriterioModificacionReunion;
import model.priorizacion.Priorizacion;
import model.recurso.Empleado;
import model.recurso.Organizador;
import model.recurso.PerfilRecurso;
import model.recurso.Recurso;
import model.recurso.Sala;

public class Reunion extends Evento implements Comparable<Evento>,ObservadorDeEstadoConcretadoDeReunion{

//Attributes
	private Organizador organizador;
	private Set<ObservadorDeEstadoConcretadoDeReunion> observadorDeReunion;
	private Set<AccionesPorConcrecionDeReunion> accionesPorConcrecionDeReunion;
	private boolean catering;	
	private Sala sala;
	private Set<Recurso> recursos;
	private RequerimientoReunion requerimiento;
	private EstadoReunion estado;
	private Priorizacion<Recurso> prioridadSeleccion;
	private CriterioModificacionReunion criterioModificacion;
	
//Accessing

	public CriterioModificacionReunion getCriterioModificacion() {
		return criterioModificacion;
	}

	public void setCriterioModificacion(CriterioModificacionReunion criterioModificacion) {
		this.criterioModificacion = criterioModificacion;
	}

	public Priorizacion<Recurso> getPrioridadSeleccion() {
		return prioridadSeleccion;
	}

	public void setPrioridadSeleccion(Priorizacion<Recurso> prioridadSeleccion) {
		this.prioridadSeleccion = prioridadSeleccion;
	}
	public EstadoReunion getEstado() {
		return estado;
	}

	public void setEstado(EstadoReunion estado) {
		this.estado = estado;
	}

	public RequerimientoReunion getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(RequerimientoReunion requerimiento) {
		this.requerimiento = requerimiento;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
		this.agregaRecurso(sala);
	}

	public void setCatering(boolean catering) {
		this.catering = catering;
	}

	public boolean getCatering() {
		return this.catering;
	}
	public Organizador getOrganizador() {
		
		return this.organizador;
	}
	
	public void setOrganizador(Organizador organizador) { //cambiado para correr los tests deberia ser private
		this.organizador = organizador;
	}
	
	public void asignarPresupuestoAProyecto() {
		this.getOrganizador().asignarCostoDeEvento(this.presupuestar());
		
	}
	
	public Set<Recurso> getRecursos() {
		return this.recursos;
	}
	public void setRecursos(Set<Recurso> recursos){
		this.recursos = recursos;
	}
	
	public Set<ObservadorDeEstadoConcretadoDeReunion> getObservadorDeReunion() {
		return observadorDeReunion;
	}

	public void setObservadorDeReunion(
			Set<ObservadorDeEstadoConcretadoDeReunion> observadorDeReunion) {
		this.observadorDeReunion = observadorDeReunion;
	}
	
	public Set<AccionesPorConcrecionDeReunion> getAccionesPorConcrecionDeReunion() {
		return accionesPorConcrecionDeReunion;
	}

	public void setAccionesPorConcrecionDeReunion(
			Set<AccionesPorConcrecionDeReunion> accionesPorConcrecionDeReunion) {
		this.accionesPorConcrecionDeReunion = accionesPorConcrecionDeReunion;
	}
	
//Constructors
	public Reunion(Organizador organizador)
	{		
		this.setOrganizador(organizador);
		this.estado = Planificado.getInstance();
		this.recursos = new HashSet<Recurso>();
		this.agregaRecurso(this.getOrganizador());
	
	}
	
//Constructors
	Reunion() {
		this.setOrganizador(organizador);
		this.estado = Planificado.getInstance();
		this.recursos = new HashSet<Recurso>();

	}

//UserMethods
	
	
	public void quitaRecurso(Recurso recurso)
	{
		if(!this.getRecursos().remove(recurso))
			throw new UserException("El recurso que se intenta quitar de la reunion, no se encontraba");

		if(this.getCriterioModificacion() != null )
			this.getCriterioModificacion().aplicarAlgunCriterioParaCancelacionAsistencia(this, recurso);
	
	}
	
	public void agregaRecurso(Recurso nuevoRecurso) {
						
		this.getEstado().validarAgregarIntegranteAReunion();
		
		if(!recursos.add(nuevoRecurso)) 		//devuelve true si no contenia al elemento que se quiere agregar
			throw new UserException("Se intenta agregar a una persona que ya esta en la reunion");
		
		if(!nuevoRecurso.estasDisponiblePara(this)) {		
			recursos.remove(nuevoRecurso);
			throw new UserException("El recurso no esta disponible para la reunion en estado Concretado");
		}
		
		nuevoRecurso.agendarReunion(this);
		
	}
	
	public void agregarRecursos(Set<Recurso> nuevosRecursos ){
		
		for( Recurso nuevoRecurso : nuevosRecursos){
			
			this.agregaRecurso(nuevoRecurso);
		}
	}
	
	public boolean teSuperponesCon(Evento eventoAOrganizar, Duration margen) {
		return this.getEstado().verificarSuperposicion() && this.seSuperponeConHorarioYFechaDe(eventoAOrganizar, margen);
	}
	
	public void agregarObserver(ObservadorDeEstadoConcretadoDeReunion observer){
		this.observadorDeReunion.add(observer);
	}
	
	@Override
	public void seConcretoLaReunion(Reunion unaReunion) throws UserException {
		for (Iterator<AccionesPorConcrecionDeReunion> iterator = this.getAccionesPorConcrecionDeReunion().iterator(); iterator.hasNext();) {
			iterator.next().ejecutarAccion(unaReunion);	
		}		
	}
	
	public Boolean contains(Recurso unaPersona) {
		return this.recursos.contains(unaPersona);
	}
	public int cantidadDeRecursos() {
		return this.recursos.size();
	}
	
	//Faltan tests para notificar
	public void notificarLaConcrecionDeLaReunionASusObservadores() throws UserException{
		for (Iterator<ObservadorDeEstadoConcretadoDeReunion> iterator = this.getObservadorDeReunion().iterator(); iterator.hasNext();) {
			iterator.next().seConcretoLaReunion(this);	
		}
	}
	
	public boolean tenesSalaAsignada(){
		return this.getSala() != null;
	}

	public void solicitarCatering(){
		
		this.setCatering(true);
	}
	
	public boolean estasUbicadaEnEdificio(Edificio edificio) {
		return this.getSala() != null && this.getSala().estasUbicadaEnEdificio(edificio);
	}
	
	public double costoTransporte() {
		return this.getOrganizador().costoTransporte();
	}

	public boolean perteneceAlProyecto(Proyecto proyecto) {
		return this.getOrganizador().getProyecto().equals(proyecto);
	}

	public double presupuestar(){
		
		return this.presupuestoDeObjetosReunibles() + this.presupuestarCatering();
	}
	
	private double presupuestoDeObjetosReunibles(){		
		double costoTotal = 0;		
		Iterator<Recurso> it = this.getRecursos().iterator();		
		while(it.hasNext()){			
			costoTotal += it.next().presupuestoPara(this);
		}		
		return costoTotal;
	}
	
	public double presupuestarCatering(){
		if (this.getCatering()){
			return this.organizador.precioDeCatering();
		}
		return 0;
	}
	
	public void concretar()
	{
		this.getEstado().concretar(this);
	}

	public void finalizar() {
		this.getEstado().finalizar(this);
	}
	
	public void cancelar() {
		this.getEstado().cancelar(this);
	}
	
	public void replanificar() {
		
		this.getEstado().replanificar( this );
	}

	public void pasarAEstado(EstadoReunion estado){
		
		estado.asignarEstadoA(this);
	}
	
	public void validarPasoAEstadoConcretado(){
		
		if (!this.tenesSalaAsignada() || !this.tenesHorario())
			throw new UserException("No se puede concretar reunion si no tiene fijado el horario y la sala");
		
		if (this.getCriterioModificacion() != null) 
			this.getCriterioModificacion().aplicarAlgunCriterioParaConfirmacionDeReunion(this);
		
	}
	
	public void pasoAEstadoConcretado(){
		
		this.asignarPresupuestoAProyecto();
		this.notificarLaConcrecionDeLaReunionASusObservadores();
	}
	
	public boolean necesitasTransporte() {
		return this.estasUbicadaEnEdificio(this.getSala().getEdificio());
	}

	public int cantidadDeRecurosQueAsisten(){
		
		return this.getRecursos().size();
	}
	
	public int totalRecursosRequeridos(){
		
		return this.getRequerimiento().perfiles().size();
	}

	public boolean faltanRecursosObligatorio() {

		return this.getRequerimiento().faltanRecursosObligatorio(this);
	}

	public boolean faltanRecursosOpcional() {
		
		return this.getRequerimiento().faltanRecursosOpcional(this);
	}

	public void reempleazarPerfil(PerfilRecurso<Recurso> perfilOriginal, PerfilRecurso<Recurso> perfilReemplazo) {
		
		this.getRequerimiento().reempleazarPerfil(perfilOriginal,perfilReemplazo);
	}

	public Set<Recurso> getRecursosDisponibles() {
		
		Set<Recurso> disponibles = new HashSet<Recurso>();
		
		for( Recurso recurso : this.getRecursos() ){
			
			if( recurso.estasDisponiblePara(this) ){
				
				disponibles.add(recurso);
			}
		}
		
		return disponibles;
		
	}

	@Override
	public Duration tiempoParaTrasladarmeA(Empleado empleado) {
		Duration margen;
		if (!estasUbicadaEnEdificio(empleado.getEdificio())) {
			margen = new Duration(1800000);// 60000 -> 1 minuto
		} else {
			margen = new Duration(0);
		}
		return margen;
	}

	public Recurso aplicarPrioridadSobre(Set<Recurso> candidatosSeleccionados) {
		return (Recurso) this.getPrioridadSeleccion().aplicateSobreLosRecursosParaLaReunion(candidatosSeleccionados, this);
	}

}
