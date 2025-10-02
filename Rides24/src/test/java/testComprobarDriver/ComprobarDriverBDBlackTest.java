package testComprobarDriver;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import testOperations.TestDataAccess;

public class ComprobarDriverBDBlackTest {

	//sut: system under test
	static DataAccess sut = new DataAccess();
	
	//Clase con métodos adicionales para ejecutar el test
	static TestDataAccess testDA = new TestDataAccess();

    boolean existe;
    String email = "driver";
    String contra = "123";

    @Test
    //Driver existe y contraseña correcta
    public void test1() {
        System.out.println("Test1");
        try{
            testDA.open();
            testDA.storeDriver(email, contra);
            testDA.close();

            existe = sut.comprobarDriver(email, contra);
            assertTrue(existe);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeDriver(email);
            testDA.close();
        }            
    }

}