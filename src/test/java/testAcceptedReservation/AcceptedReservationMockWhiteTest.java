package testAcceptedReservation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Reservation;
import domain.Ride;

public class AcceptedReservationMockWhiteTest {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	@Mock 
	TypedQuery<Reservation> typeQueryReservations;
	

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
	@Test
	public void test1() {
		System.out.println("Test1");
		List<Ride> viajes;
		String pasEmail = "letraA@gmail.com";
		sut.open();		
		Mockito.when(db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1",Reservation.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,pasEmail)).thenReturn(typeQueryReservations);
	    List<Reservation> reservas = new ArrayList<Reservation>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(reservas);
		viajes = sut.acceptedReservation(pasEmail);
		assertTrue(viajes.isEmpty());
		sut.close();	
		
	}
	
	@Test
	public void test2() {
		System.out.println("Test2");
	    List<Ride> viajes;
	    Date rideDate = null;
	    Ride ride = null;
	    String driEmail = "DriverPrueba";
	    String pasEmail = "letraB@gmail.com";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Reservation res = null;
	    
	    try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }

	    sut.open(); 
	    Driver driver = new Driver(driEmail, "abcd");
	    Mockito.when(db.find(Driver.class, driEmail)).thenReturn(driver);
	    ride = new Ride("Bilbao", "Praga", rideDate, 4, 21 , driver);
	    ride.setRideNumber(2);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
	    res = new Reservation(ride.getRideNumber(), pasEmail);
	    Mockito.when(db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1",Reservation.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,pasEmail)).thenReturn(typeQueryReservations);
	    List<Reservation> reservas = new ArrayList<Reservation>();
	    reservas.add(res);
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(reservas);
	    viajes = sut.acceptedReservation(pasEmail);
	    assertTrue(viajes.isEmpty());
	    sut.close();  
	}
	
	@Test
	public void test3() {
		System.out.println("Test3");
	    List<Ride> viajes;
	    Date rideDate = null;
	    Ride ride = null;
	    String driEmail = "DriverPrueba";
	    String pasEmail = "letraC@gmail.com";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Reservation res = null;
	    
	    try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }

	    sut.open(); 
	    Driver driver = new Driver(driEmail, "abcd");
	    Mockito.when(db.find(Driver.class, driEmail)).thenReturn(driver);
	    ride = new Ride("Bilbao", "Praga", rideDate, 4, 21 , driver);
	    ride.setRideNumber(2);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
	    res = new Reservation(ride.getRideNumber(), pasEmail);
	    res.setEstado(true);
	    Mockito.when(db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1",Reservation.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,pasEmail)).thenReturn(typeQueryReservations);
	    List<Reservation> reservas = new ArrayList<Reservation>();
	    reservas.add(res);
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(reservas);
	    viajes = sut.acceptedReservation(pasEmail);
	    assertTrue(viajes.isEmpty());
	    sut.close();  

	}
	/*
	@Test
	public void test4() {
		System.out.println("Test4");
	    List<Ride> viajes;
	    Date rideDate = null;
	    Ride ride = null;
	    String driEmail = "DriverPrueba";
	    String pasEmail = "letraD@gmail.com";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Reservation res = null;
	    
	    try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }

	    sut.open(); 
	    Driver driver = new Driver(driEmail, "abcd");
	    Mockito.when(db.find(Driver.class, driEmail)).thenReturn(driver);
	    ride = new Ride("Bilbao", "Praga", rideDate, 4, 21 , driver);
	    ride.setRideNumber(2);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
	    res = new Reservation(ride.getRideNumber(), pasEmail);
	    res.setEstado(true);
	    res.setProcesado(true);
	    Mockito.when(db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1",Reservation.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,pasEmail)).thenReturn(typeQueryReservations);
	    List<Reservation> reservas = new ArrayList<Reservation>();
	    reservas.add(res);
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(reservas);
	    viajes = sut.acceptedReservation(pasEmail);
	    assertTrue(!viajes.isEmpty());
	    sut.close();  

	}
	*/
	
	
	
}
