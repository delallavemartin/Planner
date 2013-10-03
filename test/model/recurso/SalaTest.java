package model.recurso;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.ComparadorDeDisponibilidad;
import model.Edificio;
import model.evento.Reunion;
import model.recurso.Sala;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SalaTest {

	private Sala sala;
	private ComparadorDeDisponibilidad comparador;
	@Mock private Reunion reunion;
	@Mock private Edificio edificioSala;
	@Mock private Reunion reunionAOrganizar;
	private List<Reunion> reuniones;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		this.comparador = new ComparadorDeDisponibilidad();
		this.reuniones = new ArrayList<Reunion>();
		this.reuniones.add(reunion);		
		this.sala = new Sala();	
		this.sala.setEdificio(edificioSala);
		this.sala.setReuniones(reuniones);
		this.sala.setComparadorDeDisponibilidad(comparador);
				
	}
	
	@Test
	public void testSalaEstaDisponibleParaReunionEnTuMismoEdificio() {
		
		Mockito.doReturn(false).when(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.doReturn(true).when(reunionAOrganizar).estasUbicadaEnEdificio(edificioSala);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		this.reunion.estasUbicadaEnEdificio(sala.getEdificio());
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.verify(reunion).estasUbicadaEnEdificio(edificioSala);
		
		assertTrue(sala.estasDisponiblePara(reunionAOrganizar));
	}
	
	//Este tests prueba que este funcionando la restricci—n agregada a la entrega 2, es decir
	//que una sala no pueda estar disponible en un edificio q no es el suyo, quizas el 
	//test se podria llamar testSalaNoEstaDisponibleParaReunionEnOtroEdificio()
		
	@Test
	public void testSalaEstaDisponibleParaReunionEnOtroEdificio() {
		
		Mockito.doReturn(false).when(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.doReturn(false).when(reunionAOrganizar).estasUbicadaEnEdificio(edificioSala);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		this.reunion.estasUbicadaEnEdificio(sala.getEdificio());
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		Mockito.verify(reunion).estasUbicadaEnEdificio(edificioSala);
		
		assertFalse(sala.estasDisponiblePara(reunionAOrganizar));
	}
	
	@Test
	public void testHerramientaNoEstaDisponible() {		
		
		Mockito.doReturn(true).when(reunion).teSuperponesCon(reunionAOrganizar);
		
		this.reunion.teSuperponesCon(reunionAOrganizar);		
		
		Mockito.verify(reunion).teSuperponesCon(reunionAOrganizar);
		
		assertFalse(sala.estasDisponiblePara(reunionAOrganizar));
	}

}
