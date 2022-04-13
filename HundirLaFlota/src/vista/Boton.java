package vista;

import javax.swing.JButton;

public class Boton extends JButton {

	private int id;
	
	public Boton (int pID) {this.id = pID;}
	
	public int getID() {return this.id;}
	
}
