package gui.Driver;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Mail;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ReadMailGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	/**
	 * Create the frame.
	 */
	public ReadMailGUI(Mail correo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRemitente = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Remitente") + ": "+ correo.getRemitente());
		lblRemitente.setBounds(31, 10, 344, 31);
		contentPane.add(lblRemitente);
		
		JLabel lblAsunto = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MailGUI.Asunto")+ ": "+ correo.getAsunto());
		lblAsunto.setBounds(31, 94, 344, 31);
		contentPane.add(lblAsunto);
		
		JLabel lblTexto = new JLabel("<html><body style='width: 250px'>" + correo.getMensaje() + "</body></html>");
		lblTexto.setBounds(31, 133, 351, 56);
		contentPane.add(lblTexto);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(129, 194, 162, 40);
		contentPane.add(btnClose);
		
		JLabel lblFecha = new JLabel(correo.getFechaEnvio().toString());
		lblFecha.setBounds(31, 51, 295, 25);
		contentPane.add(lblFecha);
		
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnClose_actionPerformed(e);
			}
		});
		
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
