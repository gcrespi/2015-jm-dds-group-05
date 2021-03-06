package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class EnviarConsultaPorMail extends ProcesoAsincronico {

	@ManyToMany
	private Collection<Usuario> usuariosPorLosQueSeMandanMail = new ArrayList<Usuario>();
	@Transient 
	private MailSender mailSender;//TODO revisar ver como se puede persistir

	public EnviarConsultaPorMail(Collection<Usuario> usuariosConOpcionMandarMail, MailSender mailSender) {
		usuariosPorLosQueSeMandanMail = usuariosConOpcionMandarMail;
		this.mailSender = mailSender;
	}

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		if (usuariosPorLosQueSeMandanMail.contains(unaConsulta.getUsuario())) {
			this.enviarMail(unaConsulta);
		}
	}

	public void enviarMail(Consulta unaConsulta) {
		String destinatario = unaConsulta.getDestinatario();
		String titulo = "Has realizado una nueva consulta en nuestro sistema!";

		String cuerpo = "Estimado " + unaConsulta.getNombre() + ":\n"
				+ "Hemos registrado que has realizado una nueva consulta de recetas en nuestra aplicación de recetas sociales Qué Comemos?\n"
				+ "Los parámetros de tu busqueda fueron: \n" + unaConsulta.parametrosDeBusquedaToString() + "\n"
				+ "La cantidad de resultados arrojados fue de: " + unaConsulta.cantidadConsultadas() + "\n"
				+ "Para desuscribirse del envío de mails por consultas hágalo desde los seteos de usuario en nuestra aplicación\n"
				+ "Saludos! ;)";

		mailSender.send(destinatario, titulo, cuerpo);
	}

}
