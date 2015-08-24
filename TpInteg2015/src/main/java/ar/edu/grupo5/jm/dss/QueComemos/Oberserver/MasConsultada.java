package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class MasConsultada implements ObservadorConsultas {
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> unasRecetasConsultadas) {
		recetasConsultadas.addAll(unasRecetasConsultadas);
	}

	public Optional<Receta> recetaMasConsultada() {
		return recetasConsultadas.stream().max((unNombre, otroNombre) -> cantidadDeConsultas(unNombre) - cantidadDeConsultas(otroNombre));
	}

	private int cantidadDeConsultas(Receta unaReceta) {
		return Collections.frequency(recetasConsultadas, unaReceta);
	}

	public int cantidadDeConsultasDeRecetaMAsConsultada() {
		Optional<Receta> masConsultada = recetaMasConsultada();
		return (masConsultada.isPresent()) ? Collections.frequency(recetasConsultadas, masConsultada.get()) : 0;
	}
}
