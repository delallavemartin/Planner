package model.recurso;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.ComparadorDeDisponibilidad;
import model.Edificio;
import model.evento.Reunion;
import model.recurso.Herramienta;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class HerramientaTest {
	
	private Herramienta herramienta;
	@Mock private Reunion reunion;
	@Mock private Edificio edificioHerramienta;
	@Mock private Reunion reunionAOrganizar;
	private List<Reunion> reuniones;
	private ComparadorDeDisponibilidad comparador;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		this.comparador = new ComparadorDeDisponibilidad();
		this.reuniones = new ArrayList<Reunion>();
		this.reuniones.add(reunion);		
		this.herramienta = new Herramienta(Herramienta.NombreDeHerramienta.canion);
		this.herramienta.setEdificio(edificioHerramienta);
		this.herramienta.setReuniones(reuniones);
		this.herramienta.setComparadorDeDisponibilidad(comparador);
				
	}
	
	@Test
	public void testHerramientaEstaDisponibleParaReunionEnTuMismoEdificio() {
		
		Mockito.doReturn(false).when(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.doReturn(true).when(reunionAOrganizar).estasUbicadaEnEdificio(edificioHerramienta);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		this.reunion.estasUbicadaEnEdificio(herramienta.getEdificio());
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.verify(reunion).estasUbicadaEnEdificio(edificioHerramienta);
		
		assertTrue(herramienta.estasDisponiblePara(reunionAOrganizar));
	}
	
	//Este tests prueba que este funcionando la restricci—n agregada a la entrega 2, es decir
	//que una herramienta no pueda estar disponible en un edificio q no es el suyo, quizas el 
	//test se podria llamar testHerramientaNoEstaDisponibleParaReunionEnOtroEdificio()
	
	@Test
	public void testHerramientaEstaDisponibleParaReunionEnOtroEdificio() {
		
		Mockito.doReturn(false).when(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.doReturn(false).when(reunionAOrganizar).estasUbicadaEnEdificio(edificioHerramienta);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		this.reunion.estasUbicadaEnEdificio(herramienta.getEdificio());
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.verify(reunion).estasUbicadaEnEdificio(edificioHerramienta);
		
		assertFalse(herramienta.estasDisponiblePara(reunionAOrganizar));
	}
	
	@Test
	public void testHerramientaNoEstaDisponible() {		
		
		Mockito.doReturn(true).when(reunion).teSuperponesCon(reunionAOrganizar);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		
		assertFalse(herramienta.estasDisponiblePara(reunionAOrganizar));
	}

}
