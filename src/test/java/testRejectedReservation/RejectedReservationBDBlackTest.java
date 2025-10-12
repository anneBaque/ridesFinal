package testRejectedReservation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Reservation;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class RejectedReservationBDBlackTest {
	
	//sut: system under test
	static DataAccess sut = new DataAccess();
		
	//Clase con métodos adicionales para ejecutar el test
	static TestDataAccess testDA = new TestDataAccess();
	
	@Test
	//Reservation no está en la base de datos
	public void test1() {
		System.out.println("Test1");
		List<Ride> viajes;
		sut.open();		
		viajes = sut.rejectedReservation("a@gmail.com");
		assertTrue(viajes.isEmpty());
		sut.close();
		
	}
	

	@Test
	//Reservation está en la base de datos, con rechazada=false
	public void test2() {
		System.out.println("Test2");
	    List<Ride> viajes;
	    Date rideDate = null;
	    Ride ride = null;
	    String driEmail = "d1@gmail.com";
	    String pasEmail = "b@gmail.com";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Reservation res = null;
	    String[] args = {"Bilbao", "Praga", driEmail};
	    
	    try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }

	    sut.open(); 
	    
	    boolean anadidoDriver = false;
	    boolean reservaHecha = false;
		Ride ride1 = new Ride("Bilbao", "Praga", rideDate, 5, 10);
	    try {
	        if (sut.storeDriver(driEmail, "Driver1", "prueba")) {
	            anadidoDriver = true;
	        }

	        try {
	            ride = sut.createRide(ride1, driEmail);
	        } catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
	            fail(e.getMessage());
	        }
	        


	        res = new Reservation(ride.getRideNumber(), pasEmail);
	        reservaHecha = sut.makeReservation(res);

	        viajes = sut.rejectedReservation(pasEmail);
	        assertTrue(viajes.isEmpty());
	        
	        

	    } finally {
	    	testDA.open();
	        if (reservaHecha) {
	            testDA.removeReservation(res.getIdRes());
	        }
	        if (ride != null) {
	            testDA.removeRide2(ride.getRideNumber());
	        }
	        if (anadidoDriver) {
	            testDA.removeDriver(driEmail);
	        }

	        testDA.close();
	        sut.close();  
	    }
	}
	
	@Test
	//Reservation está en la base de datos, con rechazada=true
	public void test3() {
		System.out.println("Test3");
	    List<Ride> viajes;
	    Date rideDate = null;
	    Ride ride = null;
	    String driEmail = "d1@gmail.com";
	    String pasEmail = "c@gmail.com";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Reservation res = null;
	    String[] args = {"Bilbao", "Praga", driEmail};
	    
	    try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }

	    sut.open();  
	    
	    boolean anadidoDriver = false;
	    boolean reservaHecha = false;
		Ride ride1 = new Ride("Bilbao", "Praga", rideDate, 5, 10);
	    try {
	        if (sut.storeDriver(driEmail, "Driver1", "prueba")) {
	            anadidoDriver = true;
	        }

	        try {
	            ride = sut.createRide(ride1, driEmail);
	        } catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
	            fail(e.getMessage());
	        }
	        


	        res = new Reservation(ride.getRideNumber(), pasEmail);
	        res.setEstado(false);
	        res.setProcesado(true);
	        reservaHecha = sut.makeReservation(res);

	        viajes = sut.rejectedReservation(pasEmail);
	        assertTrue(!viajes.isEmpty());
	        
	        

	    } finally {
	    	testDA.open();
	        if (reservaHecha) {
	            testDA.removeReservation(res.getIdRes());
	        }
	        if (ride != null) {
	            testDA.removeRide2(ride.getRideNumber());
	        }
	        if (anadidoDriver) {
	            testDA.removeDriver(driEmail);
	        }

	        testDA.close();
	        sut.close();  
	    }
	}
	
	@Test
	//Email es null
	public void test4() {
		System.out.println("Test4");
		List<Ride> viajes;
		sut.open();	
		viajes = sut.rejectedReservation(null);
		assertTrue(viajes.isEmpty());
		sut.close();
		
	}
		
}
