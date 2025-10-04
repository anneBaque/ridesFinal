package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Car;
import domain.Driver;
import domain.Mail;
import domain.Passenger;
import domain.Rating;
import domain.Reservation;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;
import java.util.logging.Logger;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	Logger logger=Logger.getLogger(getClass().getName());

	public BLFacadeImplementation()  {
		logger.info("Creating BLFacadeImplementation instance");
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
		dbManager.close();
		return ride;
   }
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

    
    public boolean comprobarDriver(String email, String contra) {
    	dbManager.open();
    	boolean correct =  dbManager.comprobarDriver(email, contra);
    	dbManager.close();
    	return correct;
    }
    
    public boolean comprobarPassenger(String email, String contra) {
    	dbManager.open();
    	boolean correct =  dbManager.comprobarPassenger(email, contra);
    	dbManager.close();
    	return correct;
    }
    
    public boolean storePassenger(String email, String nombre, String contra) {
    	dbManager.open();
    	boolean anadido = dbManager.storePassenger(email, nombre, contra);
    	dbManager.close();
    	return anadido;
    }
    
    public boolean storeDriver(String email, String nombre, String contra) {
    	dbManager.open();
    	boolean añadido= dbManager.storeDriver(email, nombre, contra);
    	dbManager.close();
    	return añadido;
    }
    
    public List<Ride> viajesPasajero(String email){
    	dbManager.open();
    	List<Ride> viajes = dbManager.viajesPasajero(email);
    	dbManager.close();
    	return viajes;
    }
    
    public Passenger findPassenger(String email) {
    	dbManager.open();
    	Passenger pasajero = dbManager.findPassenger(email);
    	dbManager.close();
    	return pasajero;
    }
    
    public Driver findDriver(String email) {
    	dbManager.open();
    	Driver conductor = dbManager.findDriver(email);
    	dbManager.close();
    	return conductor;
    }
    
    public boolean makeReservation(Reservation res) {
    	dbManager.open();
    	boolean ola = dbManager.makeReservation(res);
    	dbManager.close();
    	return ola;
    }
    
    public List<Reservation> getAllReservations(String email){
    	dbManager.open();
    	List<Reservation> olawenodia = dbManager.getAllReservations(email);
    	dbManager.close();
    	return olawenodia;
    }

    public List<Reservation> getAllReservations(){
    	dbManager.open();
    	List<Reservation> olawenodia = dbManager.getAllReservations();
    	dbManager.close();
    	return olawenodia;
    }
    
    public boolean acceptReservation(Reservation reserva) {
    	dbManager.open();
    	boolean aceptar = dbManager.acceptReservation(reserva);
    	dbManager.close();
    	return aceptar;
    }
    
    public void rejectReservation(Reservation reserva) {
    	dbManager.open();
    	dbManager.rejectReservation(reserva);
    	dbManager.close();
    }
    public List<Ride> acceptedReservation(String email){
    	dbManager.open();
    	List<Ride> viajes = dbManager.acceptedReservation(email);
    	dbManager.close();
    	return viajes;
    }
    
    public List<Ride> rejectedReservation(String email){
    	dbManager.open();
    	List<Ride> viajes = dbManager.rejectedReservation(email);
    	dbManager.close();
    	return viajes;
    }
    
    public boolean eraseReservation(String email, Ride ride){
    	dbManager.open();
    	boolean chorizo = dbManager.eraseReservation(email, ride);
    	dbManager.close();
    	return chorizo;
    }
    
    public boolean addRating(Rating rating) {
    	dbManager.open();
    	boolean morsilla = dbManager.addRating(rating);
    	dbManager.close();
    	return morsilla;
    }
    
    public Ride findRide(int numero) {
    	dbManager.open();
    	Ride ride = dbManager.findRide(numero);
    	dbManager.open();
    	return ride;
    }
    public boolean existsReservation(Reservation res) {
    	dbManager.open();
    	boolean esta = dbManager.existsReservation(res);
    	dbManager.close();
    	return esta;
    }
    public List<Rating> findRating(String email){
    	dbManager.open();
    	List<Rating> lista = dbManager.findRating(email);
    	dbManager.close();
    	return lista;
    }
    public void responseRating(String email, int idRide, String emailConductor, String mensaje) {
    	dbManager.open();
    	dbManager.responseRating(email, idRide, emailConductor, mensaje);
    	dbManager.close();
    }
    
    public boolean storeCar(Car coche) {
    	dbManager.open();
    	boolean añadido= dbManager.storeCar(coche);
    	dbManager.close();
    	return añadido;
    }
    public Car findCar(String email) {
    	dbManager.open();
    	Car coche = dbManager.findCar(email);
    	dbManager.close();
    	return coche;
    }
    
    public void sendEmail(Mail mensaje) {
    	dbManager.open();
    	dbManager.sendEmail(mensaje);
    	dbManager.close();
    }
    
    public boolean tieneCorreos(String emailConductor) {
    	dbManager.open();
    	boolean tiene = dbManager.tieneCorreos(emailConductor);
    	dbManager.close();
    	return tiene;
    }
    
    public List<Mail> mirarCorreos(String emailConductor){
    	dbManager.open();
    	List<Mail> correos = dbManager.mirarCorreos(emailConductor);
    	dbManager.close();
    	return correos;
    }
    public void leeCorreo(Mail correo) {
    	dbManager.open();
    	dbManager.leeCorreo(correo);
    	dbManager.close();
    }
    public void anadirDinero(Passenger pasajero, float dineros) {
    	dbManager.open();
    	dbManager.añadirDinero(pasajero, dineros);
    	dbManager.close();
    }
    
    public void anadirDinero(Driver conductor, float dineros) {
    	dbManager.open();
    	dbManager.añadirDinero(conductor, dineros);
    	dbManager.close();
    }
    
    public void withdraw(Driver conductor, float dineros){
    	dbManager.open();
    	dbManager.withdraw(conductor, dineros);
    	dbManager.close();
    }
    public void pay(Passenger pasajero, float dineros) {
    	dbManager.open();
    	dbManager.pay(pasajero, dineros);
    	dbManager.close();
    }
}

