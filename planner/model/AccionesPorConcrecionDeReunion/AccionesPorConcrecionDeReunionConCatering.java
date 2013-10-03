package model.AccionesPorConcrecionDeReunion;

import model.evento.Reunion;
import model.mailService.MailSenderCatering;

public class AccionesPorConcrecionDeReunionConCatering extends AccionesPorConcrecionDeReunionAbstract{


	@Override
	public void ejecutarAccion(Reunion reunionConcretada) {
		super.informar(reunionConcretada, new MailSenderCatering());
	}

}
