package model.evento;

import exception.UserException;

class Finalizado extends EstadoReunion {
	
	private static Finalizado instancia = null;
	
	private Finalizado(){}
	
	static Finalizado getInstance(){
		
		return instancia != null? instancia : new Finalizado();
	}
	
	@Override
	public void validarAgregarIntegranteAReunion(){
		
		throw new UserException("No se puede agregar recurso a reunion finalizada");
	}
	
	@Override 
	void concretar(Reunion unaReunion) {
			throw new UserException("No se puede Concretar la reunion porque ya esta finalizada");
		
	}

	@Override 
	void finalizar(Reunion laReunion) {
		throw new UserException("No se puede finalizar la reunion porque ya esta finalizada");
		
	}

	@Override
	void replanificar(Reunion reunion) {
		
		throw new UserException("No se puede replanificar la reunion porque ya esta finalizada");
	}

}
