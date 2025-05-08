package gui.Driver;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
import gui.MainGUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Button;

public class DriverGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public DriverGUI(Driver conductor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(conductor.getName());
		lblNombre.setBounds(237, 10, 166, 25);
		contentPane.add(lblNombre);
		
		JButton btnCreatedRides = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MyCreatedRides"));
		btnCreatedRides.setBounds(218, 57, 185, 75);
		contentPane.add(btnCreatedRides);
		
		JButton btnCrear = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.CreateRide"));
		btnCrear.setBounds(23, 57, 185, 75);
		contentPane.add(btnCrear);
		
		JButton btnViewReservations = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.ViewReservations"));
		btnViewReservations.setBounds(218, 133, 185, 75);
		contentPane.add(btnViewReservations);
		
		JButton btnHistorial = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Historial")); 
		btnHistorial.setBounds(23, 133, 185, 75);
		contentPane.add(btnHistorial);
		
		JButton btnOpinions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Opiniones")); 
		btnOpinions.setBounds(23, 218, 185, 35);
		contentPane.add(btnOpinions);
		
		JButton btnCoche = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Coche")); 
		btnCoche.setBounds(218, 218, 178, 35);
		contentPane.add(btnCoche);
		
		JButton btnNoti = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Correo")); 
		btnNoti.setBounds(38, 12, 150, 21);
		contentPane.add(btnNoti);
		
		JButton btnWallet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Wallet")); //$NON-NLS-1$ //$NON-NLS-2$
		btnWallet.setBounds(335, 11, 89, 23);
		contentPane.add(btnWallet);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		if(facade.tieneCorreos(conductor.getEmail())) {
			JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Noti"));
		}
		
		btnCreatedRides.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new MyCreatedRidesGUI(conductor);
				a.setVisible(true);
			
			}

			
		});
		
		btnCrear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new CreateRideGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
	
		btnViewReservations.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new ReservedRidesDriverGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
		
		btnHistorial.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new HistorialGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
		
		btnOpinions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new OpinionsAboutGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
		
		btnCoche.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new CarDriverGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
		
		btnNoti.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new MailGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
		
		btnWallet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				JFrame a = new DriverWalletGUI(conductor);
				a.setVisible(true);
				
			}

			
		});
	}
}
