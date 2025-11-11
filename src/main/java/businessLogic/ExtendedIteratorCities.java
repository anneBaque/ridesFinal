package businessLogic;

import java.util.List;

public class ExtendedIteratorCities implements ExtendedIterator<String> {
	private List<String> cities;
	private int i;
	
	public ExtendedIteratorCities(List<String> l) {
		this.cities = l;
		i = 0;
	}
	
	@Override
	public boolean hasNext() {
		return i < cities.size()-1;
	}

	@Override
	public String next() {
		i++;
		return cities.get(i);
	}
	
	public void goFirst() {
		i = -1;
	}
	
	public void goLast() {
		i = cities.size();
	}


	@Override
	public String previous() {
		i --;
		return cities.get(i);
	}


	@Override
	public boolean hasPrevious() {
		return i!=0;
	}

}
