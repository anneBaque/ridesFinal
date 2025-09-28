package dataAccess;

import java.io.File;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.Mail;
import domain.Passenger;
import domain.Rating;
import domain.Ride;
import domain.Transaction;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import domain.Reservation;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
	private String queryreservaEmail = "SELECT r FROM Reservation r WHERE r.pasEmail=?1";


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();
			try {
				Files.delete(Path.of(fileName));
				System.out.println("File deleted");
			}catch(NoSuchFileException e) {
				System.out.println("No existe: "+ e.getMessage());
			}catch(DirectoryNotEmptyException e) {
				System.out.println("Directorio no vacío: "+ e.getMessage());
			}catch (IOException e) {
				System.out.println("Error al borrar: "+ e.getMessage());
			}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("upena009@ikasle.ehu.eus","Urtzi Peña");
			driver1.setContraseña("a");
			

			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			

			
						
			db.persist(driver1);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of
	 *  a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	
	
	public boolean comprobarDriver(String email, String contra) {
		Driver user = db.find(Driver.class, email);
		if (user== null || !user.getContraseña().equals(contra)) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public boolean comprobarPassenger(String email, String contra) {
		Passenger user = db.find(Passenger.class, email);
		if (user== null || !user.getContraseña().equals(contra)) {
			return false;
		}else {
			return true;
		}
		
	}
	
	
	public List<Ride> viajesPasajero(String email){
		Passenger user = db.find(Passenger.class, email);
		return user.getRides();
	}

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public boolean storePassenger(String email, String nombre, String contraseña) {
		boolean añadido=false;
		db.getTransaction().begin();
		Passenger existe = db.find(Passenger.class, email);
		if (existe == null) {
		Passenger nuevo = new Passenger(email, contraseña);
		nuevo.setName(nombre);
		db.persist(nuevo);
		añadido=true;
		}
		db.getTransaction().commit();
		return añadido;
	}
	
	public boolean storeDriver(String email, String nombre, String contraseña) {
		db.getTransaction().begin();
		boolean añadido= false;
		Driver existe = 	db.find(Driver.class, email);	
		if (existe == null) {
			Driver nuevo =  new Driver(email, nombre);
			nuevo.setContraseña(contraseña);
			db.persist(nuevo);
			añadido = true;
		}
		db.getTransaction().commit();
		return añadido;
	}
	
	public Passenger findPassenger(String email) {
		return db.find(Passenger.class, email);
	}
	
	public Driver findDriver(String email) {
		return db.find(Driver.class, email);
	}


	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public boolean makeReservation(Reservation res) {
		db.getTransaction().begin();
		boolean añadido = false;		
		Reservation existe = db.find(Reservation.class, res.getIdRes());
		if (existe == null) {
		db.persist(res);
		añadido=true;
		}
		db.getTransaction().commit();
		return añadido;
	}
	
	public List<Reservation> getAllReservations(String email) {
		List<Reservation> res = new ArrayList<>();	
		TypedQuery<Reservation> query = db.createQuery(queryreservaEmail,Reservation.class);   
		query.setParameter(1, email);
		List<Reservation> reservations = query.getResultList();
	 	 for (Reservation ride:reservations){
		   res.add(ride);
		  }
	 	return res;
	}
	
	public List<Reservation> getAllReservations() {
		List<Reservation> res = new ArrayList<>();	
		TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r",Reservation.class);   
		List<Reservation> reservations = query.getResultList();
	 	 for (Reservation ride:reservations){
		   res.add(ride);
		  }
	 	return res;
	}
	
	public boolean acceptReservation(Reservation reserva) {
			TypedQuery<Ride> query= db.createQuery("SELECT r FROM Ride r WHERE r.rideNumber=?1",Ride.class);   
			query.setParameter(1, reserva.getIdRide());
			Reservation actu = db.find(Reservation.class, reserva.getIdRes());
			Ride viaje = query.getSingleResult();
			db.getTransaction().begin();

			boolean reservado = true;
			actu.setProcesado(true);

			if (viaje==null || viaje.getPlazasOcupadas() == viaje.getnPlaces()) {
			 System.out.println(reserva.getIdRide() + " no está en la base de datos");
			reservado = false;
			}else {
			viaje.setPlazasOcupadas(1+viaje.getPlazasOcupadas());;
			actu.setEstado(true);
			 System.out.println(viaje + " ha sido actualizado");
			}
			db.getTransaction().commit();
			return reservado;
		}
	
	public void rejectReservation(Reservation reserva) {

		Reservation actu = db.find(Reservation.class, reserva.getIdRes());
		db.getTransaction().begin();

		actu.setProcesado(true);
		actu.setEstado(false);
		System.out.println(actu + " ha sido actualizado");
		db.getTransaction().commit();
	}
	
	public List<Ride> acceptedReservation(String email) {
		db.getTransaction().begin();
	    db.getTransaction().commit();
		List<Reservation> res = new ArrayList<>();	
		TypedQuery<Reservation> query = db.createQuery(queryreservaEmail,Reservation.class);   
		query.setParameter(1, email);
		List<Reservation> reservations = query.getResultList();
		List<Ride> viajes = new ArrayList<>();
		for (Reservation aux: reservations) {
			if(aux.isEstado() && aux.isProcesado()) {
				Ride ride = db.find(Ride.class, aux.getIdRide());
				viajes.add(ride);
			}
		}
		
		return viajes;
		
	}
	
	public List<Ride> rejectedReservation(String email) {
		List<Reservation> res = new ArrayList<>();	
		TypedQuery<Reservation> query = db.createQuery(queryreservaEmail,Reservation.class);   
		query.setParameter(1, email);
		List<Reservation> reservations = query.getResultList();
		List<Ride> viajes = new ArrayList<>();
		for (Reservation aux: reservations) {
			if(!aux.isEstado() && aux.isProcesado()) {
				Ride ride = db.find(Ride.class, aux.getIdRide());
				viajes.add(ride);
			}
		}
		
		return viajes;
		
	}
	
	public boolean eraseReservation(String email, Ride ride) {
		boolean res=false;
		db.getTransaction().begin();
		TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1 AND r.idRide=?2 ",Reservation.class);   
		query.setParameter(1, email);
		query.setParameter(2, ride.getRideNumber());
		Reservation auxiliar = query.getSingleResult();
		if(auxiliar!=null) {
			db.remove(auxiliar);
			
			Ride sarakatumba = db.find(Ride.class, ride.getRideNumber());
			sarakatumba.setPlazasOcupadas(sarakatumba.getPlazasOcupadas()-1);
			res=true;
		}
		db.getTransaction().commit();
		return res;
		
	}
	
	public boolean addRating(Rating rating) {
		db.getTransaction().begin();
		boolean añadido = false;
		TypedQuery<Rating> query = db.createQuery("SELECT r FROM Rating r WHERE r.email=?1 AND r.idRide=?2", Rating.class);
		query.setParameter(1, rating.getEmail());
		query.setParameter(2,rating.getIdRide());
		List<Rating> lista = query.getResultList();
		if (lista.isEmpty()) {
		db.persist(rating);
		System.out.println("COCALAAAAU");
		añadido=true;
		}
		db.getTransaction().commit();
		return añadido;
	}
	public Ride findRide(int numero) {
		return db.find(Ride.class, numero);
		
	}
	
	public boolean existsReservation(Reservation res) {
		boolean existe = true;
		TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1 AND r.idRide=?2",Reservation.class);   
		query.setParameter(1, res.getPasEmail());
		query.setParameter(2, res.getIdRide());
		List<Reservation> lista = query.getResultList();
		if(lista.isEmpty()) {
			existe = false; 
		}
		return existe;
	}
	
	//Encontrar las opiniones sobre el conductor
	public List<Rating> findRating(String email){
		TypedQuery<Rating> query = db.createQuery("SELECT r FROM Rating r WHERE r.emailConductor=?1", Rating.class);
		query.setParameter(1, email);
		List<Rating> lista = query.getResultList();
		return lista;
	}
	
	public void responseRating(String email, int idRide, String emailConductor, String mensaje) {
		db.getTransaction().begin();
		TypedQuery<Rating> query = db.createQuery("SELECT r FROM Rating r WHERE r.email=?1 AND r.idRide=?2 AND r.emailConductor=?3", Rating.class);
		query.setParameter(1, email);
		query.setParameter(2, idRide);
		query.setParameter(3, emailConductor);
		Rating rating = query.getSingleResult();
		rating.setRespuesta(mensaje);
		db.getTransaction().commit();
		
		
	}
	
	public boolean storeCar(Car coche) {
		boolean añadido=false;
		db.getTransaction().begin();
		TypedQuery<Car> query =db.createQuery("SELECT c FROM Car c WHERE c.emailConductor=?1", Car.class);
		query.setParameter(1, coche.getEmailConductor());
		List<Car> existe = query.getResultList();
		if (existe.isEmpty()) {
		db.persist(coche);
		añadido=true;
		}
		db.getTransaction().commit();
		return añadido;
	}
	
	public Car findCar(String email) {
		TypedQuery<Car> query =db.createQuery("SELECT c FROM Car c WHERE c.emailConductor=?1", Car.class);
		query.setParameter(1, email);
		Car coche = query.getSingleResult();
		return coche;
	}
	
	public void sendEmail(Mail mensaje) {
		db.getTransaction().begin();
		db.persist(mensaje);
		System.out.println("Se ha guardado");
		db.getTransaction().commit();
	}
	
	public boolean tieneCorreos(String emailConductor) {
		boolean tiene = true;
		TypedQuery<Mail> query =db.createQuery("SELECT m FROM Mail m WHERE m.destinatario=?1 AND m.leido=?2", Mail.class);
		query.setParameter(1, emailConductor);
		query.setParameter(2, false);
		List<Mail> existe = query.getResultList();
		if(existe.isEmpty()) {
			tiene = false;
		}
		return tiene;
		
		
	}
	
	public List<Mail> mirarCorreos(String emailConductor){
		TypedQuery<Mail> query =db.createQuery("SELECT m FROM Mail m WHERE m.destinatario=?1", Mail.class);
		query.setParameter(1, emailConductor);
		List<Mail> correos = query.getResultList();
		return correos;
	}
	
	public void leeCorreo(Mail correo) {
		db.getTransaction().begin();
		Mail mezua = db.find(Mail.class, correo.getIdMail());
		mezua.setLeido(true);
		db.getTransaction().commit();
	}
	
	public void añadirDinero(Passenger pasajero, float dineros) {
		db.getTransaction().begin();
		Passenger intxaurra = db.find(Passenger.class, pasajero.getEmail());
		intxaurra.getWallet().addCash(dineros);
		db.getTransaction().commit();
	}
	
	public void añadirDinero(Driver conductor, float dineros) {
		db.getTransaction().begin();
		Driver hurra = db.find(Driver.class, conductor.getEmail());
		hurra.getWallet().addCash(dineros);
		db.getTransaction().commit();
	}
	
	public void withdraw(Driver conductor, float dineros) {
		db.getTransaction().begin();
		Driver hurra = db.find(Driver.class, conductor.getEmail());
		hurra.getWallet().withdraw(dineros);
		db.getTransaction().commit();
	}
	
	public void pay(Passenger pasajero, float dineros) {
		db.getTransaction().begin();
		Passenger hurra = db.find(Passenger.class, pasajero.getEmail());
		hurra.getWallet().withdraw(dineros);
		db.getTransaction().commit();
	}
	
}
	

