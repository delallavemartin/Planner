package model.recurso;

public interface PerfilRecurso<T extends Recurso> {

	public boolean esCompatibleCon(T unRecurso);
}
