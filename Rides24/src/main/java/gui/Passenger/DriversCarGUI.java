package gui.Passenger;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Car;
import gui.MainGUI;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class DriversCarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public DriversCarGUI(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		BLFacade facade = MainGUI.getBusinessLogic(); 
		Car coche = facade.findCar(email);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		
		JLabel lblMatricula = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Matricula")+ " :"+ coche.getMatricula());
		lblMatricula.setBounds(32, 22, 205, 27);
		contentPane.add(lblMatricula);
		
		JLabel lblMarca = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Marca")+ " :" + coche.getMarca());
		lblMarca.setBounds(27, 59, 160, 27);
		contentPane.add(lblMarca);
		
		JLabel lblNModelo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Modelo")+ " :" + coche.getModelo());
		lblNModelo.setBounds(235, 59, 160, 27);
		contentPane.add(lblNModelo);
		
		JLabel lblKmetraje = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Kilometraje")+ " :" + coche.getKilometraje());
		lblKmetraje.setBounds(270, 22, 124, 20);
		contentPane.add(lblKmetraje);
		
		JLabel lblFoto = new JLabel();
		lblFoto.setBounds(109, 112, 215, 91);
		contentPane.add(lblFoto);
		
		if (coche.getFoto() != null) {
            ImageIcon imagenIcon = new ImageIcon("imagenes/" + coche.getFoto());
            Image imagen = imagenIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagen));
            lblFoto.setText("");
        }

		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(170, 223, 85, 21);
		contentPane.add(btnClose);
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
