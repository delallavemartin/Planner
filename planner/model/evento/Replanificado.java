package model.evento;

import exception.UserException;

public class Replanificado extends EstadoReunion {
	
	private static Replanificado instancia = null;
	
	private Replanificado(){}
	
	static Replanificado getInstance(){
		
		return instancia != null? instancia : new Replanificado();
	}

	@Override
	void concretar(Reunion reunion) {
		
		throw new UserException("No es posible concretar una reunion en estado replanificado");
	}

	@Override
	void finalizar(Reunion reunion) {
		throw new UserException("No es posible finalizar una reunion en estado replanificado");

	}

	@Override
	void replanificar(Reunion reunion) {
		throw new UserException("No es posible volver a replanificar una reunion");

	}

}
