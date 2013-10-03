package model.evento;

import model.recurso.EmpleadoImp;
import model.recurso.Organizador;

import org.joda.time.DateTime;

public interface Tarea {
	public DateTime inicio = new DateTime();
	public DateTime fin= new DateTime();
	public double costo();
	public boolean estoyCompletada();
	public DateTime getInicio();
	public DateTime getFin();
	public void presupuestar();
	public Organizador responsable = new Organizador(new EmpleadoImp());
}
