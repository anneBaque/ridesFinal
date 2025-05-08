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
import domain.Ride;
import gui.MainGUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;

public class MyCreatedRidesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MyCreatedRidesGUI(Driver conductor) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(46, 45, 348, 112);
		contentPane.add(list);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(155, 191, 111, 32);
		contentPane.add(btnClose);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		DefaultListModel rides = new DefaultListModel();
		
		list.setModel(rides);
		for (Ride aux: conductor.getRides()) {
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
