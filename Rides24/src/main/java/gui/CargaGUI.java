package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CargaGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton btnSign = null;
	private JButton btnRegister = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JProgressBar progressBar;
	private JLabel lblLoading;
	
	/**
	 * This is the default constructor
	 */
	public CargaGUI() {
		super();
		setResizable(false);

		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		
		setLocationRelativeTo(null);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
		
		
		setContentPane(jContentPane);
		
		lblLoading = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoadingGUI.Loading"));
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		jContentPane.add(lblLoading);
		
		progressBar = new JProgressBar();
		jContentPane.add(progressBar);
		setTitle("");
		
		Timer timer = new Timer(50, e -> {
            int valor = progressBar.getValue();
            if (valor < 100) {
                progressBar.setValue(valor + 1); // esto se actualiza visualmente en tiempo real
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("LoadingGUI.COMP"));
                doNotSetVisible(e);
            }
        });

        timer.start();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
			}
		});
		
	}
	
	private void doNotSetVisible(ActionEvent e) {
		this.setVisible(false);
	}
	
	
	
	
} // @jve:decl-index=0:visual-constraint="0,0"

