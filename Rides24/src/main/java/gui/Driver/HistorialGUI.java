package gui.Driver;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
import domain.Reservation;
import domain.Ride;
import gui.MainGUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;

public class HistorialGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public HistorialGUI(Driver conductor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		DefaultComboBoxModel rides = new DefaultComboBoxModel();
		List<Reservation> lista = facade.getAllReservations();
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		
		btnClose.setBounds(156, 201, 119, 33);
		contentPane.add(btnClose);
		
		JComboBox list = new JComboBox();
		list.setBounds(38, 30, 355, 37);
		list.setModel(rides);
		contentPane.add(list);
		boolean estaEnLista=false;
		for (Reservation aux: lista) {
			estaEnLista=true;
			for(Ride r: conductor.getRides()) {
				if(aux.getIdRide()==r.getRideNumber()) {
					rides.addElement(aux);
				}
			}
		}
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
				}
			});
		
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
}
