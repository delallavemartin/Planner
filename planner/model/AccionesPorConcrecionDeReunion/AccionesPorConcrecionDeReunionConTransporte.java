package model.AccionesPorConcrecionDeReunion;

import model.evento.Reunion;
import model.mailService.MailSenderTransporte;

public class AccionesPorConcrecionDeReunionConTransporte extends
		AccionesPorConcrecionDeReunionAbstract {

	@Override
	public void ejecutarAccion(Reunion reunionConcretada) {
		super.informar(reunionConcretada, new MailSenderTransporte());
	}

}
