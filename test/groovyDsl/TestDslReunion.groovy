package groovyDsl

import javax.management.relation.Role

import model.evento.criterioModificacion.ReplanificacionCriterio
import model.evento.criterioModificacion.RecursosOpcionalesCriterio
import model.evento.criterioModificacion.RecursosObligatoriosCriterio
import model.evento.criterioModificacion.SustitucionPerfilCriterio
import model.evento.criterioModificacion.PorcentajeAsistenciaCriterio


import model.evento.RequerimientoReunion
import model.recurso.PerfilRecurso

import org.codehaus.groovy.GroovyException
import org.codehaus.groovy.runtime.metaclass.MethodSelectionException
import org.junit.Assert
import org.junit.Test


import static RequerimientoBuilder.requerimientos

import static PerfilBuilder.rol
import static PerfilBuilder.proyecto
import static PerfilBuilder.obligatorio
import static PerfilBuilder.opcional
import static PerfilBuilder.estas
import static PerfilBuilder.sino
import static RequerimientoBuilder.cancelan


class TestDslReunion {
	
	@Test
	void "especificar requerimientos de una reunion con un programador, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"  
																	]}
													
			}
		
	}
	
	@Test(expected=GroovyException)
	void "especificar requerimientos de una reunion con un programador, se espera que arroje excepcion"(){
		
		requerimientos {
			
				0.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
													
			}
		
	}

	@Test
	void "especificar requerimientos de una reunion con un programador  y 2 arquitecto, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
												
				2.integrantes obligatorio con estas caracteristicas {[
																	rol:"arquitecto",
																	proyecto:"unProyecto"
																	]}
							
			}
		
	}
	
	@Test
	void "especificar requerimientos de una reunion con un programador, 2 arquitecto y 1 canion, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
												
				2.integrantes obligatorio con estas caracteristicas {[
																	rol:"arquitecto",
																	proyecto:"unProyecto"
																	]}
				1.herramienta obligatorio con estas caracteristicas {[herramienta:"canion"]}
							
			}
		
	}

	
	@Test(expected=GroovyException)
	void "especificar requerimientos de una reunion con un programador  y 2 arquitecto, se espera que arroje excepcion"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
												
				1.integrantes obligatorio con estas caracteristicas {[
																	rol:"arquitecto",
																	proyecto:"unProyecto"
																	]}
							
			}
		
	}
	
	@Test
	void "especificar requerimientos de una reunion con un programador o un arquitecto, se espera que sea creada"(){
		
		requerimientos {
															
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador"
																	]} 
							  o sino con estas caracteristicas {[
																rol:"arquitecto"
																]}
							
			}
		
	}

	@Test
	void "especificar requerimientos de una reunion con un integrante y acciones definidas en caso de baja de un integrante, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
													rol:"programador",
													proyecto:"unProyecto"
													]}
				
				si cancelan participacion { replanificar para mas adelante
											cancelar por porcentaje menor al 20
											cancelar por baja de recurso obligatorio
											mantener si es opcional
											}
													
						}

	}

	@Test
	void "especificar requerimientos de una reunion con un programador o un arquitecto y acciones definidas en caso de baja de un integrante, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
													rol:"programador",
													]}

					o sino con estas caracteristicas {[
													rol:"arquitecto"
													]}
				si cancelan participacion { replanificar para mas adelante
											cancelar por porcentaje menor al 20
											utilizar el recurso sustituto
											cancelar por baja de recurso obligatorio
											mantener si es opcional
											}

		}
	}

	@Test
	void "especificar requerimientos de una reunion con un programador, 2 arquitecto, 1 canion y fecha se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
												
				2.integrantes obligatorio con estas caracteristicas {[
																	rol:"arquitecto",
																	proyecto:"unProyecto"
																	]}
				1.herramienta obligatorio con estas caracteristicas {[herramienta:"canion"]}
				
				
				dia "30/07/2012" desde "16:00" hasta "17:00"
							
			}
		
	
	}
	//Tests Obligatorios
	@Test
	void "crear reunion semestral con PMs de varios proyectos , 5 personas de Marketing y 2 Gerentes, y si asisten menos del 70% se cancela, se espera que sea creada"(){
		
		requerimientos {
				1.integrante obligatorio con estas caracteristicas 	{[rol:"project_leader",proyecto:"Mobileblaime"]}
				1.integrante obligatorio con estas caracteristicas {[rol:"project_leader",proyecto:"Zarlanga Object Manager Abstract System"]}
				1.integrante obligatorio con estas caracteristicas {[rol:"project_leader",proyecto:"Automatic Losing Reference Counter Garbage Collector"]}
				5.integrantes obligatorio con estas caracteristicas {[sector:"marketing"]}
				2.integrantes obligatorio con estas caracteristicas {[rol:"gerente"]}
												
				dentroDe "6meses" desde "16:00" hasta "17:00"
				si cancelan participacion { cancelar por porcentaje menor al 70	}
							
			}
	
	}
	@Test
	void "especificar requerimientos de una reunion pedidos en el segundo caso de prueba del enunciado, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
													rol:"project_leader",
													proyecto:"Mobilablame"
													]}
								o sino con estas caracteristicas{[
									rol:"arquitecto",
									proyecto:"Mobilablame"
									]}
								
				1.integrante obligatorio con estas caracteristicas {[
													rol:"gerente",
													proyecto:"Mobilablame"
													]}
						
				
				2.integrantes opcional con estas caracteristicas {[
													rol:"diseniador_grafico"
													]}
				
				1.herramienta opcional con estas caracteristicas {[
													herramienta:"canion"]}
				
				
				1.herramienta opcional con estas caracteristicas {[
													herramienta:"laptop"]}

				si cancelan participacion { cancelar por baja de recurso obligatorio}
				
				
				dia "30/07/2012" desde "12:00" hasta "14:00"
													
			}

	}
	
	@Test
	void "especificar requerimientos de una reunion pedidos en el tercer caso de prueba del enunciado, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
													rol:"project_leader"
													]}
				
				1.integrante obligatorio con estas caracteristicas {[
													rol:"diseniador_de_sistemas",
													proyecto:"Mobilablame"
													]}
				
				3.integrantes obligatorio con estas caracteristicas {[
													rol:"programador",
													proyecto:"Mobilablame"
													]}

				
				si cancelan participacion { replanificar para mas adelante}
				
				
				dia "28/07/2012" desde "16:00" hasta "17:00"
													
				}

	}
	
}
