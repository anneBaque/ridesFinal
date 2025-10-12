package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity

public class Passenger implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String email;
	private String name; 
	private String contraseña;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Monedero wallet;
	
	
	
	public Passenger() {
		super();
		wallet=new Monedero();
	}
	
	public Passenger(String email,  String contraseña) {
		super();
		this.email = email;
		this.contraseña = contraseña;
		wallet=new Monedero();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public Monedero getWallet() {
		return wallet;
	}
	
	
	
	public List<Ride> getRides() {
		return rides;
	}

	@Override
	public String toString() {
		return "email=" + email + ", name=" + name + ", rides=" + rides;
	}

	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (email != other.email)
			return false;
		return true;
	}
	
	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && 
				(java.util.Objects.equals(r.getTo(),to)) && 
				(java.util.Objects.equals(r.getDate(),date)) ) {
				found=true;
			}
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	
	
}
