package model.recurso;

public enum Rol {
					  //requieroCatering
	ARQUITECTO				(false),
	DISENIADOR_DE_SISTEMAS	(false),
	DISENIADOR_GRAFICO		(false),
	GERENTE					(true),
	PROJECT_LEADER			(true),
	PROGRAMADOR				(false);
	
	private boolean requieroCatering;
	Rol(boolean requieroCatering)
	{
		this.requieroCatering = requieroCatering;
	}
	
	public boolean requiereCatering(){		
		return this.requieroCatering;
	}

}
