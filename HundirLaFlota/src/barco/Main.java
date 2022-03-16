package barco;

public class Main {

	
	
	public static void main (String args[]) throws InterruptedException {
		
		
		/*
		 *  ESCRIBIR TEXTO POR PANTALLA NO SIGUE MODELO OBSERVADOR OBSERVABLE
		 *   
		 *  CREAR LAS CLASES OBSERVADOR Y OBSERVABLE PARA ELLOS.
		 * 
		 */
		
//		Pantalla.getPantalla().escribir("Coloca los barcos en el panel izq");
		Juego.getJuego().colocarBarcos();
	}
}
