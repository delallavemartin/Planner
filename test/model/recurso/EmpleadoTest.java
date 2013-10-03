package model.recurso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Edificio;
import model.Empresa;
import model.Proyecto;
import model.evento.Actividad;
import model.evento.Evento;
import model.evento.Reunion;
import model.recurso.EmpleadoImp;
import model.recurso.Rol;
import model.recurso.Sala;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.MutableInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import exception.UserException;

public class EmpleadoTest{

	private Duration cero = new Duration(0);
	private Duration treinta = new Duration(1800000);//60000 -> 1 minuto 1800000 -> 30 minutos
	private Empleado empleado;
	private Duration margen;
	
	@Mock private Reunion reunion;
	@Mock private Actividad actividad;
	@Mock private Actividad actividAOrganizar;
	@Mock private Edificio edificio;
	@Mock private Sala sala;
	@Mock private Empresa empresa;
	private Proyecto proyecto;
	@Mock private Reunion reunionAOrganizar;
	private List<Reunion> reuniones;
	private List<Actividad> actividades;
	
	@Before
	public void setUp() throws Exception {
			
		MockitoAnnotations.initMocks(this);
		this.reunion.setSala(sala);
		this.proyecto = new Proyecto();
		this.reuniones = new ArrayList<Reunion>();
		this.reuniones.add(reunion);	
		this.actividades = new ArrayList<Actividad>();
		this.actividades.add(this.actividad);
		
		this.empleado = new EmpleadoImp();
		this.empleado.setNombre("Claudio");
		this.empleado.setEmpresa(empresa);
		this.empleado.setProyecto(this.proyecto);													
		this.empleado.setSector("Sistemas");	
		this.empleado.setEdificio(edificio);
		this.empleado.setReuniones(reuniones);	
		this.empleado.setActividades(actividades);
		this.empleado.setPrecio(20);
		
		DateTime inicio = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime fin = new DateTime(2012, 1, 1, 17, 0, 0, 0);
		MutableInterval horarioDeTrabajo = new MutableInterval(inicio, fin);
		empleado.setHorarioDeTrabajo(horarioDeTrabajo);
	}
	
	@Test
	public void testTiempoParaTrasladarmeIgualACero(){
		
		Mockito.doReturn(cero).when(reunion).tiempoParaTrasladarmeA(this.empleado);
				
		assertEquals(cero, this.empleado.tiempoParaTrasladarmeA(reunion));
	}
		
	@Test
	public void testTiempoParaTrasladarmeDisitintoDeCero(){	

		Mockito.doReturn(treinta).when(reunion).tiempoParaTrasladarmeA(this.empleado);
		
		assertEquals(treinta, this.empleado.tiempoParaTrasladarmeA(reunion));
	}
	
	@Test
	public void testMargenIgualACero(){
		
		Mockito.doReturn(cero).when(reunion).tiempoParaTrasladarmeA(this.empleado);
		
		assertEquals(cero, this.empleado.obtenerMargen(reunion));
	}
	
	@Test
	public void testMargenDistintoACero(){
		
		Mockito.doReturn(treinta).when(reunion).tiempoParaTrasladarmeA(this.empleado);
		
		assertEquals(treinta, this.empleado.obtenerMargen(reunion));
	}
	
	@Test
	public void testEmpleadoRequiereCatering() {
		//TODO al implementar RequerisCatering esto debera ser modificado
//		Mockito.doReturn(true).when(perfil).esGerenteOProjectLeader();
//		
//		this.perfil.esGerenteOProjectLeader();		
//		
//		Mockito.verify(perfil).esGerenteOProjectLeader();
		
		this.empleado.setRol(Rol.PROJECT_LEADER);	//project leader requiere catering
		assertTrue(empleado.requerisCatering());
	}
	
	@Test
	public void testEmpleadoNoRequiereCatering() {

//		Mockito.doReturn(false).when(perfil).esGerenteOProjectLeader();
//		
//		this.perfil.esGerenteOProjectLeader();		
//		
//		Mockito.verify(perfil).esGerenteOProjectLeader();
		
		this.empleado.setRol(Rol.PROGRAMADOR);	//un programador no requiere catering		
		assertFalse(this.empleado.requerisCatering());
	}
	
	@Test
	public void testEmpleadoEstaDisponible() {
		//Seteo de la reunion agendada
		DateTime inicioReunion = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime finReunion = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeReunion = new MutableInterval(inicioReunion, finReunion);
				
		Mockito.doReturn(horarioDeReunion).when(reunion).getHorario();
		reunion.getHorario();		
		Mockito.verify(reunion).getHorario();
		
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
						
		Mockito.doReturn(horarioDeActividad).when(actividad).getHorario();
		actividad.getHorario();		
		Mockito.verify(actividad).getHorario();
				
		//seteo de reunion a organizar
		DateTime inicioDeReunionAOrganizar = new DateTime(2012, 1, 1, 10, 30, 0, 0);
		DateTime finDeReunionAOrganizar = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		MutableInterval horarioDeReunionAOrganizar = new MutableInterval(inicioDeReunionAOrganizar, finDeReunionAOrganizar);
		
		Mockito.doReturn(horarioDeReunionAOrganizar).when(reunionAOrganizar).getHorario();
		Mockito.doReturn(false).when(reunion).teSuperponesCon(reunionAOrganizar, treinta);
		
		this.reunion.teSuperponesCon(reunionAOrganizar, treinta);		
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar, treinta);
	
		assertTrue(empleado.estasDisponiblePara(reunionAOrganizar));
	}
	
	//empleado.obtenerMargen(reunionAORganizar) -> devuelve: treinta
	@Test
	public void testEmpleadoNoEstaDisponible() {	
		Mockito.doReturn(true).when(reunion).teSuperponesCon(reunionAOrganizar, treinta);
		
		this.reunion.teSuperponesCon(reunionAOrganizar, treinta);		
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar, treinta);
				
		assertFalse(empleado.estasDisponiblePara(reunionAOrganizar));
	}	
	
	@Test
	public void testDamePresupuestoParaUnEmpleadoQuePerteneceAlProyecto(){
		Mockito.doReturn(true).when(reunion).perteneceAlProyecto(proyecto);
		assertTrue(empleado.presupuestoPara(reunion) == 0);
	}
	
	
	@Test
	public void testDamePresupuestoParaUnEmpleadoQueNecesitaTransporte(){
		Mockito.doReturn(10.00).when(empresa).getCostoDeTransporte();
		Mockito.doReturn(0.5).when(reunion).duracion();
		Mockito.doReturn(sala).when(reunion).getSala();
		Mockito.doReturn(false).when(sala).estasUbicadaEnEdificio(edificio);
		Mockito.doReturn(false).when(reunion).estasUbicadaEnEdificio(edificio);
		Mockito.doReturn(false).when(reunion).perteneceAlProyecto(proyecto);
		assertTrue(20.00 == empleado.presupuestoPara(reunion) );
	}
	
	@Test
	public void testDamePresupuestoParaUnEmpleadoQueNoNecesitaTransporte(){
		Mockito.doReturn(0.5).when(reunion).duracion();
		Mockito.doReturn(sala).when(reunion).getSala();
		Mockito.doReturn(false).when(sala).estasUbicadaEnEdificio(edificio);
		Mockito.doReturn(true).when(reunion).estasUbicadaEnEdificio(edificio);
		Mockito.doReturn(false).when(reunion).perteneceAlProyecto(proyecto);
		assertTrue(empleado.presupuestoPara(reunion) == 10);
		
	}
	@Test
	public void testEmpleadoNoDisponibleParaReunionPorNoTener4HorasLibres(){
		//Seteo de la reunion agendada
		DateTime inicioReunion = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime finReunion = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		MutableInterval horarioDeReunion = new MutableInterval(inicioReunion, finReunion);
		
		Mockito.doReturn(horarioDeReunion).when(reunion).getHorario();
		reunion.getHorario();		
		Mockito.verify(reunion).getHorario();
		
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
								
		Mockito.doReturn(horarioDeActividad).when(actividad).getHorario();
		actividad.getHorario();		
		Mockito.verify(actividad).getHorario();
		
		//Seteo de la reunion a organizar
		DateTime inicioReunionAOrganizar= new DateTime(2012, 1, 1,10, 0, 0, 0);
		DateTime finReunionAOrganizar = new DateTime(2012, 1, 1, 14, 0, 0, 0);
		MutableInterval horarioDeReunionAOrganizar = new MutableInterval(inicioReunionAOrganizar, finReunionAOrganizar);
				
		Mockito.doReturn(horarioDeReunionAOrganizar).when(reunionAOrganizar).getHorario();
		reunionAOrganizar.getHorario();		
		Mockito.verify(reunionAOrganizar).getHorario();
		
		Mockito.doReturn(true).when(reunionAOrganizar).estasUbicadaEnEdificio(edificio);
		reunionAOrganizar.estasUbicadaEnEdificio(edificio);
		Mockito.verify(reunionAOrganizar).estasUbicadaEnEdificio(edificio);
				
		assertFalse(empleado.estasDisponiblePara(reunionAOrganizar));				
	}
	@Test
	public void testEmpleadoDisponibleParaActividad(){
		//Creacion de horarios y fechas
		DateTime inicioActividadAgendada= new DateTime(2012, 1, 1,10, 0, 0, 0);
		DateTime finActividadAgendada = new DateTime(2012, 1, 1, 10, 30, 0, 0);
		MutableInterval horarioDeActividadAgendada = new MutableInterval(inicioActividadAgendada, finActividadAgendada);
		
		DateTime inicioActividadAOrganizar= new DateTime(2012, 1, 1, 10, 30, 0, 0);
		DateTime finActividadAOrganizar = new DateTime(2012, 1, 1, 12, 0, 0, 0);
		MutableInterval horarioDeActividadAOrganizar = new MutableInterval(inicioActividadAOrganizar, finActividadAOrganizar);
		
		//Creacion de actividad agendada		
		Mockito.doReturn(horarioDeActividadAgendada).when(this.actividad).getHorario();
		this.actividad.getHorario();		
		Mockito.verify(this.actividad).getHorario();
		
		
		//Crear actividad a organizar		
		Mockito.doReturn(horarioDeActividadAOrganizar).when(this.actividAOrganizar).getHorario();
		this.actividAOrganizar.getHorario();		
		Mockito.verify(this.actividAOrganizar).getHorario();
		
		assertTrue(this.empleado.estasDisponiblePara(this.actividAOrganizar));
	}
	@Test
	public void testEmpleadoNoDisponibleParaActividad(){
		//Creacion de horarios y fechas
		DateTime inicioActividadAgendada= new DateTime(2012, 1, 1,10, 0, 0, 0);
		DateTime finActividadAgendada = new DateTime(2012, 1, 1, 10, 30, 0, 0);
		MutableInterval horarioDeActividadAgendada = new MutableInterval(inicioActividadAgendada, finActividadAgendada);
		
		DateTime inicioActividadAOrganizar= new DateTime(2012, 1, 10, 0, 0, 0, 0);
		DateTime finActividadAOrganizar = new DateTime(2012, 1, 12, 0, 0, 0, 0);
		MutableInterval horarioDeActividadAOrganizar = new MutableInterval(inicioActividadAOrganizar, finActividadAOrganizar);
		
		//Creacion de actividad agendada		
		Actividad actividadAgendada = new Actividad();
		actividadAgendada.setHorario(horarioDeActividadAgendada);
		
		
		//Agregar actividad agendada
		List<Actividad> actividades = new ArrayList<Actividad>();
		actividades.add(actividadAgendada);
		this.empleado.setActividades(actividades);
		
		//Crear actividad a organizar		
		Actividad actividadAOrganizar = new Actividad();
		actividadAOrganizar.setHorario(horarioDeActividadAOrganizar);
		
		assertFalse(this.empleado.estasDisponiblePara(actividadAOrganizar));
	}
	
	@Test
	public void testEmpleadoDameTiempoDedicadoParaUnaActividad(){
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
										
		Actividad actividad = new Actividad();
		actividad.setHorario(horarioDeActividad);
		
		this.empleado.agendarActividad(actividad);
		
		assertEquals(this.empleado.dameTiempoDedicadoPara(actividad),treinta);
	}
	
	@Test
	public void testEmpleadoDamePorcentajeDeTiempoDedicadoParaUnaActividad(){
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
												
		Actividad actividad = new Actividad();
		actividad.setHorario(horarioDeActividad);
				
		this.empleado.agendarActividad(actividad);
					
		assertEquals(6.25, this.empleado.damePorcentajeDeTiempoDedicadoPara(actividad), 6.25);	
	}
	@Test
	public void testEmpleadoDameDineroInvertidoEnUnaActividad(){
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
												
		Actividad actividad = new Actividad();
		actividad.setHorario(horarioDeActividad);
				
		this.empleado.agendarActividad(actividad);
		
		assertEquals(10.0, this.empleado.dameDineroInvertidoEn(actividad), 10.0);	
	}
	@Test(expected=UserException.class)
	public void testEmpleadoDameDineroInvertidoEnUnaActividadQueNoTengo(){
		//Seteo de la actividad agendada
		DateTime inicioActividad = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad = new MutableInterval(inicioActividad, finActividad);
												
		Actividad actividad = new Actividad();
		actividad.setHorario(horarioDeActividad);		
		
		this.empleado.dameDineroInvertidoEn(actividad);
	}
	@Test
	public void testEmpleadoDameTiempoDedicadoParaUnGrupoDeActividades(){
		//Seteo de la actividad agendada
		DateTime inicioActividad1 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad1 = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad1 = new MutableInterval(inicioActividad1, finActividad1);
		
		DateTime inicioActividad2 = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime finActividad2 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		MutableInterval horarioDeActividad2 = new MutableInterval(inicioActividad2, finActividad2);
		
		DateTime inicioReunion = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		DateTime finReunion = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		MutableInterval horarioDeReunion = new MutableInterval(inicioReunion, finReunion);
		
		
		Actividad actividad1 = new Actividad();
		actividad1.setHorario(horarioDeActividad1);
		Actividad actividad2 = new Actividad();
		actividad2.setHorario(horarioDeActividad2);
		
		Mockito.doReturn(horarioDeReunion.toDuration()).when(reunion).getDuracion();
		reunion.getDuracion();		
		Mockito.verify(reunion).getDuracion();
		
		this.empleado.agendarActividad(actividad1);
		this.empleado.agendarActividad(actividad2);
		
		List<Evento> grupoDeEventos = new ArrayList<Evento>();
		grupoDeEventos.add(actividad1);
		grupoDeEventos.add(actividad2);
		grupoDeEventos.add(reunion);
				
		assertEquals(this.empleado.dameTiempoDedicadoPara(grupoDeEventos),Hours.TWO.toStandardDuration());			
	}
	@Test
	public void testEmpleadoDamePorcentajeDeTiempoDedicadoParaUnGrupoDeActividades(){
		//Seteo de la actividad agendada
		DateTime inicioActividad1 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad1 = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad1 = new MutableInterval(inicioActividad1, finActividad1);
				
		DateTime inicioActividad2 = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime finActividad2 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		MutableInterval horarioDeActividad2 = new MutableInterval(inicioActividad2, finActividad2);
				
		DateTime inicioReunion = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		DateTime finReunion = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		MutableInterval horarioDeReunion = new MutableInterval(inicioReunion, finReunion);
				
				
		Actividad actividad1 = new Actividad();
		actividad1.setHorario(horarioDeActividad1);
		Actividad actividad2 = new Actividad();
		actividad2.setHorario(horarioDeActividad2);
				
		Mockito.doReturn(horarioDeReunion.toDuration()).when(reunion).getDuracion();
		reunion.getDuracion();		
		Mockito.verify(reunion).getDuracion();
				
		this.empleado.agendarActividad(actividad1);
		this.empleado.agendarActividad(actividad2);
				
		List<Evento> grupoDeEventos = new ArrayList<Evento>();
		grupoDeEventos.add(actividad1);
		grupoDeEventos.add(actividad2);
		grupoDeEventos.add(reunion);
					
		assertEquals(25, this.empleado.damePorcentajeDeTiempoDedicadoPara(grupoDeEventos), 25);	
	}
	@Test
	public void testEmpleadoDameDineroInvertidoEnUnGrupoDeActividades(){
		//Seteo de la actividad agendada
		DateTime inicioActividad1 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		DateTime finActividad1 = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		MutableInterval horarioDeActividad1 = new MutableInterval(inicioActividad1, finActividad1);
						
		DateTime inicioActividad2 = new DateTime(2012, 1, 1, 9, 0, 0, 0);
		DateTime finActividad2 = new DateTime(2012, 1, 1, 9, 30, 0, 0);
		MutableInterval horarioDeActividad2 = new MutableInterval(inicioActividad2, finActividad2);
						
		DateTime inicioReunion = new DateTime(2012, 1, 1, 10, 0, 0, 0);
		DateTime finReunion = new DateTime(2012, 1, 1, 11, 0, 0, 0);
		MutableInterval horarioDeReunion = new MutableInterval(inicioReunion, finReunion);
						
						
		Actividad actividad1 = new Actividad();
		actividad1.setHorario(horarioDeActividad1);
		Actividad actividad2 = new Actividad();
		actividad2.setHorario(horarioDeActividad2);
						
		Mockito.doReturn(horarioDeReunion.toDuration()).when(reunion).getDuracion();
		reunion.getDuracion();		
		Mockito.verify(reunion).getDuracion();
						
		this.empleado.agendarActividad(actividad1);
		this.empleado.agendarActividad(actividad2);
						
		List<Evento> grupoDeEventos = new ArrayList<Evento>();
		grupoDeEventos.add(actividad1);
		grupoDeEventos.add(actividad2);
		grupoDeEventos.add(reunion);
		
		assertEquals(40, this.empleado.dameDineroInvertidoEn(grupoDeEventos), 40);	
	}

}
