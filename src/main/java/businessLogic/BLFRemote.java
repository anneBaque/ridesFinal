package businessLogic;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;

public class BLFRemote implements BLFInterface {

	@Override
	public BLFacade getBusinessLogicFactory() {

		try {
			ConfigXML c = ConfigXML.getInstance();
	
			String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
					+ c.getBusinessLogicName() + "?wsdl";
			URL url = new URL(serviceName);
			QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
			Service service = Service.create(url, qname);
			return service.getPort(BLFacade.class);
		}
		catch(Exception e) {
			System.out.println("Error al lanzar la aplicacion remota: " + e.toString());
		}
		return null;
	}
	

}
