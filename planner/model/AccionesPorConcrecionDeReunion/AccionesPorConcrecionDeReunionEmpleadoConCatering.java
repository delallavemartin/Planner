package model.AccionesPorConcrecionDeReunion;

import model.evento.Reunion;
import model.mailService.MailSenderEmpleado;
import model.recurso.EmpleadoImp;

public class AccionesPorConcrecionDeReunionEmpleadoConCatering extends
		AccionesPorConcrecionDeReunionAbstract {

	private EmpleadoImp empleado;

	@Override
	public void ejecutarAccion(Reunion reunionConcretada) {
		super.informar(reunionConcretada, new MailSenderEmpleado(this.getEmpleado()));
	}
	
	//SETTERS Y GETTERS
	public EmpleadoImp getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadoImp empleado) {
		this.empleado = empleado;
	}
}
