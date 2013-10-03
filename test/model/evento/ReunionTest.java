package model.evento;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import mailService.Mail;
import mailService.MailServiceImpl;
import mailService.SimpleMail;
import model.Edificio;
import model.Empresa;
import model.Proyecto;
import model.AccionesPorConcrecionDeReunion.ObservadorDeEstadoConcretadoDeReunion;
import model.evento.Concretado;
import model.evento.Finalizado;
import model.evento.Planificado;
import model.evento.Reunion;
import model.mailService.MailSender;
import model.mailService.MailSenderCatering;
import model.mailService.MailSenderTransporte;
import model.recurso.Empleado;
import model.recurso.EmpleadoImp;
import model.recurso.Herramienta;
import model.recurso.Organizador;
import model.recurso.Rol;
import model.recurso.Sala;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableInterval;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exception.UserException;

public class ReunionTest {

	private Reunion unaReunion;
	private Set<ObservadorDeEstadoConcretadoDeReunion> observers = new HashSet<ObservadorDeEstadoConcretadoDeReunion>();
	private Organizador elOrganizador;
	
	private Empleado empleado;
	private Herramienta canion;
	private Herramienta laptop;
	private Sala sala;
	
	private Reunion nuevaReunion(){
		Reunion nuevaReunion = new Reunion();  
		
		Mockito.when(this.elOrganizador.estasDisponiblePara(nuevaReunion)).thenReturn(true);
		nuevaReunion.agregaRecurso(elOrganizador);
		nuevaReunion.setOrganizador(elOrganizador);
		return nuevaReunion;
	}
	
	@Before
	public void setUp() throws Exception {
		// Especificar indicando a–o, mes, d’a, horas, minutos, segundos y milisegundos
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 30, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		this.elOrganizador = Mockito.mock(Organizador.class);		
		this.empleado = Mockito.mock(Empleado.class);
		this.laptop = Mockito.mock(Herramienta.class);
		this.canion = Mockito.mock(Herramienta.class);
		this.sala = Mockito.mock(Sala.class);
		
		this.unaReunion = this.nuevaReunion();
		this.unaReunion.setHorario(horario);
		this.unaReunion.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		
		Mockito.when(this.sala.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		this.unaReunion.setSala(this.sala);
		this.unaReunion.setEstado(Concretado.getInstance());
		
	}
	
	// SUPERPOSICION DE HORARIOS
	
	@Test
	public void testTeSuperponesConSinMargen() {
		
		
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 20, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		
//		Reunion reunionAOrganizar = new Reunion(elOrganizador);
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(0);
		
		assertTrue(this.unaReunion.teSuperponesCon(reunionAOrganizar, margen));
	}
	
	@Test
	public void testNoTeSuperponesConSinMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(0);
		
		assertFalse(this.unaReunion.teSuperponesCon(reunionAOrganizar, margen));
	}
	
	
	@Test
	public void testTeSuperponesConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(60000);
		
		assertTrue(this.unaReunion.teSuperponesCon(reunionAOrganizar, margen));
	}
	
	@Test
	public void testNoTeSuperponesConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(60000);
		
		assertFalse(this.unaReunion.teSuperponesCon(reunionAOrganizar, margen));
	}

	@Test
	public void testSeSuperponeConHorarioYFechaDeSinMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 20, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(0);
		
		assertTrue(this.unaReunion.seSuperponeConHorarioYFechaDe(reunionAOrganizar, margen));
	}
	
	@Test
	public void testNoSeSuperponeConHorarioYFechaDeSinMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(0);
		
		assertFalse(this.unaReunion.seSuperponeConHorarioYFechaDe(reunionAOrganizar, margen));
	}
	
	@Test
	public void testSeSuperponeConHorarioYFechaDeConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(6000);
		
		assertTrue(this.unaReunion.seSuperponeConHorarioYFechaDe(reunionAOrganizar, margen));
	}
	
	@Test
	public void testNoSeSuperponeConHorarioYFechaDeConMargen() {
		
		DateTime inicio = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 11, 30, 0, 0);
		MutableInterval horario = new MutableInterval(inicio, fin);
		Reunion reunionAOrganizar = this.nuevaReunion();
		reunionAOrganizar.setHorario(horario);
		reunionAOrganizar.setFecha(new DateTime(2012, 1, 1, 0, 0, 0, 0));
		Duration margen = new Duration(6000);
		
		assertFalse(this.unaReunion.seSuperponeConHorarioYFechaDe(reunionAOrganizar, margen));
	}
	
	@Test
	public void testCoincidenHorariosSinMargen() 
	{
		
		DateTime inicio = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 30, 0, 0);
		MutableInterval horarioAConcretar = new MutableInterval(inicio, fin);
		Duration margen = new Duration(0);
		
		assertTrue(this.unaReunion.coincidenHorarios(horarioAConcretar, margen));
	}
	
	@Test
	public void testNoCoincidenHorariosSinMargen() 
	{
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horarioAConcretar = new MutableInterval(inicio, fin);
		Duration margen = new Duration(0);
		assertFalse(this.unaReunion.coincidenHorarios(horarioAConcretar, margen));
	}
	@Test
	public void testCoincidenHorariosConMargen() 
	{
		
		DateTime inicio = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 13, 0, 0, 0);
		MutableInterval horarioAConcretar = new MutableInterval(inicio, fin);
		Duration margen = new Duration(60000);
		
		assertTrue(this.unaReunion.coincidenHorarios(horarioAConcretar, margen));
	}
	
	@Test
	public void testNoCoincidenHorariosConMargen() 
	{
		
		DateTime inicio = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		MutableInterval horarioAConcretar = new MutableInterval(inicio, fin);
		Duration margen = new Duration(1800000);
		assertFalse(this.unaReunion.coincidenHorarios(horarioAConcretar, margen));
	}
	
	
	
	@Test
	public void testInstanciarUnaReunionSeEsperaQueTengaUnEmpleadoQueSeaOrganizador(){	
		
		//Reunion reunion = new Reunion(Mockito.mock(Organizador.class));
		Reunion reunion = this.nuevaReunion();
		assertNotNull(reunion.getOrganizador());

	}
	
	//AGREGAR Recursos (ObjetoReunible)
	
	@Test
	public void testAgregarPersonaConDisponibilidadAReunionConcretadaSeEsperaQueSeaAgregada() {
		this.unaReunion.setEstado(Concretado.getInstance());		
		Empleado empleadoConDisponibilidad = Mockito.mock(Empleado.class);		
		Mockito.doReturn(true).when(empleadoConDisponibilidad).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock		
		this.unaReunion.agregaRecurso(empleadoConDisponibilidad);		
		assertTrue(unaReunion.contains(empleadoConDisponibilidad));					
	}
	@Test
	public void testAgregarPersonaSinDisponibilidadAReunionConcretadaSeEsperaQueNoSeaAgregadaYArrojeException() {
				 		
		this.unaReunion.setEstado(Concretado.getInstance());
		Empleado empleadoSinDisponibilidad = Mockito.mock(Empleado.class);		
		Mockito.doReturn(false).when(empleadoSinDisponibilidad).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock

		try{
			this.unaReunion.agregaRecurso(empleadoSinDisponibilidad);
			fail();
		}
		catch(Exception e){
			assertEquals(UserException.class, e.getClass()); 			
			assertFalse(this.unaReunion.contains(empleadoSinDisponibilidad)); //assertion
		}
	}

	@Test
	public void testAgregar2PersonaDistintasDisponibilesAReunionConcretadaSeEsperaQueSeanAgregadasSoloEsas2()
	{
		this.unaReunion.setEstado(Concretado.getInstance());
		
		Empleado empleadoDisponible1 = Mockito.mock(Empleado.class);
		Mockito.doReturn(true).when(empleadoDisponible1).estasDisponiblePara(this.unaReunion);
		Empleado empleadoDisponible2 = Mockito.mock(Empleado.class);
		Mockito.doReturn(true).when(empleadoDisponible2).estasDisponiblePara(this.unaReunion);
		
		int cantidadDeIntegrantesInicial = this.unaReunion.cantidadDeRecursos(); 
		
		this.unaReunion.agregaRecurso(empleadoDisponible1);
		this.unaReunion.agregaRecurso(empleadoDisponible2);
		
		Mockito.verify(empleadoDisponible1).estasDisponiblePara(this.unaReunion);
		Mockito.verify(empleadoDisponible2).estasDisponiblePara(this.unaReunion);
		
		
		assertTrue("No contiene al primer empleado agregado", this.unaReunion.contains(empleadoDisponible1));
		assertTrue("No contiene al segundo empleado agregado", this.unaReunion.contains(empleadoDisponible2));		
		assertEquals("Hay distinta cantidad de empleados de los que se agregaron", cantidadDeIntegrantesInicial+2, this.unaReunion.cantidadDeRecursos());
	}

	@Test
	public void testAgregar2VecesAUnaPersonaDisponibileAReunionConcretadaSeEsperaQueSeanAgregadasSolo1VezYLaSegundaArrojeExcepcion()
	{
		this.unaReunion.setEstado(Concretado.getInstance());
		Empleado empleadoDisponible = Mockito.mock(Empleado.class);
		Mockito.doReturn(true).when(empleadoDisponible).estasDisponiblePara(this.unaReunion);

		int cantidadDeIntegrantesInicial = this.unaReunion.cantidadDeRecursos();
	
		this.unaReunion.agregaRecurso(empleadoDisponible);
		try{
			this.unaReunion.agregaRecurso(empleadoDisponible);
			fail();
		}catch(Exception e){
			assertEquals(UserException.class, e.getClass());
			
			Mockito.verify(empleadoDisponible, Mockito.atLeast(1)).estasDisponiblePara(this.unaReunion);
			assertTrue("No contiene al empleado agregado", this.unaReunion.contains(empleadoDisponible));		
			assertEquals("Hay distinta cantidad de empleados de los que se agregaron", cantidadDeIntegrantesInicial+1, this.unaReunion.cantidadDeRecursos());
		}
	}
			
	@Test
	public void testAgregarHerramientaDisponibleAReunionConcretadaSeEsperaQueSeaAgregada() {
	
		this.unaReunion.setEstado(Concretado.getInstance());
		
		Herramienta herramientaDisponible = Mockito.mock(Herramienta.class);		
		Mockito.doReturn(true).when(herramientaDisponible).estasDisponiblePara(this.unaReunion);	
		
		this.unaReunion.agregaRecurso(herramientaDisponible);
		
		assertTrue(unaReunion.contains(herramientaDisponible));					
	}
	@Test(expected = UserException.class)
	public void testAgregarHerramientaSinDisponibilidadAReunionConcretadaSeEsperaQueNoSeaAgregadaYArrojeException() {
		
		this.unaReunion.setEstado(Concretado.getInstance());
		Herramienta herramientaSinDisponibilidad = Mockito.mock(Herramienta.class);		
		Mockito.doReturn(false).when(herramientaSinDisponibilidad).estasDisponiblePara(this.unaReunion);	
		this.unaReunion.agregaRecurso(herramientaSinDisponibilidad);
	}
	@Test
	public void testAgregarHerramientaAReunionPlanificadaSeEsperaQueSeaAgregadaSinImportarDisponibilidad() {
		this.unaReunion.setEstado(Planificado.getInstance());
		Herramienta herramientaDisponible = Mockito.mock(Herramienta.class);		
		Mockito.when(herramientaDisponible.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		this.unaReunion.agregaRecurso(herramientaDisponible);
		assertTrue(unaReunion.contains(herramientaDisponible));		
	}

	@Test
	public void testAgregarPersonaAReunionPlanificadaSeEsperaQueSeaAgregadaSinImportarDisponibilidad() {
		this.unaReunion.setEstado(Planificado.getInstance());		
		Empleado empleadoConDisponibilidad = Mockito.mock(Empleado.class);	
		Mockito.when(empleadoConDisponibilidad.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		
		this.unaReunion.agregaRecurso(empleadoConDisponibilidad);		
		
		//Mockito.verify(empleadoConDisponibilidad, Mockito.never()).estasDisponiblePara(this.unaReunion);
		assertTrue(unaReunion.contains(empleadoConDisponibilidad));					
	}
	
	@Test
	public void testAgregarRecursoAReunionFinalizadaSeEsperaQueNoLoAgregueYArrojeExcepcionSinImportarDisponibilidadDeRecursos()
	{
		this.unaReunion.setEstado(Finalizado.getInstance());		
		Empleado empleado = Mockito.mock(Empleado.class);		
					
		try{
			this.unaReunion.agregaRecurso(empleado);
			fail();
		}
		catch(Exception e){
			assertEquals(UserException.class,  e.getClass());
			
			assertFalse( this.unaReunion.contains(empleado));
//			Mockito.verify(this.elOrganizador, Mockito.never()).estasDisponiblePara(this.unaReunion);
			Mockito.verify(empleado, Mockito.never()).estasDisponiblePara(this.unaReunion);
		}
	}

	
		
// COSTOS
	@Test 
	public void testPedirCateringCuandoSeAgregaUnGerenteOProjectLeader(){
		
		this.unaReunion = this.nuevaReunion();
		Mockito.doReturn(true).when(empleado).requerisCatering();
		this.unaReunion.setCatering(true);
		Mockito.doReturn((double)30).when(elOrganizador).precioDeCatering();
		Mockito.doReturn(true).when(empleado).estasDisponiblePara(unaReunion);
		
		this.unaReunion.agregaRecurso(empleado);
		assertTrue(30.00 == unaReunion.presupuestarCatering());
	}
	
	@Test 
	public void testNoPedirCateringPorEmpleadoNormal(){
		Mockito.doReturn(false).when(empleado).requerisCatering();
		Mockito.doReturn(true).when(empleado).estasDisponiblePara(unaReunion);
		
		this.unaReunion.agregaRecurso(empleado);
		assertTrue(unaReunion.presupuestarCatering() == 0.00);
	}

	@Test
	public void testPresupuestarSinCatering(){
		Mockito.doReturn(50.00).when(empleado).presupuestoPara(unaReunion);
		Mockito.doReturn(true).when(empleado).estasDisponiblePara(unaReunion);		
		Mockito.doReturn(false).when(empleado).requerisCatering();
		Mockito.doReturn(true).when(canion).estasDisponiblePara(unaReunion);	
		Mockito.doReturn(10.00).when(canion).presupuestoPara(unaReunion);		
		Mockito.doReturn(true).when(laptop).estasDisponiblePara(unaReunion);	
		Mockito.doReturn(60.00).when(laptop).presupuestoPara(unaReunion);
		
		
		this.unaReunion.agregaRecurso(empleado);
		this.unaReunion.agregaRecurso(canion);
		this.unaReunion.agregaRecurso(laptop);		
		
		assertTrue(unaReunion.presupuestar() == 120.00);
	}
	
	@Test
	public void testPresupuestarConCatering(){
		Mockito.doReturn(50.00).when(empleado).presupuestoPara(unaReunion);
		Mockito.doReturn(true).when(empleado).estasDisponiblePara(unaReunion);		
		Mockito.doReturn(true).when(empleado).requerisCatering();
		Mockito.doReturn(40.00).when(elOrganizador).precioDeCatering();
		Mockito.doReturn(true).when(canion).estasDisponiblePara(unaReunion);	
		Mockito.doReturn(10.00).when(canion).presupuestoPara(unaReunion);		
		Mockito.doReturn(true).when(laptop).estasDisponiblePara(unaReunion);	
		Mockito.doReturn(60.00).when(laptop).presupuestoPara(unaReunion);
		
		
		this.unaReunion.agregaRecurso(empleado);
		this.unaReunion.agregaRecurso(canion);
		this.unaReunion.agregaRecurso(laptop);		
		
		assertEquals((long)120.00,(long)unaReunion.presupuestar());
	}

	
	
//	@Test
//	public void testConcretarReunionPlanificadaConTodosLosRecursosDisponiblesSeEsperaPaseAEstadoConcretado()
//	{
//		Mockito.doReturn(true).when(sala).estasDisponiblePara(this.unaReunion);	
//		this.unaReunion.setEstado(Planificado.getInstance());		
//		Herramienta herramientaDisponible = Mockito.mock(Herramienta.class);		
//		Mockito.doReturn(true).when(herramientaDisponible).estasDisponiblePara(this.unaReunion);	
//		Empleado empleadoConDisponibilidad = Mockito.mock(Empleado.class);		
//		Mockito.doReturn(true).when(empleadoConDisponibilidad).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock		
//		
//		Mockito.doReturn(true).when(this.elOrganizador).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock		
//		
//		this.unaReunion.agregaRecurso(herramientaDisponible);
//		this.unaReunion.agregaRecurso(empleadoConDisponibilidad);		
//		
//		this.unaReunion.concretar();
//		
//		Mockito.verify(this.elOrganizador).estasDisponiblePara(this.unaReunion);
//		Mockito.verify(herramientaDisponible).estasDisponiblePara(this.unaReunion);
//		Mockito.verify(empleadoConDisponibilidad).estasDisponiblePara(this.unaReunion);		
//		assertEquals(this.unaReunion.getEstado().getClass(), Concretado.class);
//		
//	}

	@Test
	public void testConcretarReunionPlanificadaConRecursoNoDisponibleSeEsperaSigaPlanificadaYArrojeExcepcion()
	{
		this.unaReunion.setEstado(Planificado.getInstance());		
		Herramienta herramientaDisponible = Mockito.mock(Herramienta.class);		
		Mockito.doReturn(true).when(herramientaDisponible).estasDisponiblePara(this.unaReunion);	
		Empleado empleadoSinDisponibilidad = Mockito.mock(Empleado.class);		
		Mockito.doReturn(false).when(empleadoSinDisponibilidad).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock		
		
		Mockito.doReturn(true).when(this.elOrganizador).estasDisponiblePara(this.unaReunion);	// que queremos que retorne el metodo del mock		
		
		this.unaReunion.agregaRecurso(herramientaDisponible);
				
		try{
			this.unaReunion.agregaRecurso(empleadoSinDisponibilidad);
			fail();
		}catch(Exception e)
		{
			assertEquals(UserException.class, e.getClass());
			assertEquals(Planificado.class ,this.unaReunion.getEstado().getClass());
			
		}
	}
	@Test
	public void testConcretarReunionFinalizadaSeEsperaSigaFinalizadaYArrojeExcepcionSinImportarDisponibilidadDeRecursos()
	{

		this.unaReunion.setEstado(Planificado.getInstance());		
		Herramienta herramienta = Mockito.mock(Herramienta.class);		
		Empleado empleado = Mockito.mock(Empleado.class);		
		Mockito.when(empleado.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		Mockito.when(herramienta.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		this.unaReunion.agregaRecurso(herramienta);
		this.unaReunion.agregaRecurso(empleado);
		
		this.unaReunion.setEstado(Finalizado.getInstance());
								
		try{
			this.unaReunion.concretar();
			
			fail();
			
		}catch(Exception e){
			assertEquals(UserException.class, e.getClass());
			assertEquals(this.unaReunion.getEstado().getClass(), Finalizado.class);
//			Mockito.verify(this.elOrganizador, Mockito.never()).estasDisponiblePara(this.unaReunion);
//			Mockito.verify(herramienta, Mockito.never()).estasDisponiblePara(this.unaReunion);
//			Mockito.verify(empleado, Mockito.never()).estasDisponiblePara(this.unaReunion);				
		}
	}
	@Test
	public void testConcretarReunionConcretadaSeEsperaSigaConcretadaYArrojeExcepcionSinImportarDisponibilidadDeRecursos()
	{
		this.unaReunion.setEstado(Planificado.getInstance());		
		Herramienta herramienta = Mockito.mock(Herramienta.class);	
		Mockito.when(herramienta.estasDisponiblePara(this.unaReunion)).thenReturn(true);
		Empleado empleado = Mockito.mock(Empleado.class);
		Mockito.when(empleado.estasDisponiblePara(this.unaReunion)).thenReturn(true);
								
		this.unaReunion.agregaRecurso(herramienta);
		this.unaReunion.agregaRecurso(empleado);	
		this.unaReunion.setEstado(Concretado.getInstance());
		try{
			this.unaReunion.concretar();
			fail();
		}catch(Exception e){
			assertEquals( UserException.class, e.getClass());
			assertEquals(Concretado.class,this.unaReunion.getEstado().getClass());
		}
	}
	
	@Test
	public void testFinalizarReunionConcretadaSeEsperaPaseAEstadoFinalizado()
	{
		this.unaReunion.setEstado(Concretado.getInstance());
		this.unaReunion.finalizar();
		assertEquals(Finalizado.class , this.unaReunion.getEstado().getClass());		
	}	
	
	/*es más simple que las reuniones en estado "planificado"tmb se finalicen tal vez se podria cambiar el nombre para que quede mas claro*/
	@Test(expected=UserException.class)
	public void testFinalizarReunionPlanificadaSeEsperaPaseAEstadoFinalizado() 
	{
		this.unaReunion.setEstado(Planificado.getInstance());
		
		this.unaReunion.finalizar();
		assertEquals( Finalizado.class , this.unaReunion.getEstado().getClass());		
	}	
	@Test
	public void testFinalizarReunionFinalizadaSeEsperaQueSigaFinalizadoYArrojeException() 
	{
		this.unaReunion.setEstado(Finalizado.getInstance());
		try{
			this.unaReunion.finalizar();
			fail();
		}catch(Exception e){
			assertEquals( UserException.class ,e.getClass());
			assertEquals(Finalizado.class ,this.unaReunion.getEstado().getClass());			
		}	
		
	}
	// TEST DE LOS OBSERVERS DE LA REUNION
	@Test
	public void testAgregarReunionComoObserver(){
		
		unaReunion.setObservadorDeReunion(observers);
		unaReunion.agregarObserver(unaReunion);
		
		assertTrue(unaReunion.getObservadorDeReunion().contains(unaReunion));	
		
	}
	
	@Test
	public void testAgregarRecursosComoObserver(){

		EmpleadoImp empleadoImpl1 = Mockito.mock(EmpleadoImp.class);
		EmpleadoImp empleadoImpl2 = Mockito.mock(EmpleadoImp.class);
		
		unaReunion.setObservadorDeReunion(observers);
		unaReunion.agregarObserver(empleadoImpl1);
		unaReunion.agregarObserver(empleadoImpl2);
		
		assertTrue(unaReunion.getObservadorDeReunion().contains(empleadoImpl1));
		assertTrue(unaReunion.getObservadorDeReunion().contains(empleadoImpl2));
		
	}

	@Test
	public void testNecesitasTransporte(){
		
		Edificio edificio = Mockito.mock(Edificio.class);

		Mockito.when(this.sala.getEdificio()).thenReturn(edificio);
		Mockito.when(this.unaReunion.estasUbicadaEnEdificio(edificio)).thenReturn(true);
		
		assertTrue(this.unaReunion.necesitasTransporte());
		
	}
	
	@Test
	public void testNoNecesitasTransporte(){
		
		Edificio edificio = Mockito.mock(Edificio.class);

		Mockito.when(this.sala.getEdificio()).thenReturn(edificio);
		Mockito.when(this.unaReunion.estasUbicadaEnEdificio(edificio)).thenReturn(false);
		
		assertFalse(this.unaReunion.necesitasTransporte());
		
	}
	//TODO pasar los dos tests de abajo a una nueva clase test
//	@Test(expected=UserException.class)
//	public void testInformarAlCateringConExcepcion(){
//		
//		MailSender mailSender = Mockito.mock(MailSenderCatering.class);
//
//		Mockito.doThrow(UserException.class).when(mailSender).enviarMail(unaReunion);
//		
//		unaReunion.informar(unaReunion, mailSender);
//		
//	}
//	
//	@Test(expected=UserException.class)
//	public void testInformarAlTransporteConExcepcion(){
//		
//		MailSender mailSender = Mockito.mock(MailSenderTransporte.class);
//		
//		Mockito.doThrow(UserException.class).when(mailSender).enviarMail(unaReunion);
//		
//		unaReunion.informar(unaReunion, mailSender);
//		
//	}
	
//	@Test(expected=UserException.class)
//	public void testUpdateConExcepcionPorElCatering(){
//		
//		Edificio edificio = Mockito.mock(Edificio.class);
//		
//		MailSender mailSender = Mockito.mock(MailSenderCatering.class);
//		
//		unaReunion.setCatering(true);
//		Mockito.when(this.unaReunion.estasUbicadaEnEdificio(edificio)).thenReturn(false);
//		Mockito.doThrow(UserException.class).when(mailSender).enviarMail(unaReunion);
//		
//		unaReunion.update(unaReunion);
//		
//	}
	
	
	//TODO faltan tests para toda la funcionalidad de obsevers

	
//	@Test
//	public void testFinalizarReunion() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testCancelarReunion() {
//		fail("Not yet implemented");
//	}
	
//Tests obligatorios
//	@Test
//	public void testReservarSalaParaTodosLosIntegrantesConExito() {
//	// SETUP
//	sala = new Sala();
//	
//	Edificio edificio = new Edificio();
//	edificio.agregarSala(sala);
//	
//	Empresa empresa = new Empresa();
//	empresa.agregarEdificio(edificio);
//	
//	elOrganizador = new Organizador(new EmpleadoImp());
//	elOrganizador.setEmpresa(empresa);
//	elOrganizador.setEdificio(edificio);
//	elOrganizador.setRol(Rol.PROJECT_LEADER);
//	Empleado arquitecto = new EmpleadoImp();
//	arquitecto.setRol(Rol.ARQUITECTO);
//	Empleado programador = new EmpleadoImp();
//	programador.setRol(Rol.PROGRAMADOR);
//	
//	unaReunion = this.nuevaReunion();
//	unaReunion.setOrganizador(elOrganizador);
//	
//	unaReunion.agregaRecurso(arquitecto);
//	unaReunion.agregaRecurso(programador);	
//	unaReunion.agregaRecurso(sala);
//	unaReunion.setEstado(Concretado.getInstance());
//
//	assertTrue(sala.getReuniones().contains(unaReunion));
//		
//	}
	
//	@Test
//	public void testSeCreaReunionConArquitectoYSeVerificaQueSuTiempoEstaOcupado(){
//		Empresa empresa = new Empresa();
//		Organizador organizadorDeReunionAPlanificar = new Organizador(new EmpleadoImp());
//		Organizador organizadorDePreviaDelArquitecto = new Organizador(new EmpleadoImp());
//		Edificio edificio = new Edificio();
//		Proyecto proyecto = new Proyecto();
//		
//		
//		//Seteo de RecursosReunibles
//		DateTime inicio = new DateTime(2012, 1, 1, 9, 0, 0, 0);
//		DateTime fin = new DateTime(2012, 1, 1, 17, 0, 0, 0);
//		MutableInterval horarioDeTrabajo = new MutableInterval(inicio, fin);
//		
//		organizadorDeReunionAPlanificar.setEdificio(edificio);
//		organizadorDeReunionAPlanificar.setProyecto(proyecto);
//		organizadorDeReunionAPlanificar.setRol(Rol.DISENIADOR_DE_SISTEMAS);
//		organizadorDeReunionAPlanificar.setEmpresa(empresa);
//		organizadorDeReunionAPlanificar.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		organizadorDePreviaDelArquitecto.setEdificio(edificio);
//		organizadorDePreviaDelArquitecto.setProyecto(proyecto);
//		organizadorDePreviaDelArquitecto.setRol(Rol.DISENIADOR_DE_SISTEMAS);	
//		organizadorDePreviaDelArquitecto.setEmpresa(empresa);
//		organizadorDePreviaDelArquitecto.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		Sala sala = new Sala();
//		sala.setEdificio(edificio);
//		Empleado arquitecto = new EmpleadoImp();
//		arquitecto.setProyecto(proyecto);
//		arquitecto.setRol(Rol.ARQUITECTO);
//		arquitecto.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		//Seteo empresa
//		empresa.agregarEdificio(edificio);
//		empresa.agregarEmpleado(organizadorDeReunionAPlanificar);
//		empresa.agregarEmpleado(organizadorDePreviaDelArquitecto);
//		empresa.agregarEmpleado(arquitecto);
//		
//		//Seteo de Horarios
//		DateTime inicioDeReunionAPlanificar = new DateTime(2012, 1, 1, 10, 0, 0, 0);
//		DateTime finDeReunionAPlanificar = new DateTime(2012, 1, 1, 13, 30, 0, 0);
//		MutableInterval horarioDeReunionAPlanificar = new MutableInterval(inicioDeReunionAPlanificar, finDeReunionAPlanificar);
//		DateTime fechaDeReunionAPlanificar = new DateTime(2012, 1, 1, 0, 0, 0, 0);
//		
//		DateTime inicioDePreviaDelArquitecto = new DateTime(2012, 1, 1, 10, 0, 0, 0);
//		DateTime finDePreviaDelArquitecto = new DateTime(2012, 1, 1, 11, 15, 0, 0);
//		MutableInterval horarioDePreviaDelArquitecto = new MutableInterval(inicioDePreviaDelArquitecto, finDePreviaDelArquitecto);
//		DateTime fechaDePreviaDelArquitecto = new DateTime(2012, 1, 1, 0, 0, 0, 0);
//		
//		//Seteo de Reunion A Planificar
//		Reunion reunionAPlanificar = new Reunion(organizadorDeReunionAPlanificar);
//		reunionAPlanificar.setEstado(Planificado.getInstance());
//		reunionAPlanificar.setHorario(horarioDeReunionAPlanificar);
//		reunionAPlanificar.setFecha(fechaDeReunionAPlanificar);
//		reunionAPlanificar.setSala(sala);
//		
//		//Seteo de Reunion Previa del Arquitecto
//		Reunion reunionPreviaDelArquitecto = new Reunion(organizadorDePreviaDelArquitecto);
//		reunionPreviaDelArquitecto.setEstado(Planificado.getInstance());
//		reunionPreviaDelArquitecto.setHorario(horarioDePreviaDelArquitecto);
//		reunionPreviaDelArquitecto.setFecha(fechaDePreviaDelArquitecto);
//		reunionPreviaDelArquitecto.setEstado(Concretado.getInstance());
//		reunionPreviaDelArquitecto.setSala(sala);
//		
//		//Agregar la reunion previa al arquitecto
//		reunionPreviaDelArquitecto.agregaRecurso(arquitecto);
//		assertEquals(reunionPreviaDelArquitecto.getEstado() instanceof Concretado,true);
//		
//		//Trato de agregar un arquitecto a Reunion A Planificar
//		try{
//			
//			organizadorDeReunionAPlanificar.asignaUnEmpleadoQueCumplaPerfilAReunion(arquitecto.getPerfil(), reunionAPlanificar);
//		}catch(UserException e){
//			assertEquals( UserException.class ,e.getClass());
//		}
//		//Se verifica que no se pudo añadir por la superposicion de tiempo
//		assertEquals(reunionAPlanificar.getEstado() instanceof Planificado,true);
//		
//		reunionAPlanificar.agregaRecurso(arquitecto);
//		try{
//			reunionAPlanificar.concretar();
//		}catch(UserException e){
//			assertEquals( UserException.class ,e.getClass());
//		}	
//	}
//	
//	@Test
//	public void testSeReserveSalaPara3ProgramadoresYProyectLeaderConExitoYSeAgregaCateringAutomaticamente(){
//		Empresa empresa = new Empresa();
//		Organizador organizadorDeReunionAPlanificar = new Organizador(new EmpleadoImp());
//		Edificio edificio = new Edificio();
//		Proyecto proyecto = new Proyecto();
//		
//		
//		//Seteo de RecursosReunibles
//		DateTime inicio = new DateTime(2012, 1, 1, 9, 0, 0, 0);
//		DateTime fin = new DateTime(2012, 1, 1, 20, 0, 0, 0);
//		MutableInterval horarioDeTrabajo = new MutableInterval(inicio, fin);
//		
//		organizadorDeReunionAPlanificar.setEdificio(edificio);
//		organizadorDeReunionAPlanificar.setProyecto(proyecto);
//		organizadorDeReunionAPlanificar.setRol(Rol.PROJECT_LEADER);
//		organizadorDeReunionAPlanificar.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		organizadorDeReunionAPlanificar.setEmpresa(empresa);
//
//		Sala sala = new Sala();
//		sala.setEdificio(edificio);
//		Empleado programador1 = new EmpleadoImp();
//		programador1.setEdificio(edificio);
//		programador1.setProyecto(proyecto);
//		programador1.setRol(Rol.PROGRAMADOR);
//		programador1.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		Empleado programador2 = new EmpleadoImp();
//		programador2.setEdificio(edificio);
//		programador2.setProyecto(proyecto);
//		programador2.setRol(Rol.PROGRAMADOR);
//		programador2.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		Empleado programador3 = new EmpleadoImp();
//		programador3.setEdificio(edificio);
//		programador3.setProyecto(proyecto);
//		programador3.setRol(Rol.PROGRAMADOR);
//		programador3.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		//Seteo empresa
//		empresa.agregarEdificio(edificio);
//		empresa.agregarEmpleado(organizadorDeReunionAPlanificar);
//		empresa.agregarEmpleado(programador1);
//		empresa.agregarEmpleado(programador2);
//		empresa.agregarEmpleado(programador3);
//		
//		//Seteo de Horarios
//		DateTime inicioDeReunionAPlanificar = new DateTime(2012, 1, 1, 13, 0, 0, 0);
//		DateTime finDeReunionAPlanificar = new DateTime(2012, 1, 1, 13, 30, 0, 0);
//		MutableInterval horarioDeReunionAPlanificar = new MutableInterval(inicioDeReunionAPlanificar, finDeReunionAPlanificar);
//		DateTime fechaDeReunionAPlanificar = new DateTime(2012, 1, 1, 0, 0, 0, 0);
//				
//		//Seteo de Reunion A Planificar
//		Reunion reunionAPlanificar = new Reunion(organizadorDeReunionAPlanificar);
//		reunionAPlanificar.setEstado(Planificado.getInstance());
//		reunionAPlanificar.setHorario(horarioDeReunionAPlanificar);
//		reunionAPlanificar.setFecha(fechaDeReunionAPlanificar);
//		reunionAPlanificar.setEstado(Concretado.getInstance());
//		reunionAPlanificar.setSala(sala);
//				
//		//Trato de agregar un arquitecto a Reunion A Planificar
//		organizadorDeReunionAPlanificar.asignaUnEmpleadoQueCumplaPerfilAReunion(programador1.getPerfil(), reunionAPlanificar);
//		organizadorDeReunionAPlanificar.asignaUnEmpleadoQueCumplaPerfilAReunion(programador1.getPerfil(), reunionAPlanificar);
//		organizadorDeReunionAPlanificar.asignaUnEmpleadoQueCumplaPerfilAReunion(programador1.getPerfil(), reunionAPlanificar);
//		
//		assertTrue(reunionAPlanificar.getRecursos().contains(programador1));
//		assertTrue(reunionAPlanificar.getRecursos().contains(programador2));
//		assertTrue(reunionAPlanificar.getRecursos().contains(programador3));
//		assertTrue(reunionAPlanificar.getCatering());
//		
//	}
//	
//	@Test
//	public void testReservarSalaPara3GerentesCualquieraSinExitoPorDisponibilidadHoraria() {
//		Organizador organizador = new Organizador(new EmpleadoImp());
//		organizador.setRol(Rol.ARQUITECTO);
//		
//		// Seteo de reunion
//		DateTime inicioDeReunionAPlanificar = new DateTime(2012, 1, 1, 13, 0, 0, 0);
//		DateTime finDeReunionAPlanificar = new DateTime(2012, 1, 1, 13, 30, 0, 0);
//		MutableInterval horario = new MutableInterval(inicioDeReunionAPlanificar, finDeReunionAPlanificar);
//		DateTime fecha = new DateTime(2012, 1, 1, 0, 0, 0, 0);
//		
//		Reunion reunion1 = new Reunion(organizador);
//		Reunion reunionAPlanificar = new Reunion(organizador);
//		reunion1.setHorario(horario);
//		reunion1.setFecha(fecha);
//		reunionAPlanificar.setHorario(horario);
//		reunionAPlanificar.setFecha(fecha);
//		
//		// Seteo de gerentes
//		Empleado gerente1 = new EmpleadoImp();
//		Empleado gerente2 = new EmpleadoImp();
//		Empleado gerente3 = new EmpleadoImp();
//		gerente1.setRol(Rol.GERENTE);
//		gerente1.agendarReunion(reunion1);
//		gerente2.setRol(Rol.GERENTE);
//		gerente2.agendarReunion(reunion1);
//		gerente3.setRol(Rol.GERENTE);
//		gerente3.agendarReunion(reunion1);
//		
//		reunion1.agregaRecurso(gerente1);
//		reunion1.agregaRecurso(gerente2);
//		reunion1.agregaRecurso(gerente3);
//		reunion1.setEstado(Concretado.getInstance());
//		
//		reunionAPlanificar.agregaRecurso(gerente1);
//		reunionAPlanificar.agregaRecurso(gerente2);
//		reunionAPlanificar.agregaRecurso(gerente3);
//				
//		try{
//			reunionAPlanificar.concretar();
//		}catch(UserException e){
//			assertEquals( UserException.class ,e.getClass());
//		}
//	}
//	
//	@Test
//	public void testCostoDeProyectoXSegun1ReunionConNingunIntegrantesDeProyectoXMas1ReunionConSolo1IntegranteYEsDelProyectoX() {
//		//Seteo horario de trabajo
//		DateTime inicio = new DateTime(2012, 1, 1, 9, 0, 0, 0);
//		DateTime fin = new DateTime(2012, 1, 1, 17, 0, 0, 0);
//		MutableInterval horarioDeTrabajo = new MutableInterval(inicio, fin);
//		
//		// Seteo Empresa
//		Empresa empresa = new Empresa();
//		empresa.setCatering(0);
//		
//		// Seteo Edificio y Salas
//		Edificio edificio = new Edificio();
//		Sala sala = new Sala();
//		sala.setEdificio(edificio);
//		edificio.agregarSala(sala);
//		
//		// Seteo de Proyecto
//		Proyecto planner = new Proyecto();
//		Proyecto tpso = new Proyecto();
//		
//		// Seteo Organizador Planner
//		Organizador organizador =new Organizador( new EmpleadoImp());
//		organizador.setProyecto(planner);
//		organizador.setRol(Rol.ARQUITECTO);
//		organizador.setEdificio(edificio);
//		organizador.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		//Seteo Empleados
//		Empleado diseniador = new EmpleadoImp();
//		diseniador.setRol(Rol.DISENIADOR_DE_SISTEMAS);
//		diseniador.setProyecto(planner);
//		diseniador.setPrecio(10.00);
//		diseniador.setEdificio(edificio);
//		diseniador.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		Empleado programador1 = new EmpleadoImp();
//		programador1.setRol(Rol.PROGRAMADOR);
//		programador1.setProyecto(tpso);
//		programador1.setPrecio(10.00);
//		programador1.setEdificio(edificio);
//		programador1.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		Empleado programador2 = new EmpleadoImp();
//		programador2.setRol(Rol.PROGRAMADOR);
//		programador2.setProyecto(tpso);
//		programador2.setPrecio(10.00);
//		programador2.setEdificio(edificio);
//		programador2.setHorarioDeTrabajo(horarioDeTrabajo);
//		
//		//Seteo Reunion
//		Reunion reunionConIntegrantesDeOtroProyecto = new Reunion(organizador);
//		DateTime inicioDeReunionAPlanificar = new DateTime(2012, 1, 1, 10, 0, 0, 0);
//		DateTime finDeReunionAPlanificar = new DateTime(2012, 1, 1, 10, 30, 0, 0);
//		MutableInterval horario = new MutableInterval(inicioDeReunionAPlanificar, finDeReunionAPlanificar);
//		DateTime fecha = new DateTime(2012, 1, 1, 0, 0, 0, 0);
//		reunionConIntegrantesDeOtroProyecto.setFecha(fecha);
//		reunionConIntegrantesDeOtroProyecto.setHorario(horario);
//		reunionConIntegrantesDeOtroProyecto.setSala(sala);
//		reunionConIntegrantesDeOtroProyecto.agregaRecurso(programador1);
//		reunionConIntegrantesDeOtroProyecto.agregaRecurso(programador2);
//		reunionConIntegrantesDeOtroProyecto.concretar();
//		
//		//Seteo reunion
//		Reunion reunionCon1IntegranteMismoProyecto = new Reunion(organizador);
//		DateTime fecha2 = new DateTime(2012, 1, 3, 0, 0, 0, 0);
//		reunionCon1IntegranteMismoProyecto.setFecha(fecha2);
//		reunionCon1IntegranteMismoProyecto.setHorario(horario);
//		reunionCon1IntegranteMismoProyecto.setSala(sala);
//		reunionCon1IntegranteMismoProyecto.agregaRecurso(programador1);
//		reunionCon1IntegranteMismoProyecto.agregaRecurso(diseniador);
//		reunionCon1IntegranteMismoProyecto.concretar();
//		
//		
//		assertTrue(planner.getCosto()==15);
//	}
}

