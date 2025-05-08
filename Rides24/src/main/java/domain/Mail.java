package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mail {
	@Id
	private int idMail = (int) System.currentTimeMillis();
	private String destinatario;
	private String remitente;
	private String asunto;
	private String mensaje;
	private boolean leido;
	
	private Date fechaEnvio;

	public Mail(String destinatario, String remitente, String asunto, String mensaje) {
		super();
		this.destinatario = destinatario;
		this.remitente = remitente;
		this.asunto = asunto;
		leido = false;
		this.mensaje = mensaje;
		this.fechaEnvio = new Date();
	}

	public int getIdMail() {
		return idMail;
	}

	public void setIdMail(int idMail) {
		this.idMail = idMail;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	
	
	
	
}
