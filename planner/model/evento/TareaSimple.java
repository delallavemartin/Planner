package model.evento;

import model.recurso.Organizador;

import org.joda.time.DateTime;
import org.joda.time.Duration;

abstract class TareaSimple implements Tarea{
	private DateTime inicio = new DateTime(3900,1,1,12,0);
	private DateTime fin = new DateTime(1900,1,1,12,0);
	private Organizador responsable;
	private double costoPorHora = 0.00;
	
	//GETTERS Y SETTERS
	public DateTime getInicio() {
		return inicio;
	}
	public DateTime getFin() {
		return fin;
	}
	public void setInicio(DateTime horaInicio) {
		this.inicio = horaInicio;		
	}
	public void setFin(DateTime horaFin) {
		this.fin = horaFin;		
	}
	public Organizador getResponsable() {
		return responsable;
	}
	public void setResponsable(Organizador responsable) {
		this.responsable = responsable;
	}
	public double getCostoPorHora() {
		return costoPorHora;
	}
	public void setCostoPorHora(double costoPorHora) {
		this.costoPorHora = costoPorHora;
	}
	
	//IMPLEMENTACION DE LA INTERFACE
	@Override
	public abstract double costo();

	@Override
	public boolean estoyCompletada() {
		return this.getFin().isBeforeNow();
	}
	
	protected double getDuracionEnHoras() {
		Duration duracion = new Duration(this.getInicio(),this.getFin());
		return (double) duracion.getStandardMinutes() / 60.00;
	}
	
	@Override
	public void presupuestar() {
		this.getResponsable().asignarCostoDeEvento(this.costo());	
	}

}
