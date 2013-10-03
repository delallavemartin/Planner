package model.AccionesPorConcrecionDeReunion;

import model.evento.Reunion;
import model.mailService.MailSenderPM;
import model.recurso.EmpleadoImp;

public class AccionesPorConcrecionDeReunionConPersonaClave extends
		AccionesPorConcrecionDeReunionAbstract {

	private EmpleadoImp empleado;

	@Override
	public void ejecutarAccion(Reunion reunionConcretada) {
		if (this.getEmpleado().getProyecto().soyPersonaClave(this.getEmpleado())) {
			for (EmpleadoImp pm : this.getEmpleado().getProyecto().getProjectManagers()) {
				super.informar(reunionConcretada,
						new MailSenderPM(pm, this.getEmpleado()));

			}
		}
	}

	// SETTERS Y GETTERS
	public EmpleadoImp getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadoImp empleado) {
		this.empleado = empleado;
	}
}
