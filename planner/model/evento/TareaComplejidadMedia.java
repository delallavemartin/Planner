package model.evento;

public class TareaComplejidadMedia extends TareaSimple {

	@Override
	public double costo() {
		return (1.06)* this.getDuracionEnHoras() * this.getResponsable().getPrecio();
	}

}
