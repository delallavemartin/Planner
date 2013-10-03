package model.recurso;

import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;

import model.Edificio;
import model.evento.Reunion;
import model.priorizacion.EstadoDeRecurso;

public interface Recurso {
	
	public void agendarReunion(Reunion reunion);
	
	public boolean estasDisponiblePara(Reunion reunion);
	
	public List<Reunion> getReuniones();
	
	public double getPrecio();
		
	public Edificio getEdificio();
	
	public double presupuestoPara(Reunion reunion);
	
	public EstadoDeRecurso getEstado();

	public Duration dameElTiempoDeReunionesAcumuladoDesdeHace(Period unTiempoAtras );

	
}
