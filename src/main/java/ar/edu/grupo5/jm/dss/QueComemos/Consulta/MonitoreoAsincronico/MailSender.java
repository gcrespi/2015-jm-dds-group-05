package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

public interface MailSender {

	public void send(String destiny, String title, String body);

}
