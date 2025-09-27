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
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class ReservedRidesDriverGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ReservedRidesDriverGUI(Driver conductor) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(162, 189, 111, 32);
		contentPane.add(btnClose);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		DefaultComboBoxModel rides = new DefaultComboBoxModel();
		List<Reservation> lista = facade.getAllReservations();
		
		JButton btnAceptar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservedRidesDriverGUI.Accept")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAceptar.setBounds(65, 128, 105, 32);
		contentPane.add(btnAceptar);
		
		JButton btnRechazar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReservedRidesDriverGUI.Reject")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRechazar.setBounds(257, 128, 90, 32);
		contentPane.add(btnRechazar);
		
		JComboBox list = new JComboBox();
		list.setBounds(38, 30, 355, 37);
		list.setModel(rides);
		contentPane.add(list);
		actualizarLista(lista, rides, conductor);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedItem()!=null) {
					Reservation reserva = (Reservation) list.getSelectedItem();
					Ride r = facade.findRide(reserva.getIdRide());
					facade.anadirDinero(conductor, r.getPrice());
					facade.acceptReservation(reserva);
					lista.remove(reserva);
					actualizarLista(lista,rides,conductor);
				}else {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("ReservedRiedsDriverGUI.Reservation"));
					//Por favor, selecciona una reserva
				}
			}
			
		});
		
		btnRechazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedItem()!=null) {
					Reservation reserva = (Reservation) list.getSelectedItem();
					Ride r = facade.findRide(reserva.getIdRide());
					Passenger p = facade.findPassenger(reserva.getPasEmail());
					facade.añadirDinero(p, r.getPrice());
					facade.rejectReservation(reserva);
					lista.remove(reserva);
					actualizarLista(lista,rides,conductor);
				}else {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.NotComplete"));
				}
			}
		});
		
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		
		
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void actualizarLista(List<Reservation> list, DefaultComboBoxModel rides, Driver conductor) {
		rides.removeAllElements();
		for (Reservation aux: list) {
			for(Ride r: conductor.getRides()) {
				if(aux.getIdRide()==r.getRideNumber() && aux.isProcesado() == false) {
					rides.addElement(aux);
				}
			}
		}
	}
}
