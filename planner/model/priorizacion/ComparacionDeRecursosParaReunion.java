package model.priorizacion;

import java.util.Comparator;

import exception.UserException;

import model.evento.Reunion;
import model.recurso.Recurso;

public abstract class ComparacionDeRecursosParaReunion implements Comparator<Recurso> {

	protected enum ResultadoDeComparacion{
		EL_PRIMERO_TIENE_MAS_PRIORIDAD		(-1),
		TIENEN_IGUAL_PRIORIDAD				(0),
		EL_SEGUNDO_TIENE_MAS_PRIORIDAD		(1);
		
		private int valor;
		ResultadoDeComparacion(int valor){
			this.valor = valor;
		}
		public int toInt(){
			return this.valor;
		}
	}
	
	private Reunion reunion;
	
	
	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}


	@Override
	public int compare(Recurso primerRecurso, Recurso segundoRecurso) {
		
		this.validaReunionParaLaQueSeQuiereComparar();
		ResultadoDeComparacion resultado = comparaRecursos( primerRecurso, segundoRecurso );
		return resultado.toInt();
	}

	private void validaReunionParaLaQueSeQuiereComparar() {
		if(this.getReunion() == null)
			throw new UserException("No se establecio la reunion para la cual se quiere hacer la comparacion");
	}

	protected abstract ResultadoDeComparacion comparaRecursos(Recurso primerRecurso, Recurso segundoRecurso ); 
	
}
