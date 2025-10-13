package testGetRides;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class getRidesBDWhiteTest {
	
static DataAccess sut = new DataAccess();
	
	//Clase con métodos adicionales para ejecutar el test
	static TestDataAccess testDA = new TestDataAccess();
	
	@Test
	public void test1() {
		String from = "Bilbao";
		String to = "Praga";
		Date rideDate = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }
		
		sut.open();
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(viajes.isEmpty());

	}
	
	@Test
	public void test2() {
		String from = "Bilbao";
		String to = "Praga";
		Date rideDate = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }
		sut.open();
		boolean drivAnadido = sut.storeDriver("driEmail", "jesus","111");
		Ride nuevo = new Ride(from, to, rideDate, 5, 10);
		try {
			nuevo = sut.createRide(nuevo, "driEmail");
		} catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(!viajes.isEmpty());
		if(drivAnadido) {
			testDA.open();
			testDA.removeDriver("driEmail");
			testDA.close();
		}
		if(nuevo != null) {
			testDA.open();
			testDA.removeRide2(nuevo.getRideNumber());
			testDA.close();
		}
		sut.close();

	}

}
