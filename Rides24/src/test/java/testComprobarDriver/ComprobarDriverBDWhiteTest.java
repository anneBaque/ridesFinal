package testComprobarDriver;

package testComprobarDriver;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import testOperations.TestDataAccess;

public class ComprobarDriverBDWhiteTest {

	//sut: system under test
	static DataAccess sut = new DataAccess();
	
	//Clase con métodos adicionales para ejecutar el test
	static TestDataAccess testDA = new TestDataAccess();

    boolean existe;

    @Test
    //El driver no existe en la base de datos
    public void test1() {
        System.out.println("Test1");
        String email = "fake drvier";
        String contra = "123";
        sut.open();
        existe = sut.comprobarDriver(email, contra);
        sut.close();
        assertTrue(!existe);
    }
    
    @Test
    //El driver existe en la base de datos pero la contraseña es incorrecta
    public void test2() {
        System.out.println("Test2");
        String email = "driver test";
        String contra = "999"; // Contraseña incorrecta
        Driver driver;

        sut.open();
        boolean anadido = sut.storeDriver(email, "driver", "123");
        
        existe = sut.comprobarDriver(email, contra);
        sut.close();
        assertTrue(!existe);

        if(anadido) {
        	testDA.open();
        	testDA.removeDriver(email);
        	testDA.close();
        }
    }

    
    @Test
    //El driver existe en la base de datos y la contraseña es correcta
    public void test3() {
        System.out.println("Test3");
        String email = "driver test";
        String contra = "123";
        Driver driver;

        sut.open();
        boolean anadido = sut.storeDriver(email, "driver", contra);
        
        existe = sut.comprobarDriver(email, contra);
        assertTrue(existe);

        if(anadido) {
        	testDA.open();
        	testDA.removeDriver(email);
        	testDA.close();
        }
        sut.close();
    }
    
}