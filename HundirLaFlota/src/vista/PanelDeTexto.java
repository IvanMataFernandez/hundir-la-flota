
package vista;

import barco.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

public class PanelDeTexto extends JLabel implements Observer {
	
	
	public PanelDeTexto() {this.setText("");}


	public void update(Observable o, Object arg) {
		this.escribir(TextoYAudio.getInstancia().getTexto());
	}
	
	
	
	private void escribir(String pTexto) {
		
		
		/* Pre: String no null con el texto a poner
		   Post: El campo de texto del frame ha sido sobrescrito por el valor de entrada
		         escribiendo las letras una a una a un ritmo de 10 letras/segundo aprox. 

		         
		 */
		


		if (!pTexto.contentEquals("")) {
			this.setText("");
			
			int i = 0;
			
			while (i != pTexto.length()) {
				this.setText(this.getText() + pTexto.charAt(i));
				this.getText();
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {}
				
				i++;
			}
		}
		
		

		
		
	}

}
