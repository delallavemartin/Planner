package model.recurso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Proyecto;

import org.junit.Before;
import org.junit.Test;

public class OrganizadorTest {
	
	private static final double UN_COSTO = 400.00;
	private Proyecto proyecto;
	private Organizador organizador;
	
	
	@Before
	public void setUp()
	{
		Empleado unEmpleado = new EmpleadoImp();
		organizador = new Organizador(unEmpleado);	
	}
	
	

	//Necesarios por cuestiones de implementacion (para comparadores y tablas hash)
	@Test
	public void testCompararUnOrganizadorConSiMismoSeEsperaQueSeanIguales()
	{	
		assertTrue(this.organizador.equals(organizador));
	}
	
	@Test
	public void testCompararDosInstanciasDistinotasDeOrganizadorSeEsperaQueSeanDistintasYTenganDistinoHashCode()
	{	
		Organizador otroOrganizador = new Organizador(new EmpleadoImp());
	
		assertFalse(this.organizador.equals(otroOrganizador));
		assertFalse(otroOrganizador.equals(this.organizador));
		assertFalse(this.organizador.hashCode() == otroOrganizador.hashCode());
	}

	@Test
	public void testCompararUnEmpleadoConSiMismoSeEsperaQueSeanIguales()
	{	
		Empleado unEmpleado = new EmpleadoImp();
		assertTrue(unEmpleado.equals(unEmpleado));
	}
	
	@Test
	public void testCompararDosInstanciasDistinotasDeEmpleadoSeEsperaQueSeanDistintasYTenganDistinoHashCode()
	{
		Empleado unEmpleado = new EmpleadoImp();
		Empleado otroEmpleado = new EmpleadoImp();
		
		assertFalse(unEmpleado.equals(otroEmpleado));
		assertFalse(otroEmpleado.equals(unEmpleado));
		assertFalse(otroEmpleado.hashCode() == unEmpleado.hashCode());
		
	}
	
	@Test
	public void testCrearUnOrganizadorAPartirDeUnEmpleadoSeEsperaQueSeConsiderenIgualesYTenganElMismoHashCode()
	{
		Empleado unEmpleado = new EmpleadoImp();		
		Organizador elMismoEmpleadoDecoradoComoOrganizador = new Organizador(unEmpleado);
		
		
		assertTrue(elMismoEmpleadoDecoradoComoOrganizador.equals(unEmpleado));
		assertTrue(unEmpleado.equals(elMismoEmpleadoDecoradoComoOrganizador));				
		assertEquals(unEmpleado.hashCode() , elMismoEmpleadoDecoradoComoOrganizador.hashCode());
		
	}
	@Test
	public void testCrearUnEmpleadoYUnOrganizadorQueNoDecoraAEseEmpleadoSeEsperaQueSeConsiderenDistintosYTenganDistintoHashCode()
	{
		Empleado otroEmpleado = new EmpleadoImp();				
		
		assertFalse(otroEmpleado.equals(this.organizador));
		assertFalse(this.organizador.equals(otroEmpleado));				
		assertFalse(this.organizador.hashCode() == otroEmpleado.hashCode());
	}	
//**
	
	@Test
	public void testAsignarCostoDeReunion(){

		
		this.proyecto = new Proyecto();
		this.organizador.setProyecto(proyecto);
		
		this.organizador.asignarCostoDeEvento(UN_COSTO);
		
		assertTrue(proyecto.getCosto() == UN_COSTO);
	}
	
	
	
//	public void organizarReunion()
//	{
//		Organizador or = new Organizador(new EmpleadoImp());
//		
//		List<Caracteristica<Empleado>> caracteristicasDeEmpleadosOpcionales = new ArrayList<Caracteristica<Empleado>>();
//		caracteristicasDeEmpleadosOpcionales.add(new Perfil());
//		
//		List<Caracteristica<Herramienta>> caracteristicasDeHerramientasOpcionales = new ArrayList<Caracteristica<Herramienta>>();	
//		caracteristicasDeHerramientasOpcionales.add(new Herramienta(Herramienta.NombreDeHerramienta.canion));
//	
//		List<Caracteristica< ? extends  RecursoReunible>> caracteristicasOpcionales = new ArrayList<Caracteristica<? extends RecursoReunible>>();
//		
//		caracteristicasOpcionales.addAll(caracteristicasDeEmpleadosOpcionales);
//		caracteristicasOpcionales.addAll(caracteristicasDeHerramientasOpcionales);
//		
//		
//		@SuppressWarnings("unchecked")
//		List<Caracteristica<RecursoReunible>>  li = ListUtils.union(caracteristicasDeHerramientasOpcionales, caracteristicasDeEmpleadosOpcionales);
//
//		or.organizaReunionCon(li, li);
//	}
	
	
	
	

}
