package model.evento;

import exception.UserException;

public class Cancelado extends EstadoReunion {
	
	private static Cancelado instancia = null;

	private Cancelado(){}
	
	static Cancelado getInstance(){
		
		return instancia != null? instancia: new Cancelado();
	}

	@Override
	void concretar(Reunion unaReunion) {
		
		throw new UserException("No es posible concretar una reunion cancelada");
	}

	@Override
	void finalizar(Reunion laReunion) {
		
		throw new UserException("No es posible finalizar una reunion cancelada");
	}

	@Override
	void replanificar(Reunion reunion) {
		
		throw new UserException("No es posible replanificar una reunion cancelada");
		
	}

}
