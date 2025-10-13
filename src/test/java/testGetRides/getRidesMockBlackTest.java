package testGetRides;

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
import domain.Ride;


public class getRidesMockBlackTest {
	
static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	
	@Mock 
	TypedQuery<Ride> typeQueryReservations;
	
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
		String from = null;
		String to = "Praga";
		Date rideDate = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,from)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(2,to)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(3,rideDate)).thenReturn(typeQueryReservations);
	    List<Ride> rides = new ArrayList<Ride>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(rides);
		sut.open();
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(viajes.isEmpty());

	}
	
	@Test
	public void test2() {
		String from = "Bilbao";
		String to = null;
		Date rideDate = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
	        rideDate = sdf.parse("14/12/2025");
	    } catch (ParseException e) {
	        fail("Error al parsear la fecha: " + e.getMessage());
	    }
		
		
		sut.open();
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,from)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(2,to)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(3,rideDate)).thenReturn(typeQueryReservations);
	    List<Ride> rides = new ArrayList<Ride>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(rides);
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(viajes.isEmpty());
		sut.close();

	}
	
	@Test
	public void test3() {
		String from = "Bilbao";
		String to = "Praga";
		Date rideDate = null;
		
		sut.open();
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,from)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(2,to)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(3,rideDate)).thenReturn(typeQueryReservations);
	    List<Ride> rides = new ArrayList<Ride>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(rides);
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(viajes.isEmpty());
		sut.close();

	}
	
	@Test
	public void test4() {
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
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,from)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(2,to)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(3,rideDate)).thenReturn(typeQueryReservations);
	    List<Ride> rides = new ArrayList<Ride>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(rides);
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(viajes.isEmpty());
		sut.close();

	}
	
	@Test
	public void test5() {
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
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,from)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(2,to)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(3,rideDate)).thenReturn(typeQueryReservations);
	    List<Ride> rides = new ArrayList<Ride>();
	    rides.add(new Ride(from, to, rideDate, 5,10));
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(rides);
		List<Ride> viajes = sut.getRides(from, to, rideDate);
		assertTrue(!viajes.isEmpty());

		sut.close();

	}

}
