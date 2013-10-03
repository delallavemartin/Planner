package model.recurso;

import model.evento.Reunion;
import model.priorizacion.EstadoDeRecurso;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

public abstract class RecursoReunible implements Recurso{

	private EstadoDeRecurso estado = EstadoDeRecurso.POCAS_REUNIONES;
	
	public Duration dameElTiempoDeReunionesAcumuladoDesdeHace(Period unTiempoAtras ) {

		Duration tiempoDeReunionesAcumuladas = new Duration(0);

		for(Reunion reunionDeRecurso : this.getReuniones()){
			if(laReunionEsDeUnTiempoAtrasAlMomentoActual(unTiempoAtras, reunionDeRecurso ))							
				tiempoDeReunionesAcumuladas.plus(reunionDeRecurso.getDuracion());					
		}
		return tiempoDeReunionesAcumuladas;
	}
	
	private boolean laReunionEsDeUnTiempoAtrasAlMomentoActual(Period unTiempoAtras ,Reunion reunion ) {
		
		DateTime comienzoDelIntervaloAConsiderar = new DateTime( DateTime.now().getMillis() -  unTiempoAtras.getMillis() ); 
		
		Interval lapsoDeTiempoAConsiderar = new Interval(comienzoDelIntervaloAConsiderar , DateTime.now() );
		return lapsoDeTiempoAConsiderar.contains(reunion.getFecha());
	}

	public EstadoDeRecurso getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoDeRecurso estado) {
		this.estado = estado;
	}

}
