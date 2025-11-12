package businessLogic;

public class BLFactory {
	public BLFacade getBusinessLogicFactory(boolean isLocal) {
		BLFInterface blf;
		if(isLocal) blf = new BLFLocal();
		else blf = new BLFRemote();
		
		return blf.getBusinessLogicFactory();
	}
}
