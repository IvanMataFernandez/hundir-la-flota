package vista;

import java.awt.Color;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CasillaDePantalla extends JLabel implements Observer {
	
	
	private int fila;
	private int columna;
	private boolean esDeJugador;
	
	

	
	public CasillaDePantalla (int pFila, int pCol, boolean pJ) {
		this.fila = pFila;
		this.columna = pCol;
		this.esDeJugador = pJ;
		super.setBackground(Color.DARK_GRAY);
		super.setBorder(BorderFactory.createLineBorder(Color.white));
		super.setOpaque(true);
		
		
		

	}
	
	
	public int getFila() {return this.fila;}
	public int getCol() {return this.columna;}
	public boolean esDeJ1() {return this.esDeJugador;}
	
	public void update(Observable o, Object c) {
		Color[][] array = (Color[][]) c;
		
		
		if (array[this.fila][this.columna] != null) {super.setBackground(array[fila][columna]);}
	}

}
