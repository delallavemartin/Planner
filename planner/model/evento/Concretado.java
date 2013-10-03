package model.evento;

import exception.UserException;

class Concretado extends EstadoReunion {
	
	private static Concretado instancia = null;

	private Concretado(){}
	
	static Concretado getInstance(){
		
		return instancia != null? instancia: new Concretado();
	}
	
	public void asignarEstadoA(Reunion reunion ){
		
		reunion.validarPasoAEstadoConcretado();
		
		super.asignarEstadoA(reunion);
		
		reunion.pasoAEstadoConcretado();
	}
	
	@Override
	void concretar(Reunion unaReunion) {
		throw new UserException("No se puede concretar una reunion que ya esta concretada");
	}

	@Override
	void finalizar(Reunion reunion) {
		
		reunion.pasarAEstado(Finalizado.getInstance());
		
	}

	@Override
	void replanificar(Reunion reunion) {
		
		reunion.pasarAEstado(Replanificado.getInstance());
	}
	
	public boolean verificarSuperposicion() {
		
		return true;
	}
	
}
