package barco;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CasillaDePantalla extends JLabel implements Observer {
	
	
	private int fila;
	private int columna;
	
	public CasillaDePantalla (int pFila, int pCol) {
		this.fila = pFila;
		this.columna = pCol;
		super.setBackground(Color.DARK_GRAY);
		super.setBorder(BorderFactory.createLineBorder(Color.white));
		super.setOpaque(true);
	}
	
	
	public int getFila() {return this.fila;}
	public int getCol() {return this.columna;}
	
	
	public void update(Observable o, Object c) {
		
		// ACTUALIZAR EL COLOR DE LA CASILLA AQUI!
		
	}

}
