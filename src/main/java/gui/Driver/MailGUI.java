package gui.Driver;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Mail;
import domain.Ride;
import gui.MainGUI;

import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MailGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;


	/**
	 * Create the frame.
	 */
	public MailGUI(Driver conductor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(70, 198, 119, 38);
		contentPane.add(btnClose);
		
		BLFacade facade = MainGUI.getBusinessLogic();

		setLocationRelativeTo(null);

		
		JButton btnRead = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Leer"));
		btnRead.setBounds(224, 198, 154, 38);
		contentPane.add(btnRead);
		String[] columnas = {ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Remitente"), ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Fecha"), 
		ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Asunto"), "Mail"};
		DefaultTableModel model = new DefaultTableModel(null, columnas);
		model.setDataVector(null, columnas);
		tabla = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(40, 43, 365, 127);
		
		contentPane.add(scrollPane);
		
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		tabla.getColumnModel().getColumn(0).setPreferredWidth(150);  
		tabla.getColumnModel().getColumn(1).setPreferredWidth(300); 
		tabla.getColumnModel().getColumn(2).setPreferredWidth(300); 
		model.setColumnCount(4);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		List<Mail> correos = facade.mirarCorreos(conductor.getEmail());
		for (Mail aux : correos) {
			Vector<Object> row = new Vector<Object>();
			row.add(aux.getRemitente());
			row.add(aux.getFechaEnvio());
			row.add(aux.getAsunto());
			row.add(aux);
			model.addRow(row);
		}
		tabla.getColumnModel().getColumn(3).setMinWidth(0);
		tabla.getColumnModel().getColumn(3).setMaxWidth(0);
		tabla.getColumnModel().getColumn(3).setWidth(0);
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
				}
			});
		
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabla.getSelectedRow();
				if (selectedRow ==-1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MailGUI.SelectError"));
					return;
				}else {
					Mail correo = (Mail) model.getValueAt(selectedRow, 3);
					facade.leeCorreo(correo);
					ReadMailGUI a = new ReadMailGUI(correo);
					a.setVisible(true);
					
					
				}
			}
		});
		
	}
	
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
