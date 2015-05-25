package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class ExcesoDeCalorias extends Filtro {

	public ExcesoDeCalorias(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas,
			Usuario unUsuario) {
		Collection<Receta> recetasParciales = recetas.stream()
				.filter((unaReceta -> unaReceta.getCantCaloriasTotales() < 500))
				.collect(Collectors.toList());

		return subFiltro.filtrarRecetas(recetasParciales, unUsuario);
	}

}
