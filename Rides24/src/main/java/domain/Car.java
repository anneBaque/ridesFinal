package domain;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Car {
	@Id
	String matricula;
	
	String marca;
	String modelo;
	int kilometraje;
	String emailConductor;
	String ruta;
	
	public Car(String matricula, String marca, String modelo, int kilometraje, String emailConductor, String ruta) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.kilometraje = kilometraje;
		this.emailConductor = emailConductor;
		this.ruta = ruta;
	}
	
	public Car(String matricula, String marca, String modelo, int kilometraje, String emailConductor) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.kilometraje = kilometraje;
		this.emailConductor = emailConductor;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(int kilometraje) {
		this.kilometraje = kilometraje;
	}
	public String getEmailConductor() {
		return emailConductor;
	}
	public void setEmailConductor(String emailConductor) {
		this.emailConductor = emailConductor;
	}
	public String getFoto() {
		return ruta;
	}
	public void setFoto(File ruta) {
		this.ruta = ruta.getName();
	}
	
	

}
