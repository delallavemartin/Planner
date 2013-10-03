package model.priorizacion;

import java.util.HashSet;
import java.util.Set;

import model.Edificio;
import model.evento.Reunion;
import model.recurso.Recurso;
import model.recurso.Sala;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PriorizacionTest {

	
	
	private static final double PRECIO_BARATO = 10;
	private static final double PRECIO_INTERMEDIO = 555;
	private static final double PRECIO_CARO = 999;
	

	private static final Duration POCAS_HORAS = Duration.standardHours(2);
	private static final Duration CANTIDAD_INTERMEDIA_DE_HORAS = Duration.standardHours(10);
	private static final Duration MUCHAS_HORAS = Duration.standardHours(20);
	
	private static final Period A_PARTIR_DE_UNA_SEMANA_ATRAS = MenosHorasDeReunionEnUltimaSemana.UNA_SEMANA_ATRAS;

	
	
	
	
	
	private Reunion reunion;
	private Set<Recurso> recursos;
	private Edificio edificioDeReunion;
	private Sala salaDeLaReunion;
	@Before
	public void setUp(){
		this.reunion = Mockito.mock(Reunion.class);
		this.recursos = new HashSet<Recurso>();
		
		this.edificioDeReunion = Mockito.mock(Edificio.class);		
		this.salaDeLaReunion = Mockito.mock(Sala.class);
		
		
		Mockito.when(this.salaDeLaReunion.getEdificio()).thenReturn(this.edificioDeReunion);
		
		Mockito.when(this.reunion.getSala()).thenReturn(this.salaDeLaReunion);
		
		
	}
	
	@Test
	public void testPriorizacionSimplePorMenorPrecio(){
		
		Recurso unRecursoQueEsElMasBarato = this.creaRecursoQueDevuelvaElPrecio(PRECIO_BARATO);
		Recurso otroRecursoQueTambienEsElMasBarato = this.creaRecursoQueDevuelvaElPrecio(PRECIO_BARATO);
				
		this.recursos.add(unRecursoQueEsElMasBarato);
		this.recursos.add(otroRecursoQueTambienEsElMasBarato);		
		this.recursos.add(this.creaRecursoQueDevuelvaElPrecio(PRECIO_INTERMEDIO));
		this.recursos.add(this.creaRecursoQueDevuelvaElPrecio(PRECIO_INTERMEDIO));
		this.recursos.add(this.creaRecursoQueDevuelvaElPrecio(PRECIO_CARO));
		
		Priorizacion<Recurso> priorizacion = new PriorizacionSimple(new MenorCosto());

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoQueEsElMasBarato));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoQueTambienEsElMasBarato));
		Assert.assertTrue(recursosPriorizados.size() == 2);
				
	}
	@Test
	public void testPriorizacionSimplePorMenosHorasDeReunionEnUltimaSemana(){

		Recurso unRecursoQueTieneMenosHoras = this.creaRecursoQueEnLaUltimaSemanaTiene(POCAS_HORAS);
		Recurso otroRecursoQueTambienTieneMenosHoras = this.creaRecursoQueEnLaUltimaSemanaTiene(POCAS_HORAS);
				
		this.recursos.add(unRecursoQueTieneMenosHoras);
		this.recursos.add(otroRecursoQueTambienTieneMenosHoras);		
		this.recursos.add(this.creaRecursoQueEnLaUltimaSemanaTiene(CANTIDAD_INTERMEDIA_DE_HORAS));
		this.recursos.add(this.creaRecursoQueEnLaUltimaSemanaTiene(MUCHAS_HORAS));
		this.recursos.add(this.creaRecursoQueEnLaUltimaSemanaTiene(MUCHAS_HORAS));
		
		Priorizacion<Recurso> priorizacion = new PriorizacionSimple(new MenosHorasDeReunionEnUltimaSemana());

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoQueTieneMenosHoras));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoQueTambienTieneMenosHoras));
		Assert.assertTrue(recursosPriorizados.size() == 2);

	}
	@Test
	public void testPriorizacionSimplePorQuienSeEncuentreEnElMismoEdificio(){

		Edificio otroEdificio = Mockito.mock(Edificio.class);
		Edificio otroTercerEdificio = Mockito.mock(Edificio.class);
		
		Recurso unRecursoQueEstaEnElEdificioDeLaReunion = this.creaRecursoQueEsteEn(this.edificioDeReunion);
		Recurso otroRecursoQueTambienEstaEnElEdificioDeLaReunion = this.creaRecursoQueEsteEn(this.edificioDeReunion);
				
		this.recursos.add(unRecursoQueEstaEnElEdificioDeLaReunion);
		this.recursos.add(otroRecursoQueTambienEstaEnElEdificioDeLaReunion);		
		this.recursos.add(this.creaRecursoQueEsteEn(otroEdificio));
		this.recursos.add(this.creaRecursoQueEsteEn(otroEdificio));
		this.recursos.add(this.creaRecursoQueEsteEn(otroTercerEdificio));
		
		Priorizacion<Recurso> priorizacion = new PriorizacionSimple(new QuienSeEncuentreEnElMismoEdificioQueLaReunion());

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoQueEstaEnElEdificioDeLaReunion));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoQueTambienEstaEnElEdificioDeLaReunion));
		Assert.assertTrue(recursosPriorizados.size() == 2);
	}
	@Test
	public void testPriorizacionSimplePorEstado(){
		
		Recurso unRecursoEnEstadoPocasReuniones = this.creaRecursoEnEstado(EstadoDeRecurso.POCAS_REUNIONES);
		Recurso otroRecursoEnEstadoPocasReuniones = this.creaRecursoEnEstado(EstadoDeRecurso.POCAS_REUNIONES);
				
		this.recursos.add(unRecursoEnEstadoPocasReuniones);
		this.recursos.add(otroRecursoEnEstadoPocasReuniones);		
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES));
		
		Priorizacion<Recurso> priorizacion = new PriorizacionSimple(new ComparacionPorEstadoDeRecursos());

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoEnEstadoPocasReuniones));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoEnEstadoPocasReuniones));
		Assert.assertTrue(recursosPriorizados.size() == 2);
		
	}
	
	@Test
	public void testPriorizacionDeRecursosEnEstadoPocasReuniones(){

		Recurso unRecursoEnEstadoPocasReunionesConMayorPrioridad = this.creaRecursoEnEstadoPocasReunionesConMayorPrioridad();
		Recurso otroRecursoEnEstadoPocasReunionesConMayorPrioridad = this.creaRecursoEnEstadoPocasReunionesConMayorPrioridad();
			
		this.recursos.add(unRecursoEnEstadoPocasReunionesConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoPocasReunionesConMayorPrioridad);		
		this.recursos.add(this.creaRecursoEnEstadoPocasReunionesConPrioridadIntermediaEn(this.edificioDeReunion));
		this.recursos.add(this.creaRecursoEnEstadoPocasReunionesConPrioridadIntermediaEn(Mockito.mock(Edificio.class) ));
		this.recursos.add(this.creaRecursoEnEstadoPocasReunionesConMinimaPrioridad());
		
		Priorizacion<Recurso> priorizacion = PriorizacionFactory.getInstance().creaPriorizacionPorEstados();

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoEnEstadoPocasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoEnEstadoPocasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.size() == 2);
				
	}
	@Test
	public void testPriorizacionDeRecursosEnEstadoNormal(){

		Recurso unRecursoEnEstadonNormalConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , POCAS_HORAS, this.edificioDeReunion );
		Recurso otroRecursoEnEstadoNormalConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , POCAS_HORAS, this.edificioDeReunion );
			
		this.recursos.add(unRecursoEnEstadonNormalConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoNormalConMayorPrioridad);		
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , CANTIDAD_INTERMEDIA_DE_HORAS, this.edificioDeReunion ));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , CANTIDAD_INTERMEDIA_DE_HORAS, Mockito.mock(Edificio.class) ));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , MUCHAS_HORAS, this.edificioDeReunion ));
		
		Priorizacion<Recurso> priorizacion = PriorizacionFactory.getInstance().creaPriorizacionPorEstados();

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoEnEstadonNormalConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoEnEstadoNormalConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.size() == 2);
	}
	@Test
	public void testPriorizacionDeRecursosEnEstadoExcesivasReuniones(){

		Recurso unRecursoEnEstadonExcesivasReunionesConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , POCAS_HORAS, this.edificioDeReunion );
		Recurso otroRecursoEnEstadoExcesivasReunionesConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , POCAS_HORAS, this.edificioDeReunion );
			
		this.recursos.add(unRecursoEnEstadonExcesivasReunionesConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoExcesivasReunionesConMayorPrioridad);		
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , CANTIDAD_INTERMEDIA_DE_HORAS, this.edificioDeReunion ));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , CANTIDAD_INTERMEDIA_DE_HORAS, Mockito.mock(Edificio.class) ));
		this.recursos.add(this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , MUCHAS_HORAS, this.edificioDeReunion ));
		
		Priorizacion<Recurso> priorizacion = PriorizacionFactory.getInstance().creaPriorizacionPorEstados();

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoEnEstadonExcesivasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoEnEstadoExcesivasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.size() == 2);
	}

	
	//**
	@Test
	public void testPriorizacionDeConjuntoDeRecursosDeDistintosEstados(){	

		Recurso unRecursoEnEstadoPocasReunionesConMayorPrioridad = this.creaRecursoEnEstadoPocasReunionesConMayorPrioridad();
		Recurso otroRecursoEnEstadoPocasReunionesConMayorPrioridad = this.creaRecursoEnEstadoPocasReunionesConMayorPrioridad();
		Recurso unTercerRecursoEnEstadoPocasReunionesConMayorPrioridad = this.creaRecursoEnEstadoPocasReunionesConMayorPrioridad();

		
		Recurso recursoEnEstadoPocasReunionesConPrioridadIntermedia = this.creaRecursoEnEstadoPocasReunionesConPrioridadIntermediaEn(this.edificioDeReunion);		
		
		Recurso unRecursoEnEstadonNormalConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , POCAS_HORAS, this.edificioDeReunion );
		Recurso otroRecursoEnEstadoNormalConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.NORMAL , POCAS_HORAS, this.edificioDeReunion );
		
		Recurso unRecursoEnEstadonExcesivasReunionesConMayorPrioridad = this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , POCAS_HORAS, this.edificioDeReunion );
		Recurso otroRecursoEnEstadoExcesivasReunionesConPrioridadIntermedia = this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , POCAS_HORAS, Mockito.mock(Edificio.class) );
		Recurso unTercerRecursoEnEstadoExcesivasReunionesConPrioridadIntermedia = this.creaRecursoEnEstado(EstadoDeRecurso.EXCESIVAS_REUNIONES , POCAS_HORAS, Mockito.mock(Edificio.class) );

		
		this.recursos.add(unRecursoEnEstadoPocasReunionesConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoPocasReunionesConMayorPrioridad);	
		this.recursos.add(unTercerRecursoEnEstadoPocasReunionesConMayorPrioridad);
		this.recursos.add(recursoEnEstadoPocasReunionesConPrioridadIntermedia);
		this.recursos.add(unRecursoEnEstadonNormalConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoNormalConMayorPrioridad);
		this.recursos.add(unRecursoEnEstadonExcesivasReunionesConMayorPrioridad);
		this.recursos.add(otroRecursoEnEstadoExcesivasReunionesConPrioridadIntermedia);
		this.recursos.add(unTercerRecursoEnEstadoExcesivasReunionesConPrioridadIntermedia);
		
		
		Priorizacion<Recurso> priorizacion = PriorizacionFactory.getInstance().creaPriorizacionPorEstados();

		Set<Recurso> recursosPriorizados = priorizacion.aplicateSobreLosRecursosParaLaReunion(recursos, reunion);
		
		Assert.assertTrue(recursosPriorizados.contains(unRecursoEnEstadoPocasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.contains(otroRecursoEnEstadoPocasReunionesConMayorPrioridad));
		Assert.assertTrue(recursosPriorizados.contains(unTercerRecursoEnEstadoPocasReunionesConMayorPrioridad));
	
		Assert.assertTrue(recursosPriorizados.size() == 3);

	}
	
	
	
	private Recurso creaRecursoEnEstado(EstadoDeRecurso estado,
										Duration horasDeReunionAcumuladas, Edificio edificio) {
		Recurso recurso = Mockito.mock(Recurso.class);				
		Mockito.when(recurso.getEstado()).thenReturn(estado);
		Mockito.when(recurso.dameElTiempoDeReunionesAcumuladoDesdeHace(A_PARTIR_DE_UNA_SEMANA_ATRAS)).thenReturn(horasDeReunionAcumuladas);
		Mockito.when(recurso.getEdificio()).thenReturn(edificio);
		return recurso;		
	}

	private Recurso creaRecursoEnEstadoPocasReunionesConMinimaPrioridad() {
		Recurso recurso = Mockito.mock(Recurso.class);				
		Mockito.when(recurso.getEstado()).thenReturn(EstadoDeRecurso.POCAS_REUNIONES);
		Mockito.when(recurso.getPrecio()).thenReturn(PRECIO_CARO);
		Mockito.when(recurso.getEdificio()).thenReturn(Mockito.mock(Edificio.class));
		return recurso;		
		}

	private Recurso creaRecursoEnEstadoPocasReunionesConPrioridadIntermediaEn(Edificio unEdificio) {
		Recurso recurso = Mockito.mock(Recurso.class);				
		Mockito.when(recurso.getEstado()).thenReturn(EstadoDeRecurso.POCAS_REUNIONES);
		Mockito.when(recurso.getPrecio()).thenReturn(PRECIO_INTERMEDIO);
		Mockito.when(recurso.getEdificio()).thenReturn(unEdificio);
		return recurso;	
	}

	private Recurso creaRecursoEnEstadoPocasReunionesConMayorPrioridad() {
		Recurso recurso = Mockito.mock(Recurso.class);		
		
		Mockito.when(recurso.getEstado()).thenReturn(EstadoDeRecurso.POCAS_REUNIONES);
		Mockito.when(recurso.getPrecio()).thenReturn(PRECIO_BARATO);
		Mockito.when(recurso.getEdificio()).thenReturn(this.edificioDeReunion);
		return recurso;	
	}

	private Recurso creaRecursoEnEstado(EstadoDeRecurso unEstado) {
		Recurso recurso = Mockito.mock(Recurso.class);		
		Mockito.when(recurso.getEstado()).thenReturn(unEstado);
		return recurso;	
		}

	private Recurso creaRecursoQueEsteEn(Edificio unEdificio) {
		Recurso recurso = Mockito.mock(Recurso.class);		
		Mockito.when(recurso.getEdificio()).thenReturn(unEdificio);
		
		return recurso;

	}

	private Recurso creaRecursoQueEnLaUltimaSemanaTiene(Duration horasDeReunionAcumuladas) {
		
		Recurso recurso = Mockito.mock(Recurso.class);
		Mockito.when(recurso.dameElTiempoDeReunionesAcumuladoDesdeHace(A_PARTIR_DE_UNA_SEMANA_ATRAS)).thenReturn(horasDeReunionAcumuladas);
		
		return recurso;
	}

	private Recurso creaRecursoQueDevuelvaElPrecio(double precioDelRecurso) {
		Recurso recurso = Mockito.mock(Recurso.class);
		Mockito.when(recurso.getPrecio()).thenReturn(precioDelRecurso);
		
		return recurso;
	}
	
	
}
