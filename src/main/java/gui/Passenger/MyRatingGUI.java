package gui.Passenger;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Passenger;
import domain.Rating;
import domain.Reservation;
import domain.Ride;
import gui.MainGUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class MyRatingGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MyRatingGUI(Passenger pasajero) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(248, 218, 111, 32);
		contentPane.add(btnClose);
		
		JList list = new JList();
		list.setBounds(30, 27, 365, 45);
		contentPane.add(list);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(216, 85, 30, 20);
		SpinnerNumberModel sm=new SpinnerNumberModel(1,0,10,1);
		spinner.setModel(sm);
		contentPane.add(spinner);
		
		textField = new JTextField();
		textField.setBounds(109, 126, 286, 81);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextArea textAreaTexto = new JTextArea();
		textAreaTexto.setEditable(false);
		textAreaTexto.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.Texto")); //$NON-NLS-1$ //$NON-NLS-2$
		textAreaTexto.setBounds(29, 154, 58, 20);
		contentPane.add(textAreaTexto);
		
		JTextArea textAreaRating = new JTextArea();
		textAreaRating.setEditable(false);
		textAreaRating.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.Rating")); //$NON-NLS-1$ //$NON-NLS-2$
		textAreaRating.setBounds(148, 83, 58, 22);
		contentPane.add(textAreaRating);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		List<Ride> lista =facade.acceptedReservation(pasajero.getEmail());
		
		
		DefaultListModel rides = new DefaultListModel();
		for (Ride aux: lista) {
			rides.addElement(aux);
		}
		
		list.setModel(rides);
		
		JButton btnGuardar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.Guardar")); //$NON-NLS-1$ //$NON-NLS-2$

		btnGuardar.setBounds(95, 218, 111, 32);
		contentPane.add(btnGuardar);
		
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue()!=null && spinner.getValue()!=null) {
					Ride r = (Ride) list.getSelectedValue();
					boolean existe;
					if(textField.getText().equals("") || textField.getText()==null) {
						existe = facade.addRating(new Rating(pasajero.getEmail(), r.getRideNumber(),(int)spinner.getValue(), r.getDriver().getEmail()));
						System.out.println("Sin texto YUPIIII");
						if(!existe) {
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.RatingDone"));
						}
					}else { 
						existe = facade.addRating(new Rating(pasajero.getEmail(), r.getRideNumber(),(int)spinner.getValue(), textField.getText(), r.getDriver().getEmail()));
						System.out.println("Con Texto JOOOOOO");
						if(!existe) {
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.RatingDone"));
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("MyRatingGUI.Select"));
				}
			}
		});
		
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
