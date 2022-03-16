package barco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controlador implements MouseListener {

	private static Controlador controlador;
	
	
	private Controlador() {}
	
	public static Controlador getCon() {
		if (Controlador.controlador == null) {
			Controlador.controlador = new Controlador();
			
		}
		return Controlador.controlador;
	}


	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() instanceof CasillaDePantalla) {
			CasillaDePantalla cas = (CasillaDePantalla) e.getSource();
			Juego.getJuego().cogerInput(cas);
		}
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
