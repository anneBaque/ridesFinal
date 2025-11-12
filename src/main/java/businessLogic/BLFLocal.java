package businessLogic;
import dataAccess.DataAccess;

public class BLFLocal implements BLFInterface {

	@Override
	public BLFacade getBusinessLogicFactory() {
		DataAccess da = new DataAccess();
		return new BLFacadeImplementation(da);
	}

}
