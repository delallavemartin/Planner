package model;

import java.util.Iterator;

import model.evento.Reunion;
import model.recurso.Recurso;

public class ComparadorDeDisponibilidad {
	public boolean decimeSiEstoyDisponible(Reunion reunionAOrganizar,Recurso recursoReunible) {
		if (reunionAOrganizar.estasUbicadaEnEdificio(recursoReunible.getEdificio())){
			
			boolean flag = false;
			
			Iterator<Reunion> it = recursoReunible.getReuniones().iterator();
			
			while (it.hasNext() && !flag ){
				
				flag = it.next().teSuperponesCon(reunionAOrganizar);
			}
			
			return !flag; 
		}
		
		return false;
	}
}
