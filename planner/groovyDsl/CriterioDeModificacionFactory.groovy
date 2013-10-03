package groovyDsl

import model.evento.criterioModificacion.PorcentajeAsistenciaCriterio
import model.evento.criterioModificacion.RecursosObligatoriosCriterio
import model.evento.criterioModificacion.RecursosOpcionalesCriterio
import model.evento.criterioModificacion.ReplanificacionCriterio

class CriterioDeModificacionFactory {

	def destinatario
	
		def CriterioDeModificacionFactory(destinatario){
			this.destinatario = destinatario
			}
		
		
		def crearCriterioReplanificarParaMasAdelante(){
			
			this.destinatario.addCriteriosAAplicarAnteUnaBajaDeUnRecurso(new ReplanificacionCriterio())
		}

		def creaCriterioCancelarPorPorcentajeMenorAl(unPorcentaje){
			
			PorcentajeAsistenciaCriterio criterio = new PorcentajeAsistenciaCriterio()
			criterio.setPorcentajePresenciaRequerido(unPorcentaje)			
			this.destinatario.addCriteriosAAplicarAnteUnaBajaDeUnRecurso(criterio)
		}
		
		def creaCriterioCancelarPorBajaDeRecursObligatorio(){
						
			this.destinatario.addCriteriosAAplicarAnteUnaBajaDeUnRecurso(new RecursosObligatoriosCriterio())
			
		}

		def creaCriterioUtilizarElRecursoSustituto(){
			
			this.destinatario.ponerLosCriteriosDeSustitucionComoCriteriosAAplicar()
	
		}

		def creaCriterioMantenerSiEsOpcional(){

			this.destinatario.addCriteriosAAplicarAnteUnaBajaDeUnRecurso(new RecursosOpcionalesCriterio())
		}
	
}
