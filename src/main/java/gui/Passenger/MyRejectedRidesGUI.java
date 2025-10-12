package gui.Passenger;

import java.awt.EventQueue;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Passenger;
import domain.Ride;
import gui.MainGUI;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyRejectedRidesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public MyRejectedRidesGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(47, 33, 338, 157);
		contentPane.add(list);
		DefaultListModel rides = new DefaultListModel();
		list.setModel(rides);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

		btnClose.setBounds(154, 200, 119, 33);
		contentPane.add(btnClose);
		
		BLFacade facade = MainGUI.getBusinessLogic();
	
		List<Ride> viajes = facade.rejectedReservation(pasajero.getEmail());
		
		for (Ride aux : viajes) {
			rides.addElement(aux);
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
	


