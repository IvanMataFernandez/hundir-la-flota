

package barco;
import vista.*;
public class Main {

	
	
	public static void main (String args[]) throws InterruptedException {
		
		
		// EL MAIN NO VA A IR DEL TODO HASTA QUE SE HAYA ACABADO EL PONER BARCOS DE IA
	
		Jugadores listaJugador = Jugadores.getJugadores();	
		Pantalla.getPantalla(); // INICIALIZAR PANTALLA
		listaJugador.getJugadorHumano().colocarBarcos();
		listaJugador.getJugadorIA().colocarBarcos();
		

		try {
			while (true) {
				listaJugador.getJugadorActual().disparar();
				listaJugador.cambiarJugador();
				
				
			}

			
			
		} catch (ExcepcionFinDePartida excepcion) {
			
			if (excepcion.ganoJ1()) {
				// GANA J1!
			} else {
				// GANA IA!
			}
			
		}
		
		Pantalla.getPantalla().dispose();
		
		
		
		
	}
}
