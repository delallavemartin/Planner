package model.AccionesPorConcrecionDeReunion;

import org.joda.time.DateMidnight;

import model.evento.Reunion;
import model.recurso.EmpleadoImp;

public class AccionesPorConcrecionDeReunionEmpleadoOcuparDia extends
		AccionesPorConcrecionDeReunionAbstract {
	
	private EmpleadoImp empleado;

	@Override
	public void ejecutarAccion(Reunion reunionConcretada) {
		this.reservarElDia(reunionConcretada);
	}
	
	private void reservarElDia(Reunion reunionConcretada) {
		this.ocuparTodoElDiaConEstaReunion(reunionConcretada);
		this.getEmpleado().agendarReunion(reunionConcretada);
	}

	protected void ocuparTodoElDiaConEstaReunion(Reunion reunionConcretada) {
		DateMidnight fin = reunionConcretada.getFecha().toDateMidnight();
		DateMidnight inicio = fin.minus(23);
		reunionConcretada.getHorario().setStart(inicio);
		reunionConcretada.getHorario().setEnd(fin);
	}
	
	//SETTERS Y GETTERS
	public EmpleadoImp getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadoImp empleado) {
		this.empleado = empleado;
	}

}
