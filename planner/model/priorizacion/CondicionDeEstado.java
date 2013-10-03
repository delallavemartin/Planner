package model.priorizacion;

import model.recurso.Recurso;

import org.joda.time.Duration;
import org.joda.time.Period;


public class CondicionDeEstado {

	private  Duration duracionDeReunionesAcumuladasMinimaParaCumplirCondicion;
	private  Period tiempoAtrasAPartirDelCualConsiderar;
	private  boolean laCondicionDebeCumplirseParaTodos;
	
	private CondicionDeEstado(){
		this.laCondicionDebeCumplirseParaTodos = true;
		this.duracionDeReunionesAcumuladasMinimaParaCumplirCondicion = null;
		this.tiempoAtrasAPartirDelCualConsiderar = null;
	}
	
	public CondicionDeEstado(Duration duracionDeReunionesAcumuladasMinimaParaCumplirCondicion,
							 Period tiempoAtrasAPartirDelCualConsiderar) {

		this.laCondicionDebeCumplirseParaTodos = false;
		this.duracionDeReunionesAcumuladasMinimaParaCumplirCondicion = duracionDeReunionesAcumuladasMinimaParaCumplirCondicion;
		this.tiempoAtrasAPartirDelCualConsiderar = tiempoAtrasAPartirDelCualConsiderar;
	}

	public boolean teCumplisPara(Recurso unRecurso) {
		
		return this.laCondicionDebeCumplirseParaTodos ? true : laCondicionSeCumplePara(unRecurso);
			
	}

	private boolean laCondicionSeCumplePara(Recurso unRecurso) {
		Duration tiempoDeReunionesAcumuladas = unRecurso.dameElTiempoDeReunionesAcumuladoDesdeHace(this.tiempoAtrasAPartirDelCualConsiderar);	
		return tiempoDeReunionesAcumuladas.isLongerThan(this.duracionDeReunionesAcumuladasMinimaParaCumplirCondicion);
	}

	
}
