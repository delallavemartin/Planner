package model.priorizacion;

import java.util.Set;

import model.evento.Reunion;
import model.recurso.Recurso;


public interface Priorizacion<T extends Recurso> {

	public Set<T> aplicateSobreLosRecursosParaLaReunion(Set<T> recursos, Reunion reunion);
}
