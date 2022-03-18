package barco;

import java.util.Observable;

public class TextoYAudio extends Observable {
	
// NOMBRE DE CLASE SE PUEDE CAMBIAR DESPUES, se controla el AUDIO y TEXTO aquí, JUEGO controla el canal de los label de casilla
	
	
	private static TextoYAudio textoYAudio;
	private String audioAReproducir;
	private String textoAEscribir;
	
	
	private TextoYAudio () {this.audioAReproducir = ""; this.textoAEscribir = "";}
	
	public static TextoYAudio getInstancia() {
		if (TextoYAudio.textoYAudio == null) {TextoYAudio.textoYAudio = new TextoYAudio();}
		return TextoYAudio.textoYAudio;
	}
	
	
	
	public void darPanel (PanelDeTexto p) { this.addObserver(p); this.addObserver(new Reproductor());}
	// En el modelo Observer - Observable los update van 1 a 1 ejecutandose tras dar la orden de notifyObservers() empezando por
	// el último Observer que se le dio al Observable. Para que toque audio mientras se escribe el texto se deben dar los observer
	// en este orden: PanelDeTexto, Reproductor.
	
	public String getAudio() {return this.audioAReproducir;}
	public String getTexto() {return this.textoAEscribir;}

	public void actualizarCambios() {
		this.setChanged();
		this.notifyObservers();
		this.textoAEscribir = "";
		this.audioAReproducir = "";
	}
	
	public void setAudio(String pS) {this.audioAReproducir = pS;}
	public void setTexto(String pS) {this.textoAEscribir = pS;}
	

}
