package groovyDsl

import org.junit.Test;
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


class TestDsl {

	
	static {
		RequerimientoReunion.metaClass {
			testeate = {casoDePrueba->
						casoDePrueba(delegate)
						}
						   }
		 }
	
	
	
	@Test
	void "especificar requerimientos de una reunion con un programador, se espera que sea creada"(){
		
		requerimientos {
			
				1.integrante obligatorio con estas caracteristicas {[
																	rol:"programador",
																	proyecto:"unProyecto"
																	]}
													
			}
		
		.testeate {
					RequerimientoReunion requerimiento = it
					
					Assert.assertEquals(1, requerimiento.recursosObligatorios.size())
					
					def perfilDelRequerimiento = requerimiento.recursosObligatorios.getAt(0)
					def rol = perfilDelRequerimiento.getProperties().rol
					def proyecto = perfilDelRequerimiento.getProperties().proyecto
										
					Assert.assertTrue( model.recurso.Rol.PROGRAMADOR.equals(rol) )
					Assert.assertTrue( proyecto.nombre.equals("unProyecto") )
					
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
		.testeate {
			RequerimientoReunion requerimiento = it

			//TODO hay codigo repetido , se puede mejorar
			Assert.assertEquals(3, requerimiento.recursosObligatorios.size())
			
			def perfilDeUnProgramador = requerimiento.recursosObligatorios.getAt(0)
			def rol = perfilDeUnProgramador.getProperties().rol
			def proyecto = perfilDeUnProgramador.getProperties().proyecto
											
			Assert.assertTrue( model.recurso.Rol.PROGRAMADOR.equals(rol) )
			Assert.assertTrue( proyecto.nombre.equals("unProyecto") )
			
			def perfilDeUnArquitecto = requerimiento.recursosObligatorios.getAt(1)
			def rol2 = perfilDeUnArquitecto.getProperties().rol
			def proyecto2 = perfilDeUnArquitecto.getProperties().proyecto
											
			Assert.assertTrue( model.recurso.Rol.ARQUITECTO.equals(rol2) )
			Assert.assertTrue( proyecto2.nombre.equals("unProyecto") )

			
			def perfilDeOtroArquitecto = requerimiento.recursosObligatorios.getAt(2)
			def rol3 = perfilDeOtroArquitecto.getProperties().rol
			def proyecto3 = perfilDeOtroArquitecto.getProperties().proyecto
											
			Assert.assertTrue( model.recurso.Rol.ARQUITECTO.equals(rol3) )
			Assert.assertTrue( proyecto3.nombre.equals("unProyecto") )
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
		.testeate {
			
					RequerimientoReunion requerimiento = it
									
					def perfilTitular = requerimiento.recursosObligatorios.getAt(0)
					def rolDelperfilTitular = perfilTitular.getProperties().rol
										
					Assert.assertTrue( model.recurso.Rol.PROGRAMADOR.equals(rolDelperfilTitular) )

					
					def criterioDeSustitucion = requerimiento.getCriteriosAAplicarAnteUnaBajaDeUnRecurso().getAt(0)
					def perfilOriginalDelCriterio = criterioDeSustitucion.getProperties().perfilOriginal
					def perfilReemplazoDelCriterio = criterioDeSustitucion.getProperties().perfilReemplazo
					
					Assert.assertTrue perfilTitular.equals(perfilOriginalDelCriterio)
					
					
					def rolDelperfilReemplazo = perfilReemplazoDelCriterio.getProperties().rol
					
					Assert.assertTrue( model.recurso.Rol.ARQUITECTO.equals(rolDelperfilReemplazo) )
					
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
													
						}.testeate {
									RequerimientoReunion requerimiento = it
														
									def criteriosAAplicarAnteUnaBajaDeUnRecurso = requerimiento.getCriteriosAAplicarAnteUnaBajaDeUnRecurso()
																												
									Assert.assertEquals(ReplanificacionCriterio.class , criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(0).getClass() )
									Assert.assertEquals(PorcentajeAsistenciaCriterio.class , criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(1).getClass() )
									Assert.assertTrue(criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(1).getPorcentajePresenciaRequerido() == 20)
									Assert.assertEquals(SustitucionPerfilCriterio.class , criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(2).getClass() )
									Assert.assertEquals(RecursosObligatoriosCriterio.class , criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(3).getClass() )
									Assert.assertEquals(RecursosOpcionalesCriterio.class , criteriosAAplicarAnteUnaBajaDeUnRecurso.getAt(4).getClass() )
																												
						
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
