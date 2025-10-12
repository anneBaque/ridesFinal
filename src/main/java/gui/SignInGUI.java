package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
import gui.Driver.DriverGUI;
import gui.Passenger.PassengerGUI;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;

public class SignInGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();



	/**
	 * Create the frame.
	 */
	public SignInGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(135, 10, 263, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(135, 63, 263, 30);
		contentPane.add(passwordField);
		
		JRadioButton rdbtnconductor = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Driver"));
		buttonGroup.add(rdbtnconductor);
		rdbtnconductor.setBounds(49, 124, 123, 21);
		contentPane.add(rdbtnconductor);
		
		JRadioButton rdbtnPasajero = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Passenger"));
		buttonGroup.add(rdbtnPasajero);
		rdbtnPasajero.setBounds(235, 124, 123, 21);
		contentPane.add(rdbtnPasajero);
		
		JButton btnsistema = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Login"));
		btnsistema.setBounds(117, 159, 224, 46);
		contentPane.add(btnsistema);
		
		JButton btnregistro = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Register"));
		btnregistro.setBounds(164, 215, 136, 30);
		contentPane.add(btnregistro);
		
		JLabel lblUser =  new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Email"));
		lblUser.setBounds(40, 14, 85, 21);
		contentPane.add(lblUser);
		
		JLabel lblPassW = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Password"));
		lblPassW.setBounds(40, 67, 85, 21);
		contentPane.add(lblPassW);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		btnsistema.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String email = textField.getText().trim(); 
				String contraseña = passwordField.getText().trim();

				if (email.isEmpty() | contraseña.isEmpty() | (rdbtnPasajero.isSelected() && rdbtnconductor.isSelected())) {
				    JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.NotComplete"));
				    return;
				}
				btnsistema.setText(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Login"));
				boolean correcto = false;
				if(rdbtnconductor.isSelected()) {
					correcto = facade.comprobarDriver(email, contraseña);
					if(correcto) {
						Driver conductor = facade.findDriver(email);
						JFrame a = new DriverGUI(conductor);
						a.setVisible(true);
						doNotSetVisible(e);
					}else {
						textField.setText(null);
						passwordField.setText(null);
						btnsistema.setText(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Incorrect"));
					}
				}else {
					correcto = facade.comprobarPassenger(email, contraseña);
					if (correcto) {
						Passenger pasajero = facade.findPassenger(email);
						JFrame b = new PassengerGUI(pasajero);
						b.setVisible(true);
						doNotSetVisible(e);
					}else {
						textField.setText(null);
						passwordField.setText(null);
						btnsistema.setText(ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Incorrect"));
						
					
				}

			}
			}
		});
		
		btnregistro.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFrame c = new RegisterGUI();
				c.setVisible(true);
				
			}
		});

	}
	
	private void doNotSetVisible(ActionEvent e) {
		this.setVisible(false);
	}
	
}
