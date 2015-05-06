package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;

public class Vegano implements CondicionPreexistente {
	
	private static final Collection<String> preferenciasProhibidas = 
											Arrays.asList("pollo", "chori", "carne", "chivito"); 
			
			
	/**
	 * @return the preferenciasprohibidas
	 */
	public static Collection<String> getPreferenciasprohibidas() {
		return preferenciasProhibidas;
	}

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tienePreferencia("fruta");
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tenesAlgunIngredienteDeEstos(preferenciasProhibidas);
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return !(unUsuario.tieneAlgunaDeEstasPreferencias(getPreferenciasprohibidas()));
	}

}
