package barco;

public class Posicion {

	private int fila;
	private int columna;
	
	public Posicion (int pF, int pC) {
		this.fila = pF;
		this.columna = pC;
	}
	
	public int getFila() {return this.fila;}
	public int getCol() {return this.columna;}

}
