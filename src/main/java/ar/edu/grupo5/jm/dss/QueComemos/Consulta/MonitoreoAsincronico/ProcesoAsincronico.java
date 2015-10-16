package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

@Entity
public abstract class ProcesoAsincronico {
	
	@Id
	@GeneratedValue
	private Long ProcesoAsincronicoId;

	public abstract void procesarConsulta(Consulta unaConsulta);

	public void procesarConsultas(Collection<Consulta> unasConsultas) {
		unasConsultas.forEach(unaConsulta -> this.procesarConsulta(unaConsulta));
	}

}
