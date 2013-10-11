package model.evento;

import java.util.Set;
import model.recurso.Empleado;
import org.joda.time.Duration;

import exception.UserException;

public class Actividad extends Evento implements Comparable<Evento> {

	private Set<Empleado> empleados;

	public Set<Empleado> getEmpleados() {
		return empleados;
	}
	
	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Actividad() {
		super();
	}

	public void agregaRecurso(Empleado unEmpleado) {// Solo los empleados pueden
													// tener actividades
		estaDisponible(unEmpleado);
		agregarEmpleado(unEmpleado);
	
	}

	public void agregarEmpleado(Empleado unEmpleado) {
		this.empleados.add(unEmpleado);
	}

	public void estaDisponible(Empleado unEmpleado) throws UserException {
		if (!unEmpleado.estasDisponiblePara(this)){
			throw new UserException(
					"El empleado no esta disponible para la actividad");
		}
	}

	@Override
	public Duration tiempoParaTrasladarmeA(Empleado empleadoImp) {
		return new Duration(0);
	}

}