package gui.Driver;

import java.awt.EventQueue;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import gui.MainGUI;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Driver;
import domain.Rating;
import domain.Ride;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpinionsAboutGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable tabla;
	private JButton btnClose;
	private JButton btnAnswer;



	/**
	 * Create the frame.
	 */
	public OpinionsAboutGUI(Driver conductor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		BLFacade facade = MainGUI.getBusinessLogic(); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 33, 354, 77);
		contentPane.add(scrollPane);
		
		String[] columnas = {ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Email"), ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Code"), 
		ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.rating"), ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.Comment")};
		DefaultTableModel model = new DefaultTableModel(null, columnas);
		tabla = new JTable(model);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		tabla.getColumnModel().getColumn(0).setPreferredWidth(100);  
		tabla.getColumnModel().getColumn(1).setPreferredWidth(50); 
		tabla.getColumnModel().getColumn(2).setPreferredWidth(50); 
		tabla.getColumnModel().getColumn(3).setPreferredWidth(250);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		setLocationRelativeTo(null);

		scrollPane.setViewportView(tabla);
		
		List<Rating> opiniones = facade.findRating(conductor.getEmail());
		
		for (Rating aux : opiniones) {
			if(aux.getRespuesta() == null) {
			Object[] row = {aux.getEmail(), aux.getIdRide(), aux.getIdRating(), aux.getMensaje()};
			model.addRow(row);
			}
		}
		
		
		textField = new JTextField();
		textField.setBounds(54, 141, 354, 54);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(54, 214, 111, 39);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		
		btnAnswer = new JButton(ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.Answer"));
		btnAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabla.getSelectedRow();
				if (selectedRow ==-1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.SelectError"));
					return;
				}else {
					if(textField.getText().equals("") || textField.getText()==null) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.NoText"));
						return;
					}else {
						facade.responseRating((String)tabla.getValueAt(selectedRow, 0),(int) tabla.getValueAt(selectedRow, 1), conductor.getEmail(), textField.getText());
						
					}
				}
				
			}
		});
		btnAnswer.setBounds(260, 214, 117, 39);
		contentPane.add(btnAnswer);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
