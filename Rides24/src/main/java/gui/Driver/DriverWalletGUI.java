package gui.Driver;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import businessLogic.BLFacade;
import domain.Driver;

import gui.CargaGUI;
import gui.MainGUI;


import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;


public class DriverWalletGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textInput;


	/**
	 * Create the frame.
	 */
	public DriverWalletGUI(Driver conductor) {
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
		
		JLabel lblDineros = new JLabel(Float.toString(conductor.getWallet().calcularSaldo())); //$NON-NLS-1$ //$NON-NLS-2$
		lblDineros.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDineros.setBounds(201, 26, 46, 33);
		contentPane.add(lblDineros);
		
		textInput = new JTextField();
		textInput.setText(ResourceBundle.getBundle("Etiquetas").getString("PassengerWalletGUI.Input")); //$NON-NLS-1$ //$NON-NLS-2$
		textInput.setBounds(148, 91, 127, 33);
		contentPane.add(textInput);
		textInput.setColumns(10);
		
		JButton btnWithdraw = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverWalletGUI.Withdraw"));
		btnWithdraw.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnWithdraw.setBounds(174, 135, 89, 23);
		contentPane.add(btnWithdraw);
		
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float input;
				boolean b;
				try {
					input = Float.parseFloat(textInput.getText());
					b=conductor.getWallet().withdraw(input);
					if(b==false) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("DriverWalletGUI.NoWithdraw"));
					}else {
						lblDineros.setText(Float.toString(conductor.getWallet().calcularSaldo()));
						facade.withdraw(conductor, input);
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
