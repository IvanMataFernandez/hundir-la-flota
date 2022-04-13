package vista;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

public class Boton extends JButton implements Observer {

	private int id;
	
	public Boton (int pID) {this.id = pID;}
	
	public int getID() {return this.id;}

	@Override
	public void update(Observable o, Object arg) {
		String val = ((String[]) arg)[this.id+2];
		
		if (val.contentEquals("ocultar")) {
			super.setVisible(false);
		} else if (!val.contentEquals("")) {
			super.setText(val);
			super.setVisible(true);
		}
		
		
	}
	
	
	
	
	
}
