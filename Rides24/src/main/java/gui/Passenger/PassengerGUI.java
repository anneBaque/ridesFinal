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
import javax.swing.SwingConstants;

public class PassengerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public PassengerGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(pasajero.getName());
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(134, 11, 166, 25);
		contentPane.add(lblNombre);
		
		JButton btnReservar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Reservations"));
		btnReservar.setBounds(47, 57, 173, 154);
		contentPane.add(btnReservar);
		
		JButton btnViajes = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Rides")); //$NON-NLS-1$ //$NON-NLS-2$
		btnViajes.setBounds(218, 57, 173, 154);
		contentPane.add(btnViajes);
		
		JButton btnWallet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PassengerGUI.Wallet")); //$NON-NLS-1$ //$NON-NLS-2$
		btnWallet.setBounds(171, 222, 89, 23);
		contentPane.add(btnWallet);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		btnReservar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SearchForRidesGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		btnViajes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AboutRidesGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		btnWallet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				JFrame a = new PassengerWalletGUI(pasajero);
				a.setVisible(true);
			
			}

			
		});
		
		
	

	}
}
