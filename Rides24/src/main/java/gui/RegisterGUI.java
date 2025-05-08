package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
import gui.Driver.DriverGUI;
import gui.Passenger.PassengerGUI;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldNombre;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();



	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.User"));
		lblUser.setBounds(33, 25, 127, 25);
		contentPane.add(lblUser);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(161, 22, 237, 28);
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblNombre = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Name"));
		lblNombre.setBounds(33, 72, 127, 25);
		contentPane.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(161, 71, 237, 28);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblContra = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
		lblContra.setBounds(33, 123, 127, 25);
		contentPane.add(lblContra);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(161, 123, 237, 25);
		contentPane.add(passwordField);
		
		JRadioButton rdbtnConductor = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
		buttonGroup.add(rdbtnConductor);
		rdbtnConductor.setBounds(33, 180, 127, 21);
		contentPane.add(rdbtnConductor);
		
		JRadioButton rdbtnPasajero = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Passenger"));
		buttonGroup.add(rdbtnPasajero);
		rdbtnPasajero.setBounds(231, 180, 135, 21);
		contentPane.add(rdbtnPasajero);
		
		JButton btnEntrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.GetIN"));
		btnEntrar.setBounds(242, 217, 156, 36);
		contentPane.add(btnEntrar);
		
		JButton btnsistema = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Login"));
		btnsistema.setBounds(46, 225, 156, 28);
		contentPane.add(btnsistema);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		btnsistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SignInGUI();
				a.setVisible(true);
				
			}
		});
		btnEntrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				String email = textFieldUser.getText().trim(); 
				String nombre = textFieldNombre.getText().trim();
				String contraseña = passwordField.getText().trim();

				if (email.isEmpty() || nombre.isEmpty() || contraseña.isEmpty()) {
				    JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.NotComplete"));
				    return;
				}
				boolean añadido;
				if(rdbtnPasajero.isSelected()) {
					añadido = facade.storePassenger(email, nombre, contraseña);
					if(añadido) {
						Passenger p = facade.findPassenger(email);
						JFrame a =  new PassengerGUI(p);
						a.setVisible(true);
						doNotSetVisible(e);
					}else {
						JButton btnEntrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));	
					}
				}else {
					añadido = facade.storeDriver(email, nombre, contraseña);
					if (añadido) {
						Driver d = facade.findDriver(email);
						JFrame a = new DriverGUI(d);
						a.setVisible(true);
						doNotSetVisible(e);
					}else {
						JButton btnEntrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));
					}
					
				}
			}

		
		});
	}
	
	
	private void doNotSetVisible(ActionEvent e) {
		this.setVisible(false);
	}

}
