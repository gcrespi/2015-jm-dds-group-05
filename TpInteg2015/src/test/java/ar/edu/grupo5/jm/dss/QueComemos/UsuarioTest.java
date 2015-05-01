package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
	
	private Usuario leandro;
	private Usuario gustavo;
	private Usuario ramiro;
	private Usuario gaston;
	private Usuario juanchi;
	
	@Before
	public void setUp() {
		gustavo = new Usuario(73, 1.83, null, null, null, null, null, null, null, null);
		leandro = new Usuario(79, 1.78, null, null, null, null, null, null, null, null);
		ramiro = new Usuario(63, 1.75, null, null, null, null, null, null, null, null);
		gaston = new Usuario(65, 1.66, null, null, null, null, null, null, null, null);
		juanchi = new Usuario(70, 1.85, null, null, null, null, null, null, null, null);
	}
	

	@Test
	public void juanchiTieneIMCDe2045(){
		assertEquals(20.45,juanchi.indiceMasaCorporal(),0.01);
	}
	
	@Test
	public void gustavoTieneIMCDe2180(){
		assertEquals(21.80,gustavo.indiceMasaCorporal(),0.01);
		
	}
	
	@Test 
	public void leandroTieneIMCDe2493(){
		assertEquals(24.93,leandro.indiceMasaCorporal(),0.01);
	}
	
	@Test
	public void ramiroTieneIMCDe2057(){
		assertEquals(20.57, ramiro.indiceMasaCorporal(), 0.01);
	}
	
	@Test
	public void gastonTieneIMCDe2358(){
		assertEquals(23.58, gaston.indiceMasaCorporal(), 0.01);
	}
}
