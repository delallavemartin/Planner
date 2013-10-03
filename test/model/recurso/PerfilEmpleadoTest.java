package model.recurso;

import static org.junit.Assert.*;
import model.Proyecto;
import model.recurso.EmpleadoImp;
import model.recurso.PerfilEmpleado;
import model.recurso.Rol;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PerfilEmpleadoTest {

	private static final String OTRO_SECTOR = "Direccion";
	private static final String NOMBRE_DEL_PERFIL = "Martin";
	private static final String SECTOR_DEL_PERFIL = "Desarrollo";
	private PerfilEmpleado unPerfil = new PerfilEmpleado();
	private Proyecto proyectoDelPerfil = new Proyecto();
	
	@Mock
	private EmpleadoImp unEmpleado;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		this.proyectoDelPerfil.setNombre("TADP");
		
		this.unPerfil.setNombre(NOMBRE_DEL_PERFIL);

		this.unPerfil.setSector(SECTOR_DEL_PERFIL);
		this.unPerfil.setProyecto(proyectoDelPerfil);
	
	}

//testBasicos
	
	@Test
	public void testPerfilesIncompatiblesConProyectoYRol() {
		
		Rol unRol = Rol.ARQUITECTO;
		Rol otroRol = Rol.DISENIADOR_DE_SISTEMAS;
		
		this.unPerfil.setRol(unRol);
		Mockito.doReturn(otroRol).when(this.unEmpleado).getRol();
		Mockito.doReturn(proyectoDelPerfil).when(this.unEmpleado).getProyecto();
		
		Mockito.doReturn(NOMBRE_DEL_PERFIL).when(this.unEmpleado).getNombre();
		Mockito.doReturn(SECTOR_DEL_PERFIL).when(this.unEmpleado).getSector();
		
		
		assertFalse(this.unPerfil.esCompatibleCon(this.unEmpleado));
		
		
	}
	
	@Test
	public void testPerfilesIncompatiblesConSectorYRol() {
		
		Rol unRol = Rol.ARQUITECTO;
		Rol otroRol = Rol.DISENIADOR_DE_SISTEMAS;

		this.unPerfil.setRol(unRol);
		
		Mockito.doReturn(otroRol).when(this.unEmpleado).getRol();
		Mockito.doReturn(OTRO_SECTOR).when(this.unEmpleado).getSector();

		assertFalse(unPerfil.esCompatibleCon(this.unEmpleado));
	}
	
	@Test
	public void testPerfilesCompatiblesConProyectoYRol() {
		Rol unRol = Rol.ARQUITECTO;
		this.unPerfil.setRol(unRol);

		
		PerfilEmpleado otroPerfil = new PerfilEmpleado();
		Mockito.doReturn(unRol).when(this.unEmpleado).getRol();
		Mockito.doReturn(proyectoDelPerfil).when(this.unEmpleado).getProyecto();
		
		//otroPerfil.setProyecto(proyecto);
		assertTrue(unPerfil.esCompatibleCon(this.unEmpleado));
	}
	
	@Test
	public void testPerfilesCompatiblesConSectorYRol() {
		Rol unRol = Rol.ARQUITECTO;
		this.unPerfil.setRol(unRol);

		Mockito.doReturn(unRol).when(this.unEmpleado).getRol();
		Mockito.doReturn(SECTOR_DEL_PERFIL).when(this.unEmpleado).getSector();
		
		PerfilEmpleado otroPerfil = new PerfilEmpleado();
		
		
		assertTrue(unPerfil.esCompatibleCon(this.unEmpleado));
	}
	
	@Test
	public void testTieneIgualNombre() {
		
		Mockito.doReturn(NOMBRE_DEL_PERFIL).when(this.unEmpleado).getNombre();
		PerfilEmpleado otroPerfil = new PerfilEmpleado();		
		Mockito.doReturn(SECTOR_DEL_PERFIL).when(this.unEmpleado).getSector();
		

		assertTrue(unPerfil.tieneIgualNombreQue(this.unEmpleado));
	}
	
	@Test
	public void testTieneIgualRol() {
		Rol unRol = Rol.ARQUITECTO;
		
		this.unPerfil.setRol(unRol);

		PerfilEmpleado otroPerfil = new PerfilEmpleado();
		
		Mockito.doReturn(unRol).when(this.unEmpleado).getRol();
		Mockito.doReturn(SECTOR_DEL_PERFIL).when(this.unEmpleado).getSector();
			
		assertTrue(unPerfil.tieneIgualRolQue(this.unEmpleado));
	}
	
	@Test
	public void testTieneIgualProyecto() {
		
		
		Mockito.doReturn(NOMBRE_DEL_PERFIL).when(this.unEmpleado).getNombre();
		Mockito.doReturn(proyectoDelPerfil).when(this.unEmpleado).getProyecto();
		
		assertTrue(unPerfil.tieneIgualProyectoQue(this.unEmpleado));
	}
	
	@Test
	public void testTieneIgualSector() {
		PerfilEmpleado otroPerfil = new PerfilEmpleado();
		
		Mockito.doReturn(SECTOR_DEL_PERFIL).when(this.unEmpleado).getSector();
		
		assertTrue(unPerfil.tieneIgualSectorQue(this.unEmpleado));
	}

}
