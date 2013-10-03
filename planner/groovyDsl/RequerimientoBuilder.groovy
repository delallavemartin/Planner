package groovyDsl

import org.joda.time.DateTime
import org.joda.time.MutableInterval

import exception.UserException
import model.Proyecto
import model.evento.RequerimientoReunion
import model.recurso.PerfilEmpleado
import model.recurso.PerfilHerramienta
import model.recurso.PerfilRecurso
import model.recurso.Sala
import org.codehaus.groovy.GroovyException
import org.codehaus.groovy.runtime.metaclass.MethodSelectionException


class RequerimientoBuilder {

	{

		
		Integer.metaClass{
										
						 integrantes = { condicionDeObligatoriedad->
										  validaCantidadDeIntegrantes(2, delegate)
										  new PerfilBuilder(owner.delegate, delegate ).addObligatoriedad(condicionDeObligatoriedad)
										}
						 integrante = { condicionDeObligatoriedad->
										 validaCantidadDeIntegrantes(1, delegate)
										new PerfilBuilder(owner.delegate , delegate).addObligatoriedad(condicionDeObligatoriedad)
							}
						 
						 herramienta = { condicionDeObligatoriedad->
							  new PerfilBuilder(owner.delegate , delegate).addObligatoriedad(condicionDeObligatoriedad)
//							 this.addPerfil(perfil.build)
					
						   }
						 
						 
						 validaCantidadDeIntegrantes = {cantidadDeIntegrantesMinima, cantidadDeIntegrantes ->
															 if(cantidadDeIntegrantes < cantidadDeIntegrantesMinima){
																 throw new GroovyException("No se pudo crear la reunion, cantidad de integrantes inferior a la minima.")
															 }
														 }
						  
													  
						}
		}

	static def cancelan = "cancelan"
	static def el = "el"
			
	def perfilesOpcionales = []
	def perfilesObligatorios = []
	def criteriosDeSustitucion = new HashSet()

	def criteriosEnOrdenAAplicarPorBajaDeRecurso = new LinkedList()
		
	def losCriteriosDeSustitucionEstanAgregadosComoCriteriosAAplicar = false
	
	def sala = new Sala()
	
	def fechaReunion 
	def horarioDeInicio
	def horarioDeFin 
	
		
	def si(parametro) {
		if(!cancelan.equals(parametro))
			throw new MissingMethodException()
		this
		}

	
	
	
	def participacion(bloqueConDescripcionDeCriterios) {
		
		def factory  = new CriterioDeModificacionFactory(this)
		
		new CriterioDeModificacionFactoryAdapter(factory).with bloqueConDescripcionDeCriterios
				
		this
		}


	
	
	
	
	def static requerimientos(bloque){
		
		
		def requerimientoBuilder = new RequerimientoBuilder()
		requerimientoBuilder.with bloque
		requerimientoBuilder.evaluar()
		

		}
		
	def addPerfilObligatorio(perfil){
		
		this.perfilesObligatorios += perfil
		}

	def addPerfilOpcional(perfil){
		
		this.perfilesOpcionales += perfil
		}

	def evaluar(){
		def requerimientoReunion = this.crearRequerimientoReunion()
	}
	
	def crearRequerimientoReunion(){
				
		RequerimientoReunion requerimiento = new RequerimientoReunion(this.perfilesOpcionales, this.perfilesObligatorios)
		

						
		
		if(!this.losCriteriosDeSustitucionEstanAgregadosComoCriteriosAAplicar 	&&
		   !this.criteriosDeSustitucion.isEmpty()){		   
					this.ponerLosCriteriosDeSustitucionComoCriteriosAAplicar()		
				}
		   
		requerimiento.setCriteriosAAplicarAnteUnaBajaDeUnRecurso(this.criteriosEnOrdenAAplicarPorBajaDeRecurso)

		requerimiento.setFechaTentativa(this.fechaReunion)
		
		
		
		requerimiento.setHorarioTentativo( (this.horarioDeInicio!= null && this.horarioDeFin != null ) ? new MutableInterval(this.horarioDeInicio, this.horarioDeFin) : null)
		requerimiento
	}

	
	def addCriterioDeSustitucionDePerfil(criterio){
		criteriosDeSustitucion += criterio
		}

	
	
	
	def ponerLosCriteriosDeSustitucionComoCriteriosAAplicar(){
		if(this.criteriosDeSustitucion.isEmpty())
			throw new UserException("No se puede poner como criterio a aplicar la sustitucion de recursos, si no fueron especificados perfiles alternativos")

		this.criteriosEnOrdenAAplicarPorBajaDeRecurso += this.criteriosDeSustitucion.asList()
		this.losCriteriosDeSustitucionEstanAgregadosComoCriteriosAAplicar = true
		}
	
	
	def addCriteriosAAplicarAnteUnaBajaDeUnRecurso(criterio){
		this.criteriosEnOrdenAAplicarPorBajaDeRecurso += criterio
		}
	
	
	
	def dentroDe ={
		Integer cantidad = it.substring(0, 1).toInteger()
		String unidad = it.substring(1, it.size())
		DateTime fechaDeHoy = new DateTime()

		
		
		this.fechaReunion
		
		def dia = fechaDeHoy.getDayOfMonth();
		def mes = fechaDeHoy.getMonthOfYear();
		def annio = fechaDeHoy.getYear();
		
		if(unidad.equals("dias")){fechaReunion = fechaDeHoy.plusDays(cantidad) }
		if(unidad.equals("meses")){ fechaReunion = fechaDeHoy.plusMonths(cantidad)}
		if(unidad.equals("años")){ fechaReunion = fechaDeHoy.plusYears(cantidad)}
		
		fechaReunion = fechaDeHoy.plusMonths(cantidad)
		this
	 }
	 
	 def dia ={
		 String diaReunion = it.substring(0, 2)
		 String mesReunion = it.substring(3, 5)
		 String anioReunion = it.substring(6, 9)
		 this.fechaReunion = new DateTime(anioReunion.toInteger(), mesReunion.toInteger(), diaReunion.toInteger(), 0, 0, 0, 0)
		 this
	 }
	 
	 def desde ={
		 String  horaComienzoReunion = it.substring(0, 2)
		 String  minutosComienzoReunion = it.substring(3, 5)
		 this.horarioDeInicio = new DateTime(1, 1, 1, horaComienzoReunion.toInteger(), minutosComienzoReunion.toInteger(), 0, 0)
		 this
	 }
	 
	 def hasta ={
		 String horaFinReunion = it.substring(0, 2)
		 String minutosFinReunion = it.substring(3, 5)
		 this.horarioDeFin  = new DateTime(1, 1, 1, horaFinReunion.toInteger(), minutosFinReunion.toInteger(), 0, 0)
		 this
	 }
 
}
