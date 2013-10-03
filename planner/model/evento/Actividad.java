package model.evento;

import java.util.Set;

import model.Edificio;
import model.recurso.EmpleadoImp;
import model.recurso.Recurso;
import model.recurso.Empleado;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableInterval;

import exception.UserException;

public class Actividad extends Evento implements Comparable<Evento> {

	private Set<Empleado> empleados;

	public Actividad() {
		super();
	}

	public void agregaRecurso(Empleado unEmpleado) {// Solo los empleados pueden
													// tener actividades
		if (!unEmpleado.estasDisponiblePara(this)) {
			throw new UserException(
					"El empleado no esta disponible para la actividad");
		}
		if (!this.empleados.add(unEmpleado)) {
			throw new UserException(
					"El empleado ya esta agendad oen la actividad");
		}
	}

	@Override
	public Duration tiempoParaTrasladarmeA(Empleado empleadoImp) {
		return new Duration(0);
	}

}