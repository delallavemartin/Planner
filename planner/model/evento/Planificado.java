package model.evento;

import exception.UserException;

class Planificado extends EstadoReunion {

	private static Planificado instancia = null;
	
	private Planificado(){}
	
	static Planificado getInstance(){
		
		return instancia != null? instancia : new Planificado();
	}
	

	@Override 
	void concretar(Reunion unaReunion) {

		unaReunion.pasarAEstado(Concretado.getInstance());
		
	}

	@Override 
	void finalizar(Reunion laReunion) {
		
		throw new UserException("No es posible finalizar una reunion en estado Planificado");
	}

	@Override
	void replanificar(Reunion reunion) {
		
		reunion.pasarAEstado(Replanificado.getInstance());
	}


}
