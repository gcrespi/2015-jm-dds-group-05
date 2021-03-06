package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public abstract class ConsultorRecetas {
	
	@Id
	@GeneratedValue
	private Long consultorId;
	
	public abstract Collection<Receta> getRecetas(Usuario unUsuario);

}
