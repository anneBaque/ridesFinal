package testAdapter;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import domain.Driver;
import domain.DriverTable;

public class pruebaAdapter {
	public static void main(String[] args) {
		// the BL is local
		boolean isLocal = true;
		BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
		Driver d = blFacade.findDriver("Urtzi");
		DriverTable dt = new DriverTable(d);
		dt.setVisible(true);
	}
}
