package gui.Passenger;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import gui.MainGUI;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MyRidesPassengerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;


	/**
	 * Create the frame.
	 */
	public MyRidesPassengerGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		DefaultListModel rides = new DefaultListModel();
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		
		btnClose.setBounds(231, 200, 119, 33);
		contentPane.add(btnClose);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		List<Ride> viajes = facade.acceptedReservation(pasajero.getEmail());
		
		
		
		
		JButton btnCancelar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Cancelar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCancelar.setBounds(85, 200, 119, 33);
		contentPane.add(btnCancelar);
	
		String[] columnas = {ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Code"), ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"), 
		ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"),ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate")};
		DefaultTableModel model = new DefaultTableModel(null, columnas);
		tabla = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(40, 43, 365, 127);
		contentPane.add(scrollPane);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		tabla.getColumnModel().getColumn(0).setPreferredWidth(75);  
		tabla.getColumnModel().getColumn(1).setPreferredWidth(100); 
		tabla.getColumnModel().getColumn(2).setPreferredWidth(100); 
		tabla.getColumnModel().getColumn(3).setPreferredWidth(250); 


		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (Ride aux : viajes) {
			Object[] row = {aux.getRideNumber(), aux.getFrom(), aux.getTo(), aux.getDate()};
			model.addRow(row);
		}
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
				}
			});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabla.getSelectedRow();
				if (selectedRow ==-1) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.SelectError"));
					return;
				}else {
					Ride aux= (Ride) facade.findRide((int)tabla.getValueAt(selectedRow, 0));
					Calendar currentCalendar = Calendar.getInstance();
					currentCalendar.add(Calendar.DAY_OF_YEAR, 7);
					Calendar rideDate = Calendar.getInstance();
					rideDate.setTime(aux.getDate());
					if(rideDate.after(currentCalendar)) {
						facade.eraseReservation(pasajero.getEmail(),aux);
						actualizarLista(viajes,rides,pasajero, facade, model);
						String asunto = ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Asunto");
						String mensaje1 = ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Mensaje1");
						String mensaje2 = ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Mensaje2");
						
						//MAIL//
						
						String receptor = aux.getDriver().getEmail();
						Properties props = new Properties();
						props.put("mail.smtp.host", "smtp.ehu.es");
						Session session = Session.getInstance(props);
						try {
							 
							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("TravelBooking@business.com"));
							message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(receptor));
							message.setSubject(asunto);
							message.setText(mensaje1 +" "+aux.getRideNumber()+" "+mensaje2+" "+ pasajero.getEmail());
							Transport.send(message);
				 
							System.out.println("Mensaje Enviado");
				 
						} catch (MessagingException ex) {
							System.out.println("Ese correo no esiste tontorron");
						}
					}else {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRidesPassengerGUI.Near"));
					}
					
				
				}
			}
		});
		
		
		
		
	}
	private void actualizarLista(List<Ride> viajes, DefaultListModel rides, Passenger pasajero, BLFacade facade, DefaultTableModel model) {
		rides.removeAllElements();
		viajes= facade.acceptedReservation(pasajero.getEmail());
		for (Ride aux : viajes) {
			Object[] row = {aux.getRideNumber(), aux.getFrom(), aux.getTo(), aux.getDate()};
			model.addRow(row);
		}
		
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
