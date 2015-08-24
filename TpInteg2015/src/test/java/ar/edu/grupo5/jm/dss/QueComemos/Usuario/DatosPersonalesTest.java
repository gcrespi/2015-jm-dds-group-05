package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonalesNoValidosException;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

public class DatosPersonalesTest {

	private DatosPersonales datosPersonalesValidos;
	private DatosPersonales sinSexo;

	@Before
	public void setUp() {
		datosPersonalesValidos = new DatosPersonales("Pepito", Sexo.MASCULINO, LocalDate.parse("2000-01-01"));
		sinSexo = new DatosPersonales("Pepito", null, LocalDate.parse("2000-01-01"));
	}

	@Test
	public void sonValidos() {
		assertTrue(datosPersonalesValidos.sonValidos());
	}

	@Test(expected = DatosPersonalesNoValidosException.class)
	public void esNombreCorto() {
		new DatosPersonales("PP", Sexo.MASCULINO, LocalDate.parse("2000-01-01"));
	}

	@Test
	public void noIndicaSexo() {
		assertFalse(sinSexo.indicaSexo());
	}

	@Test(expected = DatosPersonalesNoValidosException.class)
	public void noTieneFechaDeNacimientoAnteriorAHoy() {
		new DatosPersonales("Pepito", Sexo.MASCULINO, LocalDate.now());
	}

}
