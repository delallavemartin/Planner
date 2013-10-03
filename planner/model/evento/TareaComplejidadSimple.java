package model.evento;

public class TareaComplejidadSimple extends TareaSimple {

	@Override
	public double costo() {
		return this.getCostoPorHora() * this.getDuracionEnHoras();
	}

}
