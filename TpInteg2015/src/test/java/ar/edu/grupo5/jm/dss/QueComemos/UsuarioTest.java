package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Rutina;

public class UsuarioTest {

	private Usuario leandro;
	private Usuario gustavo;
	private Usuario ramiro;
	private Usuario gaston;
	private Usuario juanchi;
	private Usuario diabeticoConPesoMenorA70;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);
	
	private Vegano condicionVegano = new Vegano();
	private Celiaco condicionCeliaco = new Celiaco();
	private Diabetico condicionDiabetico = new Diabetico();

	private Collection<String> preferenciaFruta;
	private Collection<String> preferenciasVariadas;

	private Collection<CondicionDeSalud> coleccionCondicionVegano;
	private Collection<CondicionDeSalud> coleccionCondicionCeliaco;
	private Collection<CondicionDeSalud> coleccionCondicionDiabetico;
	private Collection<CondicionDeSalud> condiciones = new ArrayList<CondicionDeSalud>();
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta nuevaEnsaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);
	private Receta choriConChimiMock = mock(Receta.class);

	private Collection<Receta> recetasPublicas = new ArrayList<Receta>();
	private Collection<Receta> recetasGaston = new ArrayList<Receta>();
	private Collection<Receta> recetasJuanchi = new ArrayList<Receta>();// Queda
																		// sin
																		// recetas
	private Collection<Receta> recetasGustavo = new ArrayList<Receta>();

	@Before
	public void setUp() {
		condiciones.add(hippie);
		condiciones.add(corporativo);

		recetasPublicas.add(ensaladaMock);
		recetasPublicas.add(panchoMock);
		recetasGustavo.add(choripanMock);
		recetasGaston.add(panchoMock);

		coleccionCondicionVegano = new ArrayList<CondicionDeSalud>();
		coleccionCondicionVegano.add(condicionVegano);
		coleccionCondicionCeliaco = new ArrayList<CondicionDeSalud>();
		coleccionCondicionCeliaco.add(condicionCeliaco);
		coleccionCondicionDiabetico = new ArrayList<CondicionDeSalud>();
		coleccionCondicionDiabetico.add(condicionDiabetico);
		preferenciaFruta = new ArrayList<String>();
		preferenciaFruta.add("fruta");
		preferenciasVariadas = new ArrayList<String>();
		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("champignones");

		Usuario.setRecetasPublicas(recetasPublicas);
		gustavo = new Usuario(datosPersonalesMock, complexionMock, null,
				recetasGustavo, condiciones, Rutina.MEDIANA);
		// es Vegano con preferencia fruta
		leandro = new Usuario(datosPersonalesMock, complexionMock,
				preferenciaFruta, null, coleccionCondicionVegano,
				Rutina.MEDIANA);
		// Celiaco
		ramiro = new Usuario(datosPersonalesMock, complexionMock, null, null,
				coleccionCondicionCeliaco, Rutina.MEDIANA);
		// No tiene Rutina
		gaston = new Usuario(datosPersonalesMock, complexionMock, null,
				recetasGaston, null, null);
		// Tiene rutina ALTA y es Diabetico
		juanchi = new Usuario(datosPersonalesMock, complexionMock, null,
				recetasJuanchi, coleccionCondicionDiabetico, Rutina.ALTA);
		diabeticoConPesoMenorA70 = new Usuario(datosPersonalesMock,
				complexionMock, null, recetasJuanchi,
				coleccionCondicionDiabetico, Rutina.MEDIANA);

	}

	// Test de Validez de usuario
	@Test
	public void gustavoEsValido() {
		when(complexionMock.esComplexionValida()).thenReturn(true);
		when(datosPersonalesMock.sonValidos()).thenReturn(true);
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.esUsuarioValido()); // XXX pensar si no podria
												// refactorizarse para que no se
												// puedan crear usuarios
												// invalidos


		verify(complexionMock, times(1)).esComplexionValida();
		verify(hippie, times(1)).esUsuarioValido(any(Usuario.class));
		verify(corporativo, times(1)).esUsuarioValido(any(Usuario.class));
	}

	@Test
	public void noEsValidoSiSusDatosPersonalesNoLoSon() {
		when(datosPersonalesMock.sonValidos()).thenReturn(false);

		assertFalse(gustavo.esUsuarioValido());

		verify(datosPersonalesMock, times(1)).sonValidos();
	}

	@Test
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(false);

		assertFalse(gustavo.esUsuarioValidoParaSusCondiciones());

		verify(hippie, times(1)).esUsuarioValido(any(Usuario.class));
		verify(corporativo, times(1)).esUsuarioValido(any(Usuario.class));
	}

	@Test
	public void siNoTieneRutinaEsInvalidos() {
		assertFalse(gaston.esUsuarioValido());
	}

	// Test de Rutina saludable
	@Test
	public void sigueRutinaSaludableConCondiciones() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.sigueRutinaSaludable());

		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void sigueRutinaSaludableSinCondiciones() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		
		assertTrue(gaston.sigueRutinaSaludable());
	}

	@Test
	public void noSigueRutinaSaludableSiNoSubsanaUnaCondicion() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class)))
				.thenReturn(false);
		
		assertFalse(gustavo.sigueRutinaSaludable());


		verify(complexionMock, times(2)).indiceMasaCorporal();
		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMenorA18() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(15.0);
		
		assertFalse(gaston.sigueRutinaSaludable());
		

		verify(complexionMock, times(1)).indiceMasaCorporal();
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMayorA30() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(35.0);
		
		assertFalse(gaston.sigueRutinaSaludable());
		

		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	public void tieneAlgunaDeEstasPreferenciasTest() {
		assertTrue(leandro.tieneAlgunaDeEstasPreferencias(preferenciasVariadas));
	}

	@Test
	public void usuarioVeganoPrefiereFruta() {
		assertTrue(leandro.tienePreferencia("fruta"));
	}

	@Test
	public void veganoQueLeGustanLAsFrutas() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		
		assertTrue(leandro.sigueRutinaSaludable());
		

		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	@Test
	public void celiacoCumpleSiCumpleIMCD() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		
		assertTrue(ramiro.sigueRutinaSaludable());
		

		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	@Test
	public void diabeticoCumpleIMCPesaMenosDe70() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		
		assertTrue(diabeticoConPesoMenorA70.sigueRutinaSaludable());

		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	@Test
	public void diabeticoCumpleIMCConRutinaAlta() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		when(complexionMock.getPeso()).thenReturn(75.0);
		
		assertTrue(juanchi.sigueRutinaSaludable());
		
		verify(complexionMock, times(1)).getPeso();
		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	// Tests Creacion de Recetas
	@Test
	public void gustavoCreaRecetaExitosa() {
		when(recetaMock.esValida()).thenReturn(true);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
	}

	@Test
	public void gustavoCreaRecetaConSubrecetas() {
		when(recetaMock.esValida()).thenReturn(true);

		gustavo.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(choripanMock, ensaladaMock));

		assertTrue(gustavo.esRecetaPropia(recetaMock));
		verify(recetaMock, times(1)).esValida();
	}

	@Test(expected = RecetaNoValidaException.class)
	public void usuarioCreaRecetaFallida() {
		when(recetaMock.esValida()).thenReturn(false);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
	}

	@Test
	public void accesoARecetas() {
		assertTrue(gustavo.puedeAcceder(choripanMock));
		assertTrue(gustavo.puedeAcceder(ensaladaMock));
		assertFalse(gaston.puedeAcceder(choripanMock));
	}

	// Tests Modificacion de Recetas

	@Test
	public void juanchiModificaRecetaPublica() {
		assertTrue(juanchi.getRecetasPropias().isEmpty());
		when(nuevaEnsaladaMock.esValida()).thenReturn(true);

		juanchi.modificarReceta(ensaladaMock, nuevaEnsaladaMock);

		assertTrue(juanchi.getRecetasPropias().contains(nuevaEnsaladaMock));
		verify(nuevaEnsaladaMock, times(1)).esValida();
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		gaston.modificarReceta(choripanMock, choriConChimiMock);

	}

	@Test
	public void gustavoModificaRecetaPropia() {
		when(choriConChimiMock.esValida()).thenReturn(true);

		gustavo.modificarReceta(choripanMock, choriConChimiMock);

		assertTrue(gustavo.getRecetasPropias().contains(choriConChimiMock));
		assertFalse(gustavo.getRecetasPropias().contains(choripanMock));
		verify(choriConChimiMock, times(1)).esValida();
	}

	// Test Eliminar Recetas Propias
	@Test
	public void eliminarUnaRecetaPrivada() {
		assertFalse(gustavo.getRecetasPropias().isEmpty());

		gustavo.eliminarRecetaPropia(choripanMock);

		assertTrue(gustavo.getRecetasPropias().isEmpty());
	}

}
