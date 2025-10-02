package testComprobarDriver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
            sut.open();
            sut.storeDriver(email, "driver", contra);
            
            existe = sut.comprobarDriver(email, contra);
            assertTrue(existe);
            sut.close();

        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeDriver(email);
            testDA.close();
        }            
    }

    @Test
    //Driver existe y contraseña incorrecta
    public void test2() {
        System.out.println("Test2");
        try{
        	sut.open();
            sut.storeDriver(email, "driver", contra);
           
            existe = sut.comprobarDriver(email, "999"); // Contraseña incorrecta
            assertTrue(!existe);
            sut.close();

        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            testDA.removeDriver(email);
            testDA.close();
        }
    }
    
    @Test
    //email = null
    public void test3() {
    
        System.out.println("Test3");
        try{
            sut.open();
            existe = sut.comprobarDriver(null, contra);
            assertTrue(!existe);
            sut.close();
        } catch (IllegalArgumentException e) {
        	assertTrue(!existe);
        } catch (Exception e) {
            fail(); 
        }
   
    }
    
    @Test
    //contraseña = null
    public void test4() {
        System.out.println("Test4");
        try{
            sut.open();
            existe = sut.comprobarDriver(email, null);
            assertTrue(!existe);
            sut.close();
            
        } catch (Exception e) { 
            fail(); 
        }
    }

}