package testComprobarDriver;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
		String email = "driver";
		sut.open();
		Mockito.when(db.find(Driver.class, email)).thenReturn(null);
		boolean existe = sut.comprobarDriver(email, "123");
		assertTrue(!existe);
		sut.close();
	}
	
	@Test
	public void test2() {
		System.out.println("Test2");
		String email = "driver";
		String contra = "123";
		Driver driver = new Driver(email, email);
		driver.setContraseña(contra);
		sut.open();
		Mockito.when(db.find(Driver.class, email)).thenReturn(driver);
		boolean existe = sut.comprobarDriver(email, "999"); //contraseña incorrecta
		assertTrue(!existe);
		sut.close();
	}

	@Test
	public void test3() {
		System.out.println("Test3");
		String email = "driver";
		String contra = "123";
		Driver driver = new Driver(email, email);
		driver.setContraseña(contra);
		sut.open();
		Mockito.when(db.find(Driver.class, email)).thenReturn(driver);
		boolean existe = sut.comprobarDriver(email, contra);
		assertTrue(existe);
		sut.close();
	}

	

}