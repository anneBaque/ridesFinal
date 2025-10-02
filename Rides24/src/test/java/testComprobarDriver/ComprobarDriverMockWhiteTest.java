package testComprobarDriver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

public class ComprobarDriverMockWhiteTest {

    static DataAccess sut;
    
    protected MockedStatic<Persistence> persistenceMock;

    @Mock
    protected  EntityManagerFactory entityManagerFactory;
    @Mock
    protected  EntityManager db;
    @Mock
    protected  EntityTransaction  et;
    
    @Mock 
    TypedQuery<Driver> typeQueryDrivers;
    

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
    public void end() {
        persistenceMock.close();
    }

	@Test
	public void test1() {
		System.out.println("Test1");
		List<Ride> viajes;
		String email = "driver";
		sut.open();		
		Mockito.when(db.createQuery("SELECT r FROM Reservation r WHERE r.pasEmail=?1",Reservation.class)).thenReturn(typeQueryReservations);
	    Mockito.when(typeQueryReservations.setParameter(1,pasEmail)).thenReturn(typeQueryReservations);
	    List<Reservation> reservas = new ArrayList<Reservation>();
	    Mockito.when(typeQueryReservations.getResultList()).thenReturn(reservas);
		viajes = sut.acceptedReservation(pasEmail);
		assertTrue(viajes.isEmpty());
		sut.close();	
		
	}



}