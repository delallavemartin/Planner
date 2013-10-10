package model.recurso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Edificio;
import model.Empresa;
import model.recurso.PerfilEmpleado;
import model.Proyecto;
import model.AccionesPorConcrecionDeReunion.AccionesPorConcrecionDeReunion;
import model.AccionesPorConcrecionDeReunion.ObservadorDeEstadoConcretadoDeReunion;
import model.evento.Actividad;
import model.evento.Evento;
import model.evento.Reunion;
import model.priorizacion.EstadoDeRecurso;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.MutableInterval;

import exception.UserException;

public class EmpleadoImp extends RecursoReunible implements Empleado,ObservadorDeEstadoConcretadoDeReunion{

	private EstadoDeRecurso estado = EstadoDeRecurso.POCAS_REUNIONES;

	private String nombre;
	private Proyecto proyecto;
	private Empresa empresa;
	private List<Reunion> reuniones = new ArrayList<Reunion>();
	private List<Actividad> actividades = new ArrayList<Actividad>();
	private Set<AccionesPorConcrecionDeReunion> accionesPorConcrecionDeReunion;
	private Edificio edificio;
	private Rol rol;
	private String sector;
	public double precio = 0;
	private MutableInterval horarioDeTrabajo;
	private String mail;

	public boolean estasDisponiblePara(Evento eventoAOrganizar) {
		return (this.estaDentroDeMiHorarioDeTrabajo(eventoAOrganizar) && this
				.noSeSuperponeConMisEventos(eventoAOrganizar));
	}

	public boolean estasDisponiblePara(Reunion reunionAOrganizar) {
		return (this.estasDisponiblePara((Evento) reunionAOrganizar) && this
				.tengoCuatroHorasSeguidasDeTrabajoAgregando(reunionAOrganizar));
	}

	private boolean tengoCuatroHorasSeguidasDeTrabajoAgregando(
			Reunion unaReunion) {
		List<MutableInterval> intervlosDeCuatroHorasSeguidas = this
				.dameLosIntervalosDeCuatroHorasLibresQueTengo();
		MutableInterval horarioDeLaReunion = unaReunion.getHorario();
		horarioDeLaReunion.setEnd(horarioDeLaReunion.getEnd().plus(
				this.obtenerMargen(unaReunion)));

		for (MutableInterval intervalo : intervlosDeCuatroHorasSeguidas) {
			if (intervalo.overlaps(horarioDeLaReunion)) {
				if (this.tengoUnEspacioDeCuatroHorasEntre(horarioDeLaReunion,
						intervalo)) {
					return true;// Tengo un intervalo de 4 horas libre
				}
			} else {// Tengo un intervalo de 4 horas libre
				return true;
			}
		}
		return false;// Se superpone con todos los intervalos de 4 horas

	}

	private boolean tengoUnEspacioDeCuatroHorasEntre(
			MutableInterval horarioReunion, MutableInterval intervalo) {
		if (this.seSuperponenPorDerecha(horarioReunion, intervalo)
				&& !this.seSuperponenPorIzquierda(horarioReunion, intervalo)) {
			return (intervalo.getStart().plus(Hours.FOUR)
					.isBefore(horarioReunion.getStart()));
		}
		if (this.seSuperponenPorIzquierda(horarioReunion, intervalo)
				&& !this.seSuperponenPorDerecha(horarioReunion, intervalo)) {
			return (horarioReunion.getEnd().plus(Hours.FOUR).isBefore(intervalo
					.getEnd()));
		}
		// se superpone por el medio
		if (!this.seSuperponenPorDerecha(horarioReunion, intervalo)
				&& !this.seSuperponenPorIzquierda(horarioReunion, intervalo)) {
			return (intervalo.getStart().plus(Hours.FOUR)
					.isBefore(horarioReunion.getStart()) || horarioReunion
					.getEnd().plus(Hours.FOUR).isBefore(intervalo.getEnd()));
		}
		return false;
	}

	private boolean seSuperponenPorDerecha(MutableInterval horario,
			MutableInterval intervalo) {
		return (intervalo.contains(horario.getStart()) && !intervalo
				.contains(horario.getEnd()));
	}

	private boolean seSuperponenPorIzquierda(MutableInterval horario,
			MutableInterval intervalo) {
		return (!intervalo.contains(horario.getStart()) && intervalo
				.contains(horario.getEnd()));
	}

	public List<MutableInterval> dameLosIntervalosDeCuatroHorasLibresQueTengo() {
		List<MutableInterval> intervalosSeguidosDe4Horas = new ArrayList<MutableInterval>();
		DateTime inicio = this.getHorarioDeTrabajo().getStart();

		List<Evento> calendario = this.dameTodosLosCompromisos();

		// Ordena por hora de inicio todo
		Collections.sort(calendario);

		for (Evento evento : calendario) {// calendario esta ordenado por hora
											// de inicio
			DateTime inicioEvento = evento.getHorario().getStart();
			DateTime finEvento = evento.getHorario().getEnd();
			if (inicio.plus(Hours.FOUR).isBefore(inicioEvento)) {
				MutableInterval intervalo = new MutableInterval(inicio,
						inicioEvento);
				intervalosSeguidosDe4Horas.add(intervalo);
			}
			inicio = finEvento;
		}

		DateTime finTrabajo = this.getHorarioDeTrabajo().getEnd();

		if (inicio.plus(Hours.FOUR).isBefore(finTrabajo)) {
			MutableInterval intervalo = new MutableInterval(inicio, finTrabajo);
			intervalosSeguidosDe4Horas.add(intervalo);
		}

		return intervalosSeguidosDe4Horas;
	}

	private boolean noSeSuperponeConMisEventos(Evento eventoAOrganizar) {

		for (Evento unEvento : this.dameTodosLosCompromisos()) {
			if (unEvento.teSuperponesCon(eventoAOrganizar,
					this.obtenerMargen(eventoAOrganizar))) {
				return false;
			}
		}
		return true;
	}

	private boolean estaDentroDeMiHorarioDeTrabajo(Evento eventoAOrganizar) {
		return this.getHorarioDeTrabajo().contains(
				eventoAOrganizar.getHorario());
	}

	public ArrayList<Evento> dameTodosLosCompromisos() {
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		eventos.addAll(this.reuniones);
		eventos.addAll(this.actividades);

		return eventos;
	}

	public Duration obtenerMargen(Evento eventoAOrganizar) {
		return this.tiempoParaTrasladarmeA(eventoAOrganizar);
	}

	public Duration tiempoParaTrasladarmeA(Evento unEvento) {
		return unEvento.tiempoParaTrasladarmeA(this);
	}

	@Override
	public double presupuestoPara(Reunion unaReunion) {
		if (unaReunion.perteneceAlProyecto(this.getProyecto())) {
			return 0;
		} else {
			return (this.getPrecio() * unaReunion.getDuracionEnMinutos())
					+ (this.presupuestoAdicionalPorTraslado(unaReunion));
		}
	}

	private double presupuestoAdicionalPorTraslado(Reunion unaReunion) {
		if (!unaReunion.estasUbicadaEnEdificio(this.getEdificio())) {
			return this.empresa.getCostoDeTransporte();
		}
		return 0;
	}

	@Override
	public void agendarReunion(Reunion reunion) {

		this.getReuniones().add(reunion);

		this.avisarQueNecesitoCatering(reunion);
	}

	public void agendarActividad(Actividad actividad) {
		this.getActividades().add(actividad);
	}

	public void avisarQueNecesitoCatering(Reunion reunion) {

		if (this.requerisCatering()) {
			reunion.solicitarCatering();
		}
	}

	public double costoTransporte() {
		return this.empresa.getCostoDeTransporte();
	}

	private boolean tengoLaActividad(Evento evento) {
		if (this.dameTodosLosCompromisos().contains(evento)) {
			return true;
		} else {
			throw new UserException(
					"El empleado no tiene agendada la actividad");
		}
	}

	public Duration dameTiempoDedicadoPara(Evento evento) {
		if (this.tengoLaActividad(evento)) {
			return evento.getDuracion();
		}
		return null;

	}

	public double damePorcentajeDeTiempoDedicadoPara(Evento evento) {
		if (this.tengoLaActividad(evento)) {
			double totalTiempoDeTrabajo = duracionDeTiempoDeTrabajo();
			double porcentaje = (evento.getDuracionEnMinutos() * 100)
					/ totalTiempoDeTrabajo;
			return porcentaje;
		}
		return 0;
	}

	public double duracionDeTiempoDeTrabajo() {
		return this.getHorarioDeTrabajo().toDuration().getStandardMinutes();
	}

	public double dameDineroInvertidoEn(Evento evento) {
		if (this.tengoLaActividad(evento)) {
			return evento.getDuracionEnMinutos() * this.getPrecio();
		}
		return 0;
	}

	public Duration dameTiempoDedicadoPara(List<Evento> eventos) {
		Duration tiempoDedicado = new Duration(0);

		for (Evento evento : eventos) {
			tiempoDedicado = tiempoDedicado.plus(this
					.dameTiempoDedicadoPara(evento));
		}

		return tiempoDedicado;
	}

	public double damePorcentajeDeTiempoDedicadoPara(List<Evento> eventos) {
		double porcentaje = 0.0;

		for (Evento evento : eventos) {
			porcentaje += this.damePorcentajeDeTiempoDedicadoPara(evento);
		}

		return porcentaje;
	}

	public double dameDineroInvertidoEn(List<Evento> eventos) {
		double totalDinero = 0.0;

		for (Evento evento : eventos) {
			totalDinero += this.dameDineroInvertidoEn(evento);
		}

		return totalDinero;
	}

	// Necesarios para que un empleado se lo considere el mismo para
	// comparadores (mas alla de que sea un decorador de empleado)
	@Override
	public boolean equals(Object objetoAComparar) {

		if (EmpleadoDecorator.esUnDecorador(objetoAComparar))
			return objetoAComparar.equals(this);

		return super.equals(objetoAComparar);
	}


	// GETTERS Y SETTERS
	public PerfilEmpleado getPerfil() {

		return new PerfilEmpleado(this);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean requerisCatering() {
		return this.rol.requiereCatering();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

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

	public List<Actividad> getActividades() {
		return this.actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}

	public MutableInterval getHorarioDeTrabajo() {
		return this.horarioDeTrabajo;
	}

	public void setHorarioDeTrabajo(MutableInterval horarioDeTrabajo) {
		this.horarioDeTrabajo = horarioDeTrabajo;
	}

	public EstadoDeRecurso getEstado() {
		return estado;
	}
	
	public Set<AccionesPorConcrecionDeReunion> getAccionesPorConcrecionDeReunion() {
		return accionesPorConcrecionDeReunion;
	}

	public void setAccionesPorConcrecionDeReunion(
			Set<AccionesPorConcrecionDeReunion> accionesPorConcrecionDeReunion) {
		this.accionesPorConcrecionDeReunion = accionesPorConcrecionDeReunion;
	}

	@Override
	public void seConcretoLaReunion(Reunion unaReunion) throws UserException {
		for (Iterator<AccionesPorConcrecionDeReunion> iterator = this.getAccionesPorConcrecionDeReunion().iterator(); iterator.hasNext();) {
			iterator.next().ejecutarAccion(unaReunion);	
		}	
		
	}

}
