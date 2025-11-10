package gui;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import businessLogic.BLFactory;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
		
		setUpLocale(c);

		/*
	    if(c.isDatabaseInitialized()) {
			cleanOldDbImages(c);
		}*/
	    
		try {
			setUILookAndFeel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isLocal = c.isBusinessLogicLocal();
		BLFacade appFacadeInterface = new BLFactory().getBusinessLogicFactory(isLocal);
		MainGUI.setBussinessLogic(appFacadeInterface);
		
	    Driver driver=new Driver("driver3@gmail.com","Test Driver");
		MainGUI a=new MainGUI(driver);
		a.setVisible(true);
		
		//initializeBusinessLogic(c, a);

	}
	
	public static void setUpLocale(ConfigXML config) {
		System.out.println(config.getLocale());
		Locale.setDefault(new Locale(config.getLocale()));
		System.out.println("Locale: "+Locale.getDefault());
	}
	
	public static void cleanOldDbImages(ConfigXML config) {
	    Path carpeta = Paths.get("imagenes");

	    if (!Files.exists(carpeta) || !Files.isDirectory(carpeta)) {
	        return;
	    }

	    deleteFilesInDirectory(carpeta);
	    deleteDirectory(carpeta);
	}

	private static void deleteFilesInDirectory(Path carpeta) {
	    try (DirectoryStream<Path> archivos = Files.newDirectoryStream(carpeta)) {
	        for (Path archivo : archivos) {
	            deleteFile(archivo);
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer " + carpeta + ": " + e.getMessage());
	    }
	}

	private static void deleteFile(Path archivo) {
	    try {
	        Files.delete(archivo);
	    } catch (IOException e) {
	        System.err.println("Error al borrar el archivo: " + archivo + " -> " + e.getMessage());
	    }
	}

	private static void deleteDirectory(Path carpeta) {
	    try {
	        Files.delete(carpeta);
	    } catch (IOException e) {
	        System.err.println("Error al borrar la carpeta: " + carpeta + " -> " + e.getMessage());
	    }
	}
	
	public static void initializeBusinessLogic(ConfigXML c, MainGUI a) {
	    try {
	        setUILookAndFeel();
	        BLFacade appFacadeInterface = c.isBusinessLogicLocal()
	                ? createLocalBusinessLogic()
	                : createRemoteBusinessLogic(c);
	        MainGUI.setBussinessLogic(appFacadeInterface);
	    } catch (Exception e) {
	        handleInitializationError(a, e);
	    }
	}

	private static void setUILookAndFeel() throws Exception {
	    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	}

	private static BLFacade createLocalBusinessLogic() {
	    DataAccess da = new DataAccess();
	    return new BLFacadeImplementation(da);
	}

	private static BLFacade createRemoteBusinessLogic(ConfigXML c) throws Exception {
	    String serviceName = "http://" + c.getBusinessLogicNode() + ":" +
	                         c.getBusinessLogicPort() + "/ws/" +
	                         c.getBusinessLogicName() + "?wsdl";
	    URL url = new URL(serviceName);
	    QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
	    Service service = Service.create(url, qname);
	    return service.getPort(BLFacade.class);
	}

	private static void handleInitializationError(MainGUI a, Exception e) {
	    a.jLabelSelectOption.setText("Error: " + e.toString());
	    a.jLabelSelectOption.setForeground(Color.RED);
	    System.err.println("Error in ApplicationLauncher: " + e.toString());
	}


}
