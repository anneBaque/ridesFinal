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

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
	
		System.out.println(c.getLocale());
		
		Locale.setDefault(new Locale(c.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
	    Driver driver=new Driver("driver3@gmail.com","Test Driver");

		
		MainGUI a=new MainGUI(driver);
		a.setVisible(true);
		if(c.isDatabaseInitialized()) {
			 Path carpeta = Paths.get("imagenes");
			 //Eliminamos las imagenes de la base de datos antigua
		        if (Files.exists(carpeta) && Files.isDirectory(carpeta)) {
					try (DirectoryStream<Path> archivos = Files.newDirectoryStream(carpeta)) { //iterar sobre el directorio
						for (Path archivo : archivos) {
							try{
								Files.delete(archivo);
							} catch (IOException e) {
								System.err.println("Error al borrar el archivo: " + archivo + " -> " + e.getMessage());
                			}
						}
					} catch (IOException e) {
            			System.err.println("Error al leer " + carpeta + ": " + e.getMessage());
        			}

        			try {
            			Files.delete(carpeta); // Eliminar la carpeta después de vaciarla
        			} catch (IOException e) {
            			System.err.println("Error al borrar la carpeta: " + carpeta + " -> " + e.getMessage());
        			}
				}	
		}

		try {
			
			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			if (c.isBusinessLogicLocal()) {
			
				DataAccess da= new DataAccess();
				appFacadeInterface=new BLFacadeImplementation(da);
			
			}	
			
			
			else { //If remote
				
				 String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
				 
				URL url = new URL(serviceName);

		 
		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);

		         appFacadeInterface = service.getPort(BLFacade.class);
			} 
			
			MainGUI.setBussinessLogic(appFacadeInterface);

		

			
		}catch (Exception e) {
			a.jLabelSelectOption.setText("Error: "+e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

}
