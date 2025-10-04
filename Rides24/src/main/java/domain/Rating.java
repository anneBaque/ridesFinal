package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rating {
	
	//Atributos//
	
	private static int count;
	@Id
	private int idRating;
	private String email;
	private int idRide;
	private int ratingValue;
	private String mensaje;
	private String emailConductor;
	private String respuesta;
	
	//Metodos//
	
	public Rating(String p_email, int p_idRide, int p_rating, String p_mensaje, String p_con) {
		idRating=count;
		count++;
		email=p_email;
		idRide=p_idRide;
		mensaje=p_mensaje;
		emailConductor = p_con;
		
	}
	
	public Rating(String p_email, int p_idRide, int p_rating, String p_con) {
		idRating=count;
		count++;
		email=p_email;
		idRide=p_idRide;
		mensaje="Sin comentario añadido";
		emailConductor = p_con;
	}
	
	@Override
	public String toString() {
		return "Valoracion por el usuario "+email+": \n Rating: "+ratingValue+"/10 \n Comentarios: " +mensaje;  
	}
	
	public int getIdRating() {
		return idRating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdRide() {
		return idRide;
	}

	public void setIdRide(int idRide) {
		this.idRide = idRide;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	
	

}
