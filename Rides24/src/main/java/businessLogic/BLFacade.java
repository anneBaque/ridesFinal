package businessLogic;

import java.util.Date;
import java.util.List;
import domain.Ride;
import domain.Car;
import domain.Driver;
import domain.Mail;
import domain.Passenger;
import domain.Rating;
import domain.Reservation;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( Ride r, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	public boolean comprobarDriver(String email, String contra);
	
	public boolean comprobarPassenger(String email, String contra);
	
	 public boolean storePassenger(String email, String nombre, String contra);
	 
	 public boolean storeDriver(String email, String nombre, String contra);
	 
	 public Passenger findPassenger(String email);
	 
	 public Driver findDriver(String email);
	 
	 public List<Reservation> getAllReservations(String email);
	 public List<Reservation> getAllReservations();
	 
	 public boolean makeReservation(Reservation res);
	 public boolean acceptReservation(Reservation reserva);
	 public void rejectReservation(Reservation reserva);
	 public List<Ride> acceptedReservation(String email);
	 public List<Ride> rejectedReservation(String email);
	 public boolean eraseReservation(String email, Ride ride);
	 public boolean addRating(Rating rating);
	 public Ride findRide(int numero);
	 public boolean existsReservation(Reservation res);
	 public List<Rating> findRating(String email);
	 public void responseRating(String email, int idRide, String emailConductor, String mensaje);
	 public boolean storeCar(Car coche);
	 public Car findCar(String email);
	 public void sendEmail(Mail mensaje);
	 public boolean tieneCorreos(String emailConductor);
	 public List<Mail> mirarCorreos(String emailConductor);
	 public void leeCorreo(Mail correo);
	 public void anadirDinero(Passenger pasajero, float dineros);
	 public void anadirDinero(Driver conductor, float dineros);
	 public void withdraw(Driver conductor, float dineros);
	 public void pay(Passenger pasajero, float dineros);
}
