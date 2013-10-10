package model.evento;

import model.recurso.Empleado;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableInterval;

public abstract class Evento implements Comparable<Evento>{
	private static final Duration SIN_MARGEN = new Duration(0);
		
	protected MutableInterval horario;
	protected DateTime fecha;
	
	public boolean tenesHorario() {
		return this.getFecha() != null && this.getHorario() != null;
	}
	
	public double duracion() {
		return (this.getHorario().toDuration().getStandardMinutes()/60.0);
	}

	public Duration getDuracion() {
		return this.getHorario().toDuration();
	}
	//Comparaciones
	public boolean teSuperponesCon(Evento eventoAOrganizar) {
		return this.teSuperponesCon(eventoAOrganizar, SIN_MARGEN);
	}


	public boolean teSuperponesCon(Evento eventoAOrganizar, Duration margen) {
		return this.seSuperponeConHorarioYFechaDe(eventoAOrganizar, margen);
	}

	public boolean seSuperponeConHorarioYFechaDe(Evento otroEvento, Duration margen) {
		return (this.tenesMismaFechasQue(otroEvento) && this.coincidenHorarios(otroEvento.horario,margen));
	}

	protected boolean tenesMismaFechasQue(Evento otroEvento) {
		return this.fecha.equals(otroEvento.getFecha());
	}

	public boolean coincidenHorarios(MutableInterval horarioAConcretar, Duration margen) {
		horarioAConcretar.setEnd(horarioAConcretar.getEnd().plus(margen));
		return this.horario.overlaps(horarioAConcretar);
	}
	
	@Override
	public int compareTo(Evento otroEvento) {
		DateTime miHoraDeInicio = this.getHorario().getStart();
		DateTime otraReunionHoraDeInicio = otroEvento.getHorario().getStart();
		
		return miHoraDeInicio.compareTo(otraReunionHoraDeInicio);
		
	}
	
//Accessing
	public MutableInterval getHorario() {
		return horario;
	}
	public void setHorario(MutableInterval horario) {
		this.horario = horario;
	}
	public DateTime getFecha() {
		return fecha;
	}
	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}

	public abstract Duration tiempoParaTrasladarmeA(Empleado empleadoImp);
	
}
