package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class StExcesoCalorias implements StFiltro {
	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return unaReceta.getCantCaloriasTotales() < 500;
	}
}