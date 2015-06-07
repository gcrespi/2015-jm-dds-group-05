package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class ConsultaVeganoRecetasDificles implements ObservadorConsultas {

	private int cantidadConsultas=0;
	
	@Override
	public void notificar(Usuario unUsuario,
			Collection<Receta> recetasConsultadas) {
		
		if (unUsuario.tieneCondicionDeSalud(new Vegano())) {
			
			Collection<Receta> consultasFiltradas = this.filtrarRecetasDificiles (recetasConsultadas);
	        cantidadConsultas = cantidadConsultas + consultasFiltradas.size(); 
	    }
	}
	
	public Collection<Receta> filtrarRecetasDificiles (Collection<Receta> unasRecetas){
		
		return unasRecetas;
	}

}
