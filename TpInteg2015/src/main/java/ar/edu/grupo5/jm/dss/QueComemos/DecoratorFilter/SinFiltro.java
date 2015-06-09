package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class SinFiltro implements IFiltro {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		return recetas;
	}

}