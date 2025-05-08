package gui.Passenger;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Passenger;
import gui.MainGUI;

import javax.swing.JLabel;
import javax.swing.JButton;

public class AboutRidesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public AboutRidesGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(pasajero.getName());
		lblNombre.setBounds(147, 11, 166, 25);
		contentPane.add(lblNombre);
		
		JButton btnMyRejected = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Rejection"));
		btnMyRejected.setBounds(36, 126, 173, 71);
		contentPane.add(btnMyRejected);
		
		JButton btnMyRides = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Rides"));
		btnMyRides.setBounds(36, 57, 173, 71);
		contentPane.add(btnMyRides);
		
		JButton btnMyReserved = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Visualize")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMyReserved.setBounds(208, 57, 173, 71);
		contentPane.add(btnMyReserved);
		
		JButton btnRate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Rating")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRate.setBounds(208, 126, 173, 71);
		contentPane.add(btnRate);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		btnMyRejected.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyRejectedRidesGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		btnMyRides.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyRidesPassengerGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		btnMyReserved.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyReservedRidesGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		btnRate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyRatingGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
	

	}
}
