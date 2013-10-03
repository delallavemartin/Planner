package model.AccionesPorConcrecionDeReunion;

import exception.UserException;
import model.evento.Reunion;

public interface ObservadorDeEstadoConcretadoDeReunion {

	public void seConcretoLaReunion(Reunion unaReunion) throws UserException;
}
