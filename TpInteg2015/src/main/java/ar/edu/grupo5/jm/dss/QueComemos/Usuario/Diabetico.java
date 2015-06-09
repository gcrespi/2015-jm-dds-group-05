package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class Diabetico extends CondicionDeSalud {
	public static Condimentacion condimentoProhibido = new Condimentacion("Azucar", 100);

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.getPeso() < 70 || unUsuario.tieneRutinaActiva();
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tenesMasDe(condimentoProhibido);
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return unUsuario.indicaSexo() && unUsuario.tieneAlgunaPreferencia();
	}

	public static Condimentacion GetCondimentosProhibidos() {
		return condimentoProhibido;
	}

	@Override
	public boolean deboNotificar() {
		return false;
	}

}
