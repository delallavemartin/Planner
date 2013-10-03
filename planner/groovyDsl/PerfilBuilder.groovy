package groovyDsl

import java.util.HashMap;

import model.Proyecto
import model.recurso.PerfilEmpleado
import model.recurso.PerfilHerramienta
import model.recurso.PerfilRecurso;

import model.evento.criterioModificacion.SustitucionPerfilCriterio;



class PerfilBuilder {
	
	
	
	static def sino = "sino"
	static def estas = "estas"
	static def obligatorio = "obligatorio"
	static def opcional = "opcional"

	static def rol="rol"
	static def proyecto="proyecto"

	def opcionesDeCaracteristicasDePerfiles = []
	
	def condicionDeObligatoriedad
		
	def destinatarioDePerfil
	
	def cantidadDePerfilesACrear
	
	def ultimosPerfilesCreados 
	
	def yaSeAgregaronPerfilesAlDestinatario = false
	
	def PerfilBuilder(destinatarioDePerfil, cantidadDePerfilesACrear){
		this.destinatarioDePerfil = destinatarioDePerfil
		this.cantidadDePerfilesACrear = cantidadDePerfilesACrear
		}
	

	def methodMissing(String name, args){
		if(!["si", "participacion", "dia", "dentroDe"].contains(name) ){
			throw new MissingMethodException()
			} 
		this.destinatarioDePerfil.invokeMethod(name, args)
		}
	
	//TODO hacer con el methodMissing()
	def con(parametro) {
		if(!estas.equals(parametro))
			throw new MissingMethodException()
		this
		}
	
	def o ={parametro ->
		if(!sino.equals(parametro))
			throw new MissingMethodException()
		
		this.opcionesDeCaracteristicasDePerfiles = []
		this
	}
	
		
	def caracteristicas ={bloque->
				
		addOpcionDeCaracteristicasDePerfil(bloque())		
				
		def perfilesCreados = this.build()
		
		if(yaSeAgregaronPerfilesAlDestinatario){	

			agregaCriterioDeSustitucionDePerfilADestinatario(this.ultimosPerfilesCreados , perfilesCreados)
			
		}else{
		
			agregaPerfilesAlDestinatario(perfilesCreados)
		}
	
		
		this.ultimosPerfilesCreados = perfilesCreados
				
		bloque.delegate.delegate = this
		
		this
		}

	
	def agregaCriterioDeSustitucionDePerfilADestinatario(perfilesOriginales , perfilesSustitutos){
		
		SustitucionPerfilCriterio criterio = new SustitucionPerfilCriterio()
		criterio.setPerfilOriginal(perfilesOriginales.getAt(0))
		criterio.setPerfilReemplazo(perfilesSustitutos.getAt(0))
		
		this.destinatarioDePerfil.addCriterioDeSustitucionDePerfil(criterio)
		}

	
	def agregaPerfilesAlDestinatario(perfilesCreados) {
		if(obligatorio.equals(this.condicionDeObligatoriedad) ){
			this.destinatarioDePerfil.addPerfilObligatorio(perfilesCreados)
		}
		else{
			this.destinatarioDePerfil.addPerfilOpcional(perfilesCreados)
		}
		yaSeAgregaronPerfilesAlDestinatario = true
	}
	
			
	
	def build(){
		
		
		def perfiles = this.opcionesDeCaracteristicasDePerfiles.collect { mapa -> transformarElMapaEnUnPerfil(mapa)}
		
		for(int i=1 ; i< this.cantidadDePerfilesACrear ; i++){		//se puede mejorar
			perfiles += perfiles.getAt(0)
		}
		perfiles 
		}
	
	def transformarElMapaEnUnPerfil(HashMap mapa) {
		if (!mapa.get("herramienta").is(null)){
			def perfilHerramienta = new PerfilHerramienta()
			perfilHerramienta.nombre = mapa.get("herramienta")
			perfilHerramienta
		}else {
			def perfil = new PerfilEmpleado()
			if (!mapa.get("rol").is(null)){
				perfil.rol = mapa.get("rol").toUpperCase()
			}
			perfil.proyecto = this.crearProyecto(mapa)
			perfil.nombre = mapa.get("nombre")
			perfil.sector = mapa.get("sector")
			perfil
		}
		
	}
	
	def crearProyecto(HashMap mapa){
		//TODO analizar si faltan cosas, o si hay q preguntar si ya existe.
		def proyecto = new Proyecto()
		proyecto.nombre = mapa.get("proyecto")
		proyecto
	}
	
	def addObligatoriedad(condicionDeObligatoriedad){
		this.condicionDeObligatoriedad = condicionDeObligatoriedad			//TODO agregar obligatorio/opcional
		this
		}
	
	

	
	def addOpcionDeCaracteristicasDePerfil(mapaDeCaracteristicas){
		this.opcionesDeCaracteristicasDePerfiles << mapaDeCaracteristicas
		}
	
}
