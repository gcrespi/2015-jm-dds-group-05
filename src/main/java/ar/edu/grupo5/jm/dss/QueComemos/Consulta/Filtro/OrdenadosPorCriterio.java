package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;



import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.uqbarproject.jpa.java8.extras.convert.LambdaConverter;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

@Entity
public class OrdenadosPorCriterio extends PostProcesamiento{

	@Convert(converter = LambdaConverter.class)
	private Comparator<Receta> criterio;
	private String nombreCriterio;
	

	public OrdenadosPorCriterio(Filtro unFiltro, Comparator<Receta> unComparador, String unNombreCriterio) {
		super(unFiltro);
		criterio = unComparador;
		nombreCriterio = unNombreCriterio;
	}

	@Override
	public String getNombre() {
		return "Ordenadas por " + nombreCriterio;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		List<Receta> recetasList = recetas.stream().collect(Collectors.toList());
		Collections.sort(recetasList, criterio);
		return recetasList;
	}

}