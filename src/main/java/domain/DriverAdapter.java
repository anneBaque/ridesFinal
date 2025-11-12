package domain;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DriverAdapter extends AbstractTableModel {

	protected Driver driver;
    protected List<Ride> rList;
    private final String[] columnNames = {"From", "To", "Date", "Places", "Price"};
	
	public DriverAdapter(Driver d) {
        this.driver = d;
        this.rList = d.getRides();
    }
	
	@Override
	public int getRowCount() {
		return rList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
	    return columnNames[columnIndex];  // Return column header
	 }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ride ride = rList.get(rowIndex);  // Get ride at the specified row index
        switch (columnIndex) {
            case 0: return ride.getFrom();
            case 1: return ride.getTo();
            case 2: return ride.getDate();
            case 3: return ride.getnPlaces();
            case 4: return ride.getPrice();
            default: return null;
        }
	}
}
