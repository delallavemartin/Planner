package model.evento;

import org.joda.time.Duration;

public class TareaComplejidadComplicada extends TareaSimple {

	@Override
	public double costo() {
		if (this.cantidadDeDias() < 5){
			return costoBase();
		}
		return this.costoExtra() + this.costoBase();
	}

	private double costoExtra() {
		return (this.cantidadDeDias() - 5) * 30;
	}

	private long cantidadDeDias() {
		Duration duracion = new Duration(this.getInicio(), this.getFin());
		return duracion.getStandardDays();
	}

	private double costoBase() {
		return this.getResponsable().getPrecio() *  this.getDuracionEnHoras();
	}

}
