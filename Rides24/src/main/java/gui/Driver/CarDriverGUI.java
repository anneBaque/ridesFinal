package gui.Driver;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Car;
import domain.Driver;
import gui.MainGUI;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class CarDriverGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textKmetraje;
	private JTextField textMatricula;


	public CarDriverGUI(Driver conductor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		
		textMarca = new JTextField();
		textMarca.setBounds(196, 37, 191, 21);
		contentPane.add(textMarca);
		textMarca.setColumns(10);
		
		textModelo = new JTextField();
		textModelo.setBounds(196, 75, 191, 21);
		contentPane.add(textModelo);
		textModelo.setColumns(10);
		
		JLabel lblMarca = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Marca"));
		lblMarca.setBounds(39, 37, 113, 21);
		contentPane.add(lblMarca);
		
		JLabel lblModelo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Modelo"));
		lblModelo.setBounds(39, 71, 113, 21);
		contentPane.add(lblModelo);
		
		textKmetraje = new JTextField();
		textKmetraje.setBounds(196, 106, 191, 21);
		contentPane.add(textKmetraje);
		textKmetraje.setColumns(10);
		
		JLabel lblKmetraje = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Kilometraje"));
		lblKmetraje.setBounds(39, 110, 113, 21);
		contentPane.add(lblKmetraje);
		
		JLabel lblFoto = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Foto"));
		lblFoto.setBounds(130, 140, 191, 71);
		contentPane.add(lblFoto);
		
		JButton btnSubirFoto = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.SubirFoto"));
		btnSubirFoto.setBounds(123, 210, 196, 28);
		contentPane.add(btnSubirFoto);
		
		JLabel lblMatricula = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Matricula"));
		lblMatricula.setBounds(39, 10, 45, 13);
		contentPane.add(lblMatricula);
		
		textMatricula = new JTextField();
		textMatricula.setBounds(195, 7, 192, 19);
		contentPane.add(textMatricula);
		textMatricula.setColumns(10);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();

		
		btnSubirFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = textMatricula.getText().trim();
				String marca = textMarca.getText().trim();
				String modelo = textModelo.getText().trim();
				int kilometraje = -1;
				try {
					kilometraje = Integer.parseInt(textKmetraje.getText());
				}catch(NumberFormatException et) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.NoNumero"));
					textKmetraje.setText(null);

				}

				if (matricula.isEmpty() | marca.isEmpty() | modelo.isEmpty() | kilometraje == -1) {
				    JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("SignInGUI.NotComplete"));
				    return;
				}
				
				
				Car coche = new Car(textMatricula.getText(), textMarca.getText(), textModelo.getText(), Integer.parseInt(textKmetraje.getText()), conductor.getEmail());
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

                int resultado = fileChooser.showOpenDialog(CarDriverGUI.this);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File imagenSeleccionada = fileChooser.getSelectedFile();

                    // Mostrar imagen
                    ImageIcon imagenIcon = new ImageIcon(imagenSeleccionada.getAbsolutePath());
                    Image imagen = imagenIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    lblFoto.setIcon(new ImageIcon(imagen));
                    lblFoto.setText("");

                    // Guardar imagen en carpeta
                    try {
                        File carpetaDestino = new File("imagenes");
                        if (!carpetaDestino.exists()) {
                            carpetaDestino.mkdir();
                        }
                        File destino = new File(carpetaDestino, imagenSeleccionada.getName());
                        Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        coche.setFoto(destino);
                        boolean guardado = facade.storeCar(coche);
                        if(!guardado) {
                            JOptionPane.showMessageDialog(CarDriverGUI.this, ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.YaGuardado"));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CarDriverGUI.this, ResourceBundle.getBundle("Etiquetas").getString("CarDriverGUI.Error"));
                    }

                   
                }
				
			}
		});
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(22, 164, 85, 59);
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
