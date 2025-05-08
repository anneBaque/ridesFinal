package gui.Passenger;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Passenger;
import domain.Rating;
import gui.MainGUI;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class OpinionsDriverGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;


	OpinionsDriverGUI(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		BLFacade facade = MainGUI.getBusinessLogic(); 
		setLocationRelativeTo(null);


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 34, 391, 154);
		contentPane.add(scrollPane);
		
		tabla = new JTable();
		String[] columnas = {ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.Email"), ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.rating"), 
		ResourceBundle.getBundle("Etiquetas").getString("OpinionsAboutGUI.Comment"), ResourceBundle.getBundle("Etiquetas").getString("OpinionsDriverGUI.Answer")};
		DefaultTableModel model = new DefaultTableModel(null, columnas);
		tabla = new JTable(model);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		tabla.getColumnModel().getColumn(0).setPreferredWidth(100);  
		tabla.getColumnModel().getColumn(1).setPreferredWidth(50); 
		tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(150); 			
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(tabla);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(157, 215, 105, 38);
		contentPane.add(btnClose);
		List<Rating> opiniones = facade.findRating(email);
		
		for (Rating aux : opiniones) {
			Object[] row = {aux.getEmail(), aux.getIdRating(), aux.getMensaje(), aux.getRespuesta()};
			model.addRow(row);
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
