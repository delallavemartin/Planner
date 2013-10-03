package model.evento;


public abstract class EstadoReunion {

	abstract void concretar(Reunion reunion);
	
	abstract void finalizar(Reunion reunion);

	abstract void replanificar(Reunion reunion);
	
	public void cancelar(Reunion reunion){
		
		reunion.pasarAEstado(Cancelado.getInstance() );
	}
	
	public void asignarEstadoA(Reunion reunion){
		
		reunion.setEstado(this);
	}
	
	public void validarAgregarIntegranteAReunion(){
		
		//se puede agregar integrante
	}

	public boolean verificarSuperposicion() {
		
		return false;
	}
	
}