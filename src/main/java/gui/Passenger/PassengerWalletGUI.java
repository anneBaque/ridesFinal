package gui.Passenger;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Mail;
import domain.Passenger;
import domain.Reservation;
import domain.Ride;
import gui.CargaGUI;
import gui.MainGUI;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.awt.Component;

public class PassengerWalletGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textInput;


	/**
	 * Create the frame.
	 */
	public PassengerWalletGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		DefaultListModel rides = new DefaultListModel();
		
		BLFacade facade = MainGUI.getBusinessLogic();

		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnClose.setBounds(156, 200, 119, 33);
		contentPane.add(btnClose);
		
		JLabel lblDineros = new JLabel(Float.toString(pasajero.getWallet().calcularSaldo())); //$NON-NLS-1$ //$NON-NLS-2$
		lblDineros.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDineros.setBounds(201, 26, 46, 33);
		contentPane.add(lblDineros);
		
		textInput = new JTextField();
		textInput.setText(ResourceBundle.getBundle("Etiquetas").getString("PassengerWalletGUI.Input")); //$NON-NLS-1$ //$NON-NLS-2$
		textInput.setBounds(148, 91, 127, 33);
		contentPane.add(textInput);
		textInput.setColumns(10);
		
		JButton btnConfirm = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept")); //$NON-NLS-1$ //$NON-NLS-2$
		btnConfirm.setBounds(170, 135, 89, 23);
		contentPane.add(btnConfirm);
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float input;
				boolean b;
				try {
					input = Float.parseFloat(textInput.getText());
					b=pasajero.getWallet().addCash(input);
					if(b==false) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("PassengerWalletGUI.NumNegativo"));
					}else {
						lblDineros.setText(Float.toString(pasajero.getWallet().calcularSaldo()));
						facade.anadirDinero(pasajero, input);
						JFrame a = new CargaGUI();
						a.setVisible(true);
						btnClose_actionPerformed(e);
					}
				}catch(NumberFormatException et) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("PassengerWalletGUI.NoNumero"));
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
	
}
