package barco;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;

public class Pantalla extends JFrame {

	private static Pantalla pantalla;
	private JPanel contentPane;
	private JPanel panelTitulo;
	private JLabel titulo;
	private JPanel panelCentral;
	private JPanel panelDelTexto;
	private JLabel texto;
	private JPanel panelDeJuego;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pantalla frame = new Pantalla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	public void escribir(String pTexto) throws InterruptedException {
		
		this.texto.setText("");
		
		int i = 0;
		
		while (i != pTexto.length()) {
			this.texto.setText(this.texto.getText() + pTexto.charAt(i));
			this.texto.getText();
			Thread.sleep(100);
			
			i++;
		}
		
		
	}

	public static Pantalla getPantalla() {
		if (Pantalla.pantalla == null) {Pantalla.pantalla = new Pantalla();}
		return Pantalla.pantalla;
	}
	
	/**
	 * Create the frame.
	 */
	private Pantalla() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelTitulo(), BorderLayout.NORTH);
		contentPane.add(getPanelCentral(), BorderLayout.CENTER);
		this.setVisible(true);
	}

	private JPanel getPanelTitulo() {
		if (panelTitulo == null) {
			panelTitulo = new JPanel();
			panelTitulo.add(getTitulo());
		}
		return panelTitulo;
	}
	private JLabel getTitulo() {
		if (titulo == null) {
			titulo = new JLabel();
			titulo.setIcon(new ImageIcon(".\\materiales\\titulo.png"));
		}
		return titulo;
	}
	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			panelCentral = new JPanel();
			panelCentral.setLayout(new BorderLayout(0, 0));
			panelCentral.add(getPanelDelTexto(), BorderLayout.NORTH);
			panelCentral.add(getPanelDeJuego(), BorderLayout.CENTER);
		}
		return panelCentral;
	}
	private JPanel getPanelDelTexto() {
		if (panelDelTexto == null) {
			panelDelTexto = new JPanel();
			panelDelTexto.add(getTexto());
		}
		return panelDelTexto;
	}
	private JLabel getTexto() {
		if (texto == null) {
			texto = new JLabel("");
			texto.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		}
		return texto;
	}
	private JPanel getPanelDeJuego() {
		if (panelDeJuego == null) {
			panelDeJuego = new JPanel();
			panelDeJuego.setLayout(new GridLayout(1, 0, 0, 0));
		}
		return panelDeJuego;
	}
}
