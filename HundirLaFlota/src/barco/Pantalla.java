package barco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;


public class Pantalla extends JFrame {

	private static Pantalla pantalla;
	private CasillaDePantalla[][] casillasJugador;
	private CasillaDePantalla[][] casillasMaquina;
	private JPanel contentPane;
	private JPanel panelTitulo;
	private JLabel titulo;
	private JPanel panelCentral;
	private JPanel panelDelTexto;
	private JLabel texto;
	private JPanel panelDeJuego;
	private JPanel panelBotones;
	private JPanel panelTableros;
	private JPanel panelJugador;
	private JPanel panelMaquina;
	

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
		
		
		/* Pre: String no null con el texto a poner
		   Post: El campo de texto del frame ha sido sobrescrito por el valor de entrada
		         escribiendo las letras una a una a un ritmo de 10 letras/segundo aprox. 
		         
		   
		   NOTA: ESTE MÉTODO DEBERIA SER PRIVADO PARA ANTES DE ENTREGAR EL PROGRAMA FINAL.
		         
		         
		         
		 */
		
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
	
	
	public JLabel getPanelTexto() {return this.texto;}
	
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
		
		
		
		this.casillasJugador = new CasillaDePantalla[10][10];
		this.casillasMaquina = new CasillaDePantalla[10][10];
		
		this.panelJugador.add(new JLabel());
		this.panelMaquina.add(new JLabel());
		
		
		for (int i = 1; i != 11; i++) {
			char c = (char) (i + 96);
			this.panelJugador.add(new JLabel(Character.toString(c), SwingConstants.CENTER)); 
			this.panelMaquina.add(new JLabel(Character.toString(c), SwingConstants.CENTER));
		}
		
		for (int i = 0; i != 10; i++) {
			
			this.panelJugador.add(new JLabel(Integer.toString(i+1), SwingConstants.CENTER));
			this.panelMaquina.add(new JLabel(Integer.toString(i+1), SwingConstants.CENTER));

			
			for (int j = 0; j != 10; j++) {
				CasillaDePantalla cas = new CasillaDePantalla(i,j,true);

				CasillaDePantalla cas2 = new CasillaDePantalla(i,j,false);
				cas.addMouseListener(Controlador.getCon());
				cas2.addMouseListener(Controlador.getCon());
				
				Juego.getJuego().addObserver(cas);
				Juego.getJuego().addObserver(cas2);
				
				this.panelJugador.add(cas);
				this.panelMaquina.add(cas2);
	
				
			}
		}
	
		this.panelJugador.setBorder(BorderFactory.createLineBorder(Color.pink));
		this.panelMaquina.setBorder(BorderFactory.createLineBorder(Color.pink));
		
		this.setVisible(true);
	}

	
	// Getters automaticos generados por WindowBuilder
	
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
	
			titulo.setHorizontalAlignment(SwingConstants.CENTER);
			titulo.setIcon(new ImageIcon(".\\materiales\\titulo.png"));
		}
		return titulo;
	}
	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			panelCentral = new JPanel();
			panelCentral.setLayout(new BorderLayout(0, 0));
			panelCentral.add(getPanelDelTexto(), BorderLayout.SOUTH);
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
			panelDeJuego.setLayout(new BorderLayout(0, 0));
			panelDeJuego.add(getPanelTableros(), BorderLayout.CENTER);
		}
		return panelDeJuego;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
		}
		return panelBotones;
	}
	private JPanel getPanelTableros() {
		if (panelTableros == null) {
			panelTableros = new JPanel();
			panelTableros.setLayout(new GridLayout(1, 3, 0, 0));
			panelTableros.add(getPanelJugador());
			panelTableros.add(getPanelBotones());
			panelTableros.add(getPanelMaquina());
		}
		return panelTableros;
	}
	private JPanel getPanelJugador() {
		if (panelJugador == null) {
			panelJugador = new JPanel();
			panelJugador.setLayout(new GridLayout(11, 11, 0, 0));
		}
		return panelJugador;
	}
	private JPanel getPanelMaquina() {
		if (panelMaquina == null) {
			panelMaquina = new JPanel();
			panelMaquina.setLayout(new GridLayout(11, 11, 0, 0));
		}
		return panelMaquina;
	}
	
	

	
	
}
