package barco;


import vista.*;

import java.util.Observable;

public class TextoYAudio extends Observable {
	
// NOMBRE DE CLASE SE PUEDE CAMBIAR DESPUES, se controla el AUDIO y TEXTO aquí, JUEGO controla el canal de los label de casilla
	
	
	private static TextoYAudio textoYAudio;
	private String[] contenidosAEscribir; // audio, texto, btn1, btn2, btn3, btn4
	
	
	private TextoYAudio () {
		this.contenidosAEscribir = new String[6];
		
		this.resetearStrings();

		
	}
	
	private void resetearStrings() {
		
		for (int i = 0; i != 6; i++) {
			this.contenidosAEscribir[i] = "";
		}
	
	}
	
	public static TextoYAudio getInstancia() {
		if (TextoYAudio.textoYAudio == null) {TextoYAudio.textoYAudio = new TextoYAudio();}
		return TextoYAudio.textoYAudio;
	}
	
	
	
	public void darPanel (PanelDeTexto p) { this.addObserver(p); this.addObserver(Reproductor.getRep());}
	// En el modelo Observer - Observable los update van 1 a 1 ejecutandose tras dar la orden de notifyObservers() empezando por
	// el último Observer que se le dio al Observable. Para que toque audio mientras se escribe el texto se deben dar los observer
	// en este orden: PanelDeTexto, Reproductor.
	

	public void actualizarCambios() {

		
		
		this.setChanged();
		this.notifyObservers(this.contenidosAEscribir);
		this.resetearStrings();

	}
	
	public void setAudio(String pS) {this.contenidosAEscribir[0] = pS;}
	public void setTexto(String pS) {this.contenidosAEscribir[1] = pS;}
	public void setBoton(int pID, String pS) {
		this.contenidosAEscribir[2+pID] = pS;
	}

}
