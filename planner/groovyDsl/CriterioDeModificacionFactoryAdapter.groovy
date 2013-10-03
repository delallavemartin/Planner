package groovyDsl

class CriterioDeModificacionFactoryAdapter {

	
	static def para = "para"
	static def adelante ="adelante"
	static def por = "por"
	static def menor = "menor"
	static def de = "de"
	static def opcional = "opcional"
	static def obligatorio = "obligatorio"
	static def sustituto = "sustituto"
	static def si = "si"
	static def el = "el"
	
	def factoryAdaptada

	def CriterioDeModificacionFactoryAdapter(factoryAdaptada){
		this.factoryAdaptada = factoryAdaptada
		}
	
	
	
	
//	replanificar para mas adelante
//	cancelar por porcentaje menor al 20
//	cancelar por baja de recurso obligatorio
//	mantener si es opcional
//	utilizar el recurso sustituto
	
	def replanificar(parametro) {
		if(!para.equals(parametro))
			throw new MissingMethodException()				
		this
		}
	def mas(parametro) {
		if(!adelante.equals(parametro))
			throw new MissingMethodException()
		this.factoryAdaptada.crearCriterioReplanificarParaMasAdelante()
		this
		}
	

	

	def cancelar(parametro) {
		if(!por.equals(parametro))
			throw new MissingMethodException()
		this
		}
	def porcentaje(parametro) {
		if(!menor.equals(parametro))
			throw new MissingMethodException()
		this
		}
	def al(unPorcentaje) {
		
		this.factoryAdaptada.creaCriterioCancelarPorPorcentajeMenorAl unPorcentaje
		this
		}


	
	
	def baja(parametro) {
		if(!de.equals(parametro))
			throw new MissingMethodException()
		this
		}
	

	def utilizar(parametro) {
		if(!el.equals(parametro))
			throw new MissingMethodException()
		this
		}

	
		
	def recurso(parametro) {
		if(obligatorio.equals(parametro)){
			this.factoryAdaptada.creaCriterioCancelarPorBajaDeRecursObligatorio()
			return this
			}
		else  if(sustituto.equals(parametro)){
			this.factoryAdaptada.creaCriterioUtilizarElRecursoSustituto()
			return this
			}
		throw new MissingMethodException()
		}
	

	
	def mantener(parametro) {
		if(!si.equals(parametro))
			throw new MissingMethodException()
		this
		}

	def es(parametro) {
		if(!opcional.equals(parametro))
			throw new MissingMethodException()
		this.factoryAdaptada.creaCriterioMantenerSiEsOpcional()
		this
		}
			

}
